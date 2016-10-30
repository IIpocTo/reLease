package re_lease.service_layer.product_type;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProductTypeRepository extends CrudRepository<ProductType, UUID> {}
