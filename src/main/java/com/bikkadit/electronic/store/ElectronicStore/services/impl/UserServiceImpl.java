package com.bikkadit.electronic.store.ElectronicStore.services.impl;

import com.bikkadit.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.bikkadit.electronic.store.ElectronicStore.dtos.UserDto;
import com.bikkadit.electronic.store.ElectronicStore.entities.User;
import com.bikkadit.electronic.store.ElectronicStore.exceptions.ResourceNotFoundException;
import com.bikkadit.electronic.store.ElectronicStore.helper.AppConstants;
import com.bikkadit.electronic.store.ElectronicStore.helper.Helper;
import com.bikkadit.electronic.store.ElectronicStore.repositories.UserRepository;
import com.bikkadit.electronic.store.ElectronicStore.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${user.profile.image.path}")
    private String imagePath;

    /**
     *
     * @param userDto
     * @return
     */
    @Override
    public UserDto createUser(UserDto userDto) {


        //generate unique Id in long format
       // String userId = UUID.randomUUID().toString();
        User user = dtoToEntity(userDto);

        //dto to entity

        User savedUser = userRepository.save(user);
        //entity to dto
        UserDto newDto= entityToDto(savedUser);
        newDto.setIsactive(AppConstants.YES);


        return newDto;
    }


    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not find with given Id"));
        user.setName(userDto.getName());

        //update email

        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());

        User updatedUser = userRepository.save(user);
        UserDto updatedDto = entityToDto(updatedUser);


        return updatedDto;
    }

    @Override
    public void deleteUser(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given Id!!"));

        //delete user profile image
        String fullPath = imagePath + user.getImageName();

        try{
            Path path= Paths.get(fullPath);
            Files.delete(path);
        }catch (NoSuchFileException ex){
         logger.info("User image not found in folder");
         ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // only for deleting user
        userRepository.delete(user);
    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        //initially pageNumber by default starts from 0
        //but if we want to start from 1 then pass (pageNumber-1) in the parameters
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<User> page = userRepository.findAll(pageable);

        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);
        return response;
    }

    @Override
    public UserDto getUserById(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given Id"));

        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with given Id"));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());

        return dtoList;
    }


    private UserDto entityToDto(User savedUser) {
//        UserDto userDto=   UserDto.builder()
//                .userId(savedUser.getUserId())
//                .name(savedUser.getName())
//                .email(savedUser.getEmail())
//                .password(savedUser.getPassword())
//                .about(savedUser.getAbout())
//                .gender(savedUser.getGender())
//                .imageName(savedUser.getImageName())
//                .build();

        return modelMapper.map(savedUser, UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {
//        User user=  User.builder()
//                .userId(userDto.getUserId())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .about(userDto.getAbout())
//                .gender(userDto.getGender())
//                .imageName(userDto.getImageName())
//                .build();


        return modelMapper.map(userDto,User.class);
    }
}
