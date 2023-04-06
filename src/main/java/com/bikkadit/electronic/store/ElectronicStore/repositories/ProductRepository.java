package com.bikkadit.electronic.store.ElectronicStore.repositories;

import com.bikkadit.electronic.store.ElectronicStore.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    //search
   List <Product>findByTitleContaining(String subTitle);
    List<Product> findByLiveTrue();
}
