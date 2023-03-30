package com.bikkadit.electronic.store.ElectronicStore.controller;

import ch.qos.logback.core.boolex.EvaluationException;
import com.bikkadit.electronic.store.ElectronicStore.dtos.ApiResponseMessage;
import com.bikkadit.electronic.store.ElectronicStore.dtos.ImageResponse;
import com.bikkadit.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.bikkadit.electronic.store.ElectronicStore.dtos.UserDto;
import com.bikkadit.electronic.store.ElectronicStore.services.FileService;
import com.bikkadit.electronic.store.ElectronicStore.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@RestController
@RequestMapping("/users")
public class UserController {

    Logger logger= LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    //create
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){

        logger.info("Initiating request for create user");
        UserDto userDto1 = userService.createUser(userDto);
        logger.info("Completed request for create user");
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);

    }
    //update
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable ("userId") Long userId,@Valid @RequestBody UserDto userDto){

        logger.info("Initiating request for update user : with{}",userId);
        UserDto updatedUserDto = userService.updateUser(userDto, userId);
        logger.info("Completed request for update user : with{}",userId);
        return new ResponseEntity<>(updatedUserDto,HttpStatus.OK);

    }


    //delete

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable Long userId){

        logger.info("Initiating request for delete user : with{}",userId);

        userService.deleteUser(userId);
        ApiResponseMessage message = ApiResponseMessage
                .builder()
                .message("User is deleted successfully!!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        logger.info("Completed request for delete user : with {}",userId);
        return  new ResponseEntity<>(message,HttpStatus.OK);
    }
    //getAll

    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "name",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        logger.info("Initiating request for get all users");
        logger.info("Completed request for get all users");

        return new ResponseEntity<>(userService.getAllUser(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    //getSingle

    @GetMapping("/{userId}")
    public  ResponseEntity<UserDto> getUser(@PathVariable Long userId){

        logger.info("Initiating request for single user : with{}",userId);
        logger.info("Completed request for single user : with{}",userId);
        return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
    }

    //getByEmail

    @GetMapping("/email/{email}")
    public  ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){

        logger.info("Initiating request for get email user with : {}",email);
        logger.info("Completed request for get email user with : {}",email);
        return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
    }

    // search user

    @GetMapping("/search/{keyword}")
    public  ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword){

        logger.info("Initiating request for get search user with : {}",keyword);
        logger.info("Completed request for get search user with : {}",keyword);
        return new ResponseEntity<>(userService.searchUser(keyword),HttpStatus.OK);
    }

    //upload user image

    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam ("userImage")MultipartFile image,@PathVariable Long userId) throws IOException {

        logger.info("Initiating request to upload image");
        String imageName = fileService.uploadImage(image, imageUploadPath);
        UserDto user = userService.getUserById(userId);
        user.setImageName(imageName);
        UserDto userDto = userService.updateUser(user, userId);

        ImageResponse imageResponse=ImageResponse.builder().imageName(imageName).message("Image uploaded successfully !!").success(true).status(HttpStatus.CREATED).build();
        logger.info("Completed request to upload image");

        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }
    //serve user image

    @GetMapping("/image/{userId}")
    public  void serveUserImage(@PathVariable Long userId, HttpServletResponse response) throws IOException {

        logger.info("Initiating request to serve image");
        UserDto user = userService.getUserById(userId);
        logger.info("User image name : {} ",user.getImageName());
        InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
        logger.info("Completed request for serve image");

    }
}
