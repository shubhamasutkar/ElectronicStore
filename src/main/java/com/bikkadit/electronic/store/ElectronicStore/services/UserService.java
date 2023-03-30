package com.bikkadit.electronic.store.ElectronicStore.services;

import com.bikkadit.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.bikkadit.electronic.store.ElectronicStore.dtos.UserDto;

import java.util.List;

public interface UserService {

    //create

    UserDto createUser(UserDto userDto);


    //update

    UserDto updateUser(UserDto userDto, Long userId);


    //delete


    void deleteUser(Long userId);


    //get all users


    PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);


    //get single user by id


    UserDto getUserById(Long userId);


    //get single user by email

    UserDto getUserByEmail(String email);


    //search user

    List<UserDto> searchUser(String keyword);
}
