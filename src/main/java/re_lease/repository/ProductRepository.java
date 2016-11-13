package re_lease.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import re_lease.domain.Product;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {}
