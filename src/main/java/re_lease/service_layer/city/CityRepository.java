package re_lease.service_layer.city;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CityRepository extends CrudRepository<City, UUID> {}
