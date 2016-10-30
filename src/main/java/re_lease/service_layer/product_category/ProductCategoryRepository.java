package re_lease.service_layer.product_category;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProductCategoryRepository extends CrudRepository<ProductCategory, UUID> {}
