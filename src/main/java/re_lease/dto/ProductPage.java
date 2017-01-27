package re_lease.dto;

import lombok.Builder;
import lombok.Value;
import re_lease.domain.Product;
import re_lease.dto.ProductDTO;

import java.util.List;

@Value
@Builder
public class ProductPage {

    private Long currentPage;
    private Long totalPages;
    private Long totalElements;
    private List<ProductDTO> content;

    public static ProductPage instance(Long currentPage, Long totalPages,Long totalElements, List<ProductDTO> content) {
        return ProductPage.builder()
                .currentPage(currentPage)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .content(content)
                .build();
    }
}
