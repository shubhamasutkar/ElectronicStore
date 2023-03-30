package com.bikkadit.electronic.store.ElectronicStore.repositories;

import com.bikkadit.electronic.store.ElectronicStore.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category ,Long> {
}
