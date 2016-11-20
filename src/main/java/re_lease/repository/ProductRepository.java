package re_lease.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import re_lease.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {}
