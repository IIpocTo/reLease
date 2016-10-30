package re_lease.service_layer.metro_station;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MetroRepository extends CrudRepository<Metro, UUID> {}
