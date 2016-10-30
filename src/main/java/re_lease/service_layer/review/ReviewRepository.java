package re_lease.service_layer.review;


import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ReviewRepository extends CrudRepository<Review, UUID> {}
