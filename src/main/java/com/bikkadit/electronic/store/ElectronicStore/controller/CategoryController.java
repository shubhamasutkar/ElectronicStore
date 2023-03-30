package com.bikkadit.electronic.store.ElectronicStore.controller;

import com.bikkadit.electronic.store.ElectronicStore.dtos.*;
import com.bikkadit.electronic.store.ElectronicStore.services.CategoryService;
import com.bikkadit.electronic.store.ElectronicStore.services.CoverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CoverService coverService;

    @Value("${category.profile.coverImage.path}")
    private String coverImagePath;

    //create
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){

        log.info("Initiating request for create category");
        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        log.info("Completed request for create category");
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable Long categoryId,
            @RequestBody CategoryDto categoryDto){
        log.info("Initiating request for update category with:{}",categoryId);
        CategoryDto updatedCategory = categoryService.update(categoryDto, categoryId);
        log.info("Completed request for update category with:{}",categoryId);
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);

    }

    //delete
    @DeleteMapping("/{categoryId}")
    public  ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable Long categoryId){
        log.info("Initiating request for delete category with:{}",categoryId);
        categoryService.delete(categoryId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Category is deleted successfully !!").status(HttpStatus.OK).success(true).build();
        log.info("Completed request for deleted category with:{}",categoryId);
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);

    }

    //get all

    @GetMapping
    public  ResponseEntity<PageableResponse<CategoryDto>> getAll(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        log.info("Initiating request for getAll category");
        PageableResponse<CategoryDto> pageableResponse = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        log.info("Completed request for getAll category");
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    //get single

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingle(@PathVariable Long categoryId){
        log.info("Initiating request for get single category with:{}",categoryId);
        CategoryDto categoryDto = categoryService.get(categoryId);
        log.info("Completed request for get single category with:{}",categoryId);
        return ResponseEntity.ok(categoryDto);

    }
    //cover image
    @PostMapping("/coverImage/{categoryId}")
    public ResponseEntity<ImageResponse>coverImage(
            @PathVariable Long categoryId ,@RequestParam("coverImage") MultipartFile coverImage) throws IOException {

        log.info("Initiating request for cover image with : {}",categoryId);
        String image = this.coverService.coverImage(coverImage, coverImagePath);
        CategoryDto single = categoryService.get(categoryId);
        single.setCoverImage(image);
        CategoryDto update = categoryService.update(single, categoryId);
        ImageResponse imageResponse = ImageResponse.builder().imageName(image).message("Image successfully uploaded !!").success(true).status(HttpStatus.OK).build();
        log.info("Completed request for cover image : {}",categoryId);
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }
    //search keyword
    @GetMapping("/search/{keyword}")
    public  ResponseEntity<List<CategoryDto>> searchCategory(@PathVariable String keyword){

        log.info("Initiating request for get search category with : {}",keyword);
        log.info("Completed request for get search category with : {}",keyword);
        return new ResponseEntity<>(categoryService.searchCategory(keyword),HttpStatus.OK);
    }
    //serve image
    @GetMapping("/image/{categoryId}")
    public  void serveCoverImage(@PathVariable Long categoryId, HttpServletResponse response) throws IOException {

        log.info("Initiating request to serve image with : {}",categoryId);
        CategoryDto categoryDto = categoryService.get(categoryId);
        log.info("Category image name : {} ",categoryDto.getCoverImage());
        InputStream resource = coverService.getResource(coverImagePath, categoryDto.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
        log.info("Completed request for serve image with : {}",categoryId);

    }
}
