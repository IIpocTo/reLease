package re_lease.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import re_lease.domain.Product;
import re_lease.domain.User;
import re_lease.dto.PageParams;
import re_lease.dto.ProductDTO;
import re_lease.dto.ProductPage;
import re_lease.repository.ProductCustomRepository;
import re_lease.repository.ProductRepository;
import re_lease.repository.UserRepository;
import re_lease.service.exceptions.NotPermittedException;
import re_lease.service.exceptions.ProductNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductCustomRepository productCustomRepository;
    private final SecurityContextService securityContextService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              UserRepository userRepository,
                              ProductCustomRepository productCustomRepository,
                              SecurityContextService securityContextService) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.productCustomRepository = productCustomRepository;
        this.securityContextService = securityContextService;
    }

    @Override
    public void delete(Long id) throws NotPermittedException {
        final Product product = productRepository.findOne(id);
        final Optional<User> currentUser = securityContextService.currentUser();
        currentUser.filter(user -> user.equals(product.getProductLeaser()))
                .ifPresent(user -> productRepository.delete(id));
        currentUser.filter(user -> user.equals(product.getProductLeaser()))
                .orElseThrow(() -> new NotPermittedException("no permission to delete this product"));
    }

    @Override
    public ProductPage findByUser(Long userId, PageParams pageParams) {
        final User user = userRepository.findOne(userId);
        List<ProductCustomRepository.Row> rows = productCustomRepository.findByUser(user, pageParams);
        List<ProductDTO> products = rows
                .stream()
                .map(toDTO())
                .collect(Collectors.toList());
        Long page = pageParams.getPage();
        Long size = pageParams.getSize();
        if (!rows.isEmpty() && page != null && size != null) {
            Long value = rows.get(0).getUserStats().getProductCount();
            Long pageMax = 0L;
            if (value > 0) {
                pageMax = value / size + 1;
            }
            return ProductPage.instance(page, pageMax, value, products);
        } else {
            return ProductPage.instance(1L, 0L, 0L, Collections.emptyList());
        }
    }

    @Override
    public ProductPage findMyProducts(PageParams pageParams) {
        return securityContextService.currentUser()
                .map(u -> findByUser(u.getId(), pageParams))
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public ProductPage findFromAll(PageParams pageParams) {
        List<ProductCustomRepository.Row> rows = productCustomRepository.findAll(pageParams);
        List<ProductDTO> products = rows.stream().map(toDTO()).collect(Collectors.toList());
        Long value = 0L;
        Long page = pageParams.getPage();
        Long size = pageParams.getSize();
        if (!rows.isEmpty()) {
            value = rows.get(0).getUserStats().getProductCount();
        }
        Long pageMax = 0L;
        if (value > 0) {
            pageMax = value / size + 1;
        }
        return ProductPage.instance(page, pageMax, value, products);
    }

    @Override
    public Product saveMyProduct(Product product) {
        return securityContextService.currentUser()
                .map(u -> {
                    product.setProductLeaser(u);
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new AccessDeniedException(""));
    }

    @Override
    public ProductDTO findOne(Long id) throws ProductNotFoundException {
        return productCustomRepository.findOne(id)
                .map(row -> ProductDTO.newInstance(row.getProduct(), true))
                .orElseThrow(ProductNotFoundException::new);
    }

    private Function<ProductCustomRepository.Row, ProductDTO> toDTO() {
        final Optional<User> currentUser = securityContextService.currentUser();
        return row -> {
            final Boolean isMyProduct = currentUser
                    .map(user -> row.getProduct().getProductLeaser().equals(user))
                    .orElse(null);
            return ProductDTO.newInstance(row.getProduct(), row.getUserStats(), isMyProduct);
        };
    }

}
