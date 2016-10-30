package re_lease.service_layer.deal;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DealRepository extends CrudRepository<Deal, UUID> {}
