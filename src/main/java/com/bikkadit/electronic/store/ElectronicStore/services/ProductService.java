package com.bikkadit.electronic.store.ElectronicStore.services;

import com.bikkadit.electronic.store.ElectronicStore.dtos.ProductDto;

import java.util.List;

public interface ProductService {

    //create
    ProductDto create(ProductDto productDto);

    //update
    ProductDto update(ProductDto productDto,Long productId);

    //delete
    void  delete(Long productId);

    //get single
    ProductDto get(Long productId);

    //get all
    List<ProductDto> getAll();

    //get all : live
    List<ProductDto> getAllLive();

    //search product
    List<ProductDto> searchByTitle(String subTitle );
}
