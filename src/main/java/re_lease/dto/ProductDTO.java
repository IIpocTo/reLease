package re_lease.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import re_lease.domain.Product;
import re_lease.domain.UserStats;

@Value
@Builder
public class ProductDTO {

    private final Long id;

    @NonNull
    private Integer price;

    @NonNull
    private final String title;

    @NonNull
    private final String description;

    @JsonProperty("user")
    private final UserDTO userDTO;

    private final Boolean isMyProduct;

    public static ProductDTO newInstance(Product product, UserStats userStats, Boolean isMyProduct) {

        final UserDTO userDTO = UserDTO.builder()
                .id(product.getProductLeaser().getId())
                .login(product.getProductLeaser().getUsername())
                .email(product.getProductLeaser().getEmail())
                .userStats(userStats)
                .build();

        return ProductDTO.builder()
                .id(product.getId())
                .price(product.getPrice())
                .title(product.getTitle())
                .description(product.getDescription())
                .userDTO(userDTO)
                .isMyProduct(isMyProduct)
                .build();

    }

    public static ProductDTO newInstance(Product product, Boolean isMyProduct) {
        return ProductDTO.newInstance(product, null, isMyProduct);
    }

}
