package re_lease.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import re_lease.domain.Product;
import re_lease.domain.User;
import re_lease.dto.PageParams;
import re_lease.dto.ProductDTO;
import re_lease.repository.ProductCustomRepository;
import re_lease.repository.ProductRepository;
import re_lease.repository.UserRepository;
import re_lease.service.exceptions.NotPermittedException;
import re_lease.service.exceptions.ProductNotFoundException;

import javax.persistence.EntityNotFoundException;
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
        currentUser.filter(u -> u.equals(product.getProductLeaser()))
                .ifPresent(u -> productRepository.delete(id));
        currentUser.filter(u -> u.equals(product.getProductLeaser()))
                .orElseThrow(() -> new NotPermittedException("no permission to delete this product"));
    }

    @Override
    public List<ProductDTO> findByUser(Long userId, PageParams pageParams) {
        final User user = userRepository.findOne(userId);
        return productCustomRepository.findByUser(user, pageParams)
                .stream()
                .map(toDTO())
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> findMyProducts(PageParams pageParams) {
        return securityContextService.currentUser()
                .map(u -> findByUser(u.getId(), pageParams))
                .orElseThrow(RuntimeException::new);
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
        return productCustomRepository.findOne(id).map(r -> {
            return ProductDTO.newInstance(r.getProduct(), true);
        })
                .orElseThrow(ProductNotFoundException::new);
    }

    private Function<ProductCustomRepository.Row, ProductDTO> toDTO() {
        final Optional<User> currentUser = securityContextService.currentUser();
        return r -> {
            final Boolean isMyProduct = currentUser
                    .map(u -> r.getProduct().getProductLeaser().equals(u))
                    .orElse(null);
            return ProductDTO.newInstance(r.getProduct(), r.getUserStats(), isMyProduct);
        };
    }

}
