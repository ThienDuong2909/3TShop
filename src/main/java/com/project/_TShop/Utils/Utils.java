package com.project._TShop.Utils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.project._TShop.DTO.AccountDTO;
import com.project._TShop.DTO.CartDTO;
import com.project._TShop.DTO.Cart_ItemsDTO;
import com.project._TShop.DTO.CategoryDTO;
import com.project._TShop.DTO.ColorDTO;
import com.project._TShop.DTO.ImagesDTO;
import com.project._TShop.DTO.OrderDTO;
import com.project._TShop.DTO.Order_DetailDTO;
import com.project._TShop.DTO.Order_StatusDTO;
import com.project._TShop.DTO.ProductDTO;
import com.project._TShop.DTO.RoleDTO;
import com.project._TShop.DTO.SizeDTO;
import com.project._TShop.DTO.SpecificationsDTO;
import com.project._TShop.DTO.UserDTO;
import com.project._TShop.Entities.Account;
import com.project._TShop.Entities.Cart;
import com.project._TShop.Entities.Cart_Items;
import com.project._TShop.Entities.Category;
import com.project._TShop.Entities.Color;
import com.project._TShop.Entities.Images;
import com.project._TShop.Entities.Order;
import com.project._TShop.Entities.Order_Detail;
import com.project._TShop.Entities.Order_Status;
import com.project._TShop.Entities.Product;
import com.project._TShop.Entities.Role;
import com.project._TShop.Entities.Size;
import com.project._TShop.Entities.Specifications;
import com.project._TShop.Entities.User;

public class Utils {
    public static UserDTO mapUser(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setAccountDTO(mapAccount(user.getAccount()));
        userDTO.setDate_of_birth(user.getDate_of_birth());
        userDTO.setEmail(user.getEmail());
        userDTO.setF_name(user.getF_name());
        userDTO.setGender(user.isGender());
        userDTO.setL_name(user.getL_name());
        userDTO.setPhone(user.getPhone());
        userDTO.setUser_id(user.getUser_id());
        return userDTO;
    }

    public static AccountDTO mapAccount(Account account){
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccount_id(account.getAccount_id());
        accountDTO.setUsername(account.getUsername());
        accountDTO.setPassword(account.getPassword());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setStatus(account.isStatus());
        accountDTO.setCreateAt(account.getCreatedAt());
        accountDTO.setReset_password_token(account.getResetPasswordToken());
        accountDTO.setRoleDTOs(mapRoles(account.getRoles()));
        return accountDTO;
    }

    public static RoleDTO mapRole(Role role){
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRole_id(role.getRole_id());
        roleDTO.setName(role.getName());
        return roleDTO;
    }

    public static ProductDTO mapProduct(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategoryDTO(mapCategory(product.getCategory_id()));
        productDTO.setCreate_at(product.getCreated_at());
        productDTO.setDescription(product.getDescription());
        productDTO.setName(product.getName());
        productDTO.setImage(product.getImage());
        productDTO.setPrice(product.getPrice());
        productDTO.setProduct_id(product.getProduct_id());
        productDTO.setSold(product.getSold());
        productDTO.setStatus(product.getStatus());
        productDTO.setWhich_gender(product.getWhich_gender());
        return productDTO;
    }

    public static CategoryDTO mapCategory(Category category){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategory_id(category.getCategory_id());
        categoryDTO.setCreate_at(category.getCreated_at());
        categoryDTO.setImage(category.getImage());
        categoryDTO.setName(category.getName());
        categoryDTO.setStatus(category.getStatus());
        return categoryDTO;
    }

    public static ImagesDTO mapImages(Images images){
        ImagesDTO imagesDTO = new ImagesDTO();
        imagesDTO.setImage_data(images.getImage_data());
        imagesDTO.setImage_id(images.getImage_id());
        imagesDTO.setProductDTO(mapProduct(images.getProduct()));
        return imagesDTO;
    }

    public static OrderDTO mapOrder(Order order){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setAddress_line_1(order.getAddress_line_1());
        orderDTO.setAddress_line_2(order.getAddress_line_2());
        orderDTO.setDate(order.getDate());
        orderDTO.setName(order.getName());
        orderDTO.setOrder_id(order.getOrder_id());
        orderDTO.setPhone(order.getPhone());
        orderDTO.setTotal_price(order.getTotal_price());
        orderDTO.setUserDTO(mapUser(order.getUser_id()));
        return orderDTO;
    }

