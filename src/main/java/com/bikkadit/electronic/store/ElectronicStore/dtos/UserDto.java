package com.bikkadit.electronic.store.ElectronicStore.dtos;

import com.bikkadit.electronic.store.ElectronicStore.validate.ImageNameValid;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto extends BaseDto{

    private Long userId;

    @Size(min=3,max=20,message = "Invalid Name!!")
    private String name;

//    @Email(message="Invalid User Email !!")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$",message = "Invalid User Email !!")
    @NotBlank(message = "Email is required !!")
    private String email;

    @NotBlank(message = "Password is Required !!")
    private String password;

    @Size(min = 4,max = 6,message = "Invalid gender !!")
    private String gender;

    @NotBlank(message = "Write something about yourself !!")
    private String about;

    @ImageNameValid
    private String imageName;
}
