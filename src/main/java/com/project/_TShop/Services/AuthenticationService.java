package com.project._TShop.Services;

import com.project._TShop.Entities.Account;
import com.project._TShop.Entities.Auth_Provider;
import com.project._TShop.Entities.Role;
import com.project._TShop.Repositories.AccountRepository;
import com.project._TShop.Repositories.RoleRepository;
import com.project._TShop.Request.AuthenticationRequest;
import com.project._TShop.Request.ChangePasswordReq;
import com.project._TShop.Request.FogotPasswordRequest;
import com.project._TShop.Request.RegisterRequest;
import com.project._TShop.Request.ResetPasswordRequest;
import com.project._TShop.Response.AuthenticationResponse;
import com.project._TShop.Response.Response;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import net.bytebuddy.utility.RandomString;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AccountRepository accountRepo;
    private final RoleRepository roleRepo;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    public Response register(RegisterRequest request, HttpServletRequest req) throws MessagingException, UnsupportedEncodingException {
        Response response = new Response();

        if(isEmailOrUsernameExists(request)){
            response.setStatus(209);
            response.setMessage("Email or username is used");
            return response;
        }
        Collection<Role> roles = new ArrayList<>();
        Optional<Role> optionalRole = roleRepo.findByName("USER");
        if (optionalRole.isPresent()) {
            roles.add(optionalRole.get());
        } else {
            response.setStatus(201);
            response.setMessage("Default Role not found");
            return response;
        }
        String registrationToken = RandomString.make(50);
        var user = Account.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .resetPasswordToken(null)
                .registrationToken(registrationToken)
                .email(request.getEmail())
                .createdAt(new Date())
                .auth_provider(String.valueOf(Auth_Provider.LOCAL))
                .roles(roles)
                .status(false)
                .build();
        accountRepo.save(user);
        emailService.sendVerificationEmail(user, req);
        var jwtToken = jwtService.generateToken(user);
        response.setStatus(200);
        response.setToken(jwtToken);
        return response;
    }

    public boolean isEmailOrUsernameExists(RegisterRequest request) {
        return accountRepo.existsByEmailOrUsername(request.getEmail(), request.getUsername());
    }

    public boolean isEmailExists(FogotPasswordRequest request) {
        return accountRepo.findByEmail(request.getEmail()).isPresent();
    }
    public boolean isEmailExistAndEnable(FogotPasswordRequest request) {
        return accountRepo.findByEmailAndStatus(request.getEmail(), true).isPresent();
    }

    public Response updateResetPasswordToken(FogotPasswordRequest request, HttpServletRequest req) throws MessagingException, UnsupportedEncodingException {
        System.out.println("Mail: "+ request.getEmail());
        Response response = new Response();
        if(!isEmailExistAndEnable(request)){
            response.setStatus(201);
            response.setMessage("No registration found for this email, or the account has not been verified.");
        }
        Optional<Account> optionalAccount = accountRepo.findByEmail(request.getEmail());
        if (optionalAccount.isPresent()) {
            optionalAccount.get().setResetPasswordToken(RandomString.make(50));
            accountRepo.save(optionalAccount.get());
            emailService.sendEmailToResetPassword(optionalAccount.get(), req);
            response.setStatus(200);
            response.setMessage("Update Reset Password Token and Send mail success");
        } else {
            response.setStatus(202);
            response.setMessage("Account not found");
        }
        return response;
    }

    public Response updatePassword(ResetPasswordRequest request) {
        System.out.println("New Password: "+request.getNewPassword());
        Response response = new Response();
        Optional <Account> optionalAccount = accountRepo.findByResetPasswordToken(request.getToken());
        System.out.println(optionalAccount);
        if (optionalAccount.isPresent()) {
            optionalAccount.get().setResetPasswordToken(null);
            optionalAccount.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
            accountRepo.save(optionalAccount.get());
            response.setStatus(200);
            response.setMessage("Update Password success");
        } else {
            response.setStatus(201);
            response.setMessage("Account not found");
        }
        return response;
    }


    public Response authenticate(AuthenticationRequest request) {
        Response response = new Response();

        Optional<Account> optionalAccount = accountRepo.findByUsername(request.getUsername());
        if (optionalAccount.isEmpty()) {
            response.setStatus(202);
            response.setMessage("Username not found");
            return response;
        }
        if (!optionalAccount.get().isStatus()) {
            response.setStatus(203);
            response.setMessage("Your account is not verified. Please verify your account.");
            return response;
        }
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            response.setStatus(201);
            response.setMessage("Invalid username or password");
            return response;
        }
        var jwtToken = jwtService.generateToken(optionalAccount.get());
        response.setStatus(200);
        response.setToken(jwtToken);
      return response;
    }
}
