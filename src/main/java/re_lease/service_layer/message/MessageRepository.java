package re_lease.service_layer.message;

import org.springframework.data.repository.CrudRepository;
import re_lease.service_layer.product.Product;

import java.util.UUID;

public interface MessageRepository extends CrudRepository<Message, UUID> {}