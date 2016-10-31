package re_lease.service_layer.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(path = "all_users")
public interface UserRepository extends CrudRepository<User, UUID> {}
