package com.bikkadit.electronic.store.ElectronicStore.services.impl;

import com.bikkadit.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.bikkadit.electronic.store.ElectronicStore.dtos.ProductDto;
import com.bikkadit.electronic.store.ElectronicStore.entities.Product;
import com.bikkadit.electronic.store.ElectronicStore.exceptions.ResourceNotFoundException;
import com.bikkadit.electronic.store.ElectronicStore.helper.Helper;
import com.bikkadit.electronic.store.ElectronicStore.repositories.ProductRepository;
import com.bikkadit.electronic.store.ElectronicStore.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public ProductDto create(ProductDto productDto) {
        log.info("Initiating Dao call for save the product details !!");
        Product product = mapper.map(productDto, Product.class);
        Product saveProduct = productRepository.save(product);
        log.info("Completed Dao call for save the product details !!");
        return mapper.map(saveProduct, ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, Long productId) {
        log.info("Initiating Dao call for update product details with:{}", productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found for given Id !!"));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        Product updatedProduct = productRepository.save(product);
        log.info("Completed Dao call for update  product details: {}", productId);
        return mapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public void delete(Long productId) {
        log.info("Initiating Dao call for delete product with:{}",productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found for given Id !!"));
        log.info("Completed Dao call for delete product with:{}",productId);
        productRepository.delete(product);
    }

    @Override
    public ProductDto get(Long productId) {
        log.info("Initiating Dao call for getSingle product details:{}",productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found for given Id !!"));
        log.info("Completed Dao call for getSingle  product details: {}", productId);
        return mapper.map(product, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        log.info("Initiating Dao call for getAll product !!");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending()) ;
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findAll(pageable);
        log.info("Completed Dao call for getAll product !!");
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        log.info("Initiating Dao call for getAllLive product !!");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending()) ;
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByLiveTrue(pageable);
        log.info("Completed Dao call for getAllLive product !!");
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        log.info("Initiating Dao call for search for product !!");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending()) ;
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByTitleContaining(subTitle,pageable);
        log.info("Completed Dao call for search for product !!");
        return Helper.getPageableResponse(page, ProductDto.class);
    }

}
