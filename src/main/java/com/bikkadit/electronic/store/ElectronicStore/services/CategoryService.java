package com.bikkadit.electronic.store.ElectronicStore.services;

import com.bikkadit.electronic.store.ElectronicStore.dtos.CategoryDto;
import com.bikkadit.electronic.store.ElectronicStore.dtos.PageableResponse;

public interface CategoryService {

    //create
    CategoryDto create(CategoryDto categoryDto);

    //update
    CategoryDto update(CategoryDto categoryDto,Long categoryId);

    //delete
    void delete(Long categoryId);

    //get all

    PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get single category detail
    CategoryDto get(Long categoryId);

    //search
}
