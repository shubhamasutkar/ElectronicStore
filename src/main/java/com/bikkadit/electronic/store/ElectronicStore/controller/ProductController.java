package com.bikkadit.electronic.store.ElectronicStore.controller;

import com.bikkadit.electronic.store.ElectronicStore.dtos.ApiResponseMessage;
import com.bikkadit.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.bikkadit.electronic.store.ElectronicStore.dtos.ProductDto;
import com.bikkadit.electronic.store.ElectronicStore.helper.AppConstants;
import com.bikkadit.electronic.store.ElectronicStore.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    //create

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        log.info("Initiating request for save product");
        ProductDto createdProduct = productService.create(productDto);
        log.info("Completed request for save product");
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    //update

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId , @RequestBody ProductDto productDto) {
        log.info("Initiating request for update product with:{}", productId);
        ProductDto updatedProduct = productService.update(productDto, productId);
        log.info("Completed request for update product with:{}", productId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }
    //delete

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable Long productId) {
        log.info("Initiating request for delete product with:{}", productId);
        productService.delete(productId);
        ApiResponseMessage message = ApiResponseMessage.builder()
                .message(AppConstants.USER_DELETE)
                .success(true)
                .status(HttpStatus.OK)
                .build();
        log.info("Completed request for delete product with:{}", productId);
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    //get single

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long productId) {
        log.info("Initiating request for single product");
        ProductDto productDto = productService.get(productId);
        log.info("Completed request for single product");
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    //get all

    @GetMapping
    public ResponseEntity<PageableResponse> getAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer PageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sorDir", defaultValue = "asc", required = false) String sortDir) {
        log.info("Initiating request for getAll product ");
        PageableResponse<ProductDto> pageableResponse = productService.getAll(pageNumber, PageSize, sortBy, sortDir);
        log.info("Completed request for getAll product ");
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);

    }

    //get all live

    @GetMapping("/live")
    public ResponseEntity<PageableResponse> getAllLive(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer PageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sorDir", defaultValue = "asc", required = false) String sortDir) {
        log.info("Initiating request for getAll Live product ");
        PageableResponse<ProductDto> pageableResponse = productService.getAllLive(pageNumber, PageSize, sortBy, sortDir);
        log.info("Completed request for getAll Live product ");
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);

    }
    //search

    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse> searchProduct(
            @PathVariable String query,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer PageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sorDir", defaultValue = "asc", required = false) String sortDir) {
        log.info("Initiating request for search product ");
        PageableResponse<ProductDto> pageableResponse = productService.searchByTitle(query,pageNumber, PageSize, sortBy, sortDir);
        log.info("Completed request for search product ");
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);

    }


}