    public static ColorDTO mapColor(Color color){
        ColorDTO colorDTO = new ColorDTO();
        colorDTO.setColor_id(color.getColor_id());
        colorDTO.setHex(color.getHex());
        colorDTO.setName(color.getName());
        return colorDTO;
    }

    public static Order_DetailDTO mapOrderDetail(Order_Detail order_Detail){
        Order_DetailDTO order_DetailDTO = new Order_DetailDTO();
        order_DetailDTO.setOrderDTO(mapOrder(order_Detail.getOrder()));
        order_DetailDTO.setOrder_detail_id(order_Detail.getOrder_detail_id());
        order_DetailDTO.setProductDTO(mapProduct(order_Detail.getProduct()));
        order_DetailDTO.setQuantity(order_Detail.getQuantity());
        return order_DetailDTO;
    }

    public static SizeDTO mapSize(Size size){
        SizeDTO sizeDTO = new SizeDTO();
        sizeDTO.setName(size.getName());
        sizeDTO.setSize_id(size.getSize_id());
        return sizeDTO;
    }

    public static CartDTO mapCart(Cart cart){
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCart_id(cart.getCart_id());
        cartDTO.setAccountDTO(mapAccount(cart.getAccount()));
        return cartDTO;
    }

    public static Cart_ItemsDTO mapCart_Items(Cart_Items cart_Items){
        Cart_ItemsDTO cart_ItemsDTO = new Cart_ItemsDTO();
        cart_ItemsDTO.setCard_item_id(cart_Items.getCart_items_id());
        cart_ItemsDTO.setCartDTO(mapCart(cart_Items.getCart()));
        cart_ItemsDTO.setCreate_at(cart_Items.getCreated_at());
        cart_ItemsDTO.setProductDTO(mapProduct(cart_Items.getProduct()));
        cart_ItemsDTO.setQuantity(cart_Items.getQuantity());
        return cart_ItemsDTO;
    }

    public static SpecificationsDTO mapSpecifications(Specifications specifications){
        SpecificationsDTO specificationsDTO = new SpecificationsDTO();
        specificationsDTO.setColorDTO(mapColor(specifications.getColor()));
        specificationsDTO.setProductDTO(mapProduct(specifications.getProduct()));
        specificationsDTO.setQuantity(specifications.getQuantity());
        specificationsDTO.setSizeDTO(mapSize(specifications.getSize_id()));
        specificationsDTO.setSpecifications_id(specifications.getSpecifications_id());
        return specificationsDTO;

    }

    public static Order_StatusDTO mapOrder_Status(Order_Status order_Status){
        Order_StatusDTO order_StatusDTO = new Order_StatusDTO();
        order_StatusDTO.setCreate_at(order_Status.getCreated_at());
        order_StatusDTO.setOrderDTO(mapOrder(order_Status.getOrder_id()));
        order_StatusDTO.setOrder_status_id(order_Status.getOrder_status_id());
        order_StatusDTO.setStatus(order_Status.getStatus());
        return order_StatusDTO;
    }

    public static Collection<RoleDTO> mapRoles(Collection<Role> roles) {
        return roles.stream()
                    .map(Utils::mapRole)
                    .collect(Collectors.toList());
    }

    public static List<UserDTO> mapUsers(List<User> users){
        return users.stream().map(Utils::mapUser).collect(Collectors.toList());
    }
    public static List<OrderDTO> mapOrders(List<Order> orders){
        return orders.stream().map(Utils::mapOrder).collect(Collectors.toList());
    }
    public static List<ProductDTO> mapProducts(List<Product> products){
        return products.stream().map(Utils::mapProduct).collect(Collectors.toList());
    }
    public static List<ColorDTO> mapColors(List<Color> colors){
        return colors.stream().map(Utils::mapColor).collect(Collectors.toList());
    }
    public static List<CategoryDTO> mapCategories(List<Category> categories){
        return categories.stream().map(Utils::mapCategory).collect(Collectors.toList());
    }
}
