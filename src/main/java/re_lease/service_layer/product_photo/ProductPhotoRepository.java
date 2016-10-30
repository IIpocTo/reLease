package re_lease.service_layer.product_photo;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProductPhotoRepository extends CrudRepository<ProductPhoto, UUID> {}
