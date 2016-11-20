package re_lease.service;

import re_lease.domain.Product;
import re_lease.dto.PageParams;
import re_lease.dto.ProductDTO;
import re_lease.service.exceptions.NotPermittedException;

import java.util.List;

public interface ProductService {
    void delete(Long id) throws NotPermittedException;
    List<ProductDTO> findByUser(Long userId, PageParams pageParams);
    List<ProductDTO> findMyProducts(PageParams pageParams);
    Product saveMyProduct(Product product);
}
