package io.pivotal.catalog.repositories;

import io.pivotal.catalog.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, String> {
}
