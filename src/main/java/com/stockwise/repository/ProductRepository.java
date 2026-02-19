package com.stockwise.repository;

import com.stockwise.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product,Long> {

    @Query("SELECT p from Product p WHERE p.currentStock <= p.minimunStock")
    List<Product> findProductsByLowStock();

}
