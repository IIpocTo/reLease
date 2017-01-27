package re_lease.service;

import re_lease.domain.Product;
import re_lease.dto.PageParams;
import re_lease.dto.ProductDTO;
import re_lease.dto.ProductPage;
import re_lease.service.exceptions.NotPermittedException;
import re_lease.service.exceptions.ProductNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    void delete(Long id) throws NotPermittedException;
    ProductPage findByUser(Long userId, PageParams pageParams);
    ProductPage findMyProducts(PageParams pageParams);
    ProductPage findFromAll(PageParams pageParams);
    Product saveMyProduct(Product product);
    ProductDTO findOne(Long id) throws ProductNotFoundException;
}
