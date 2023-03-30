package com.bikkadit.electronic.store.ElectronicStore.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto extends BaseDto{

    private Long categoryId;

    @NotBlank(message = "Title is required !!")
  // @Min(value = 4,message = "Title must be of minimum 4 characters !!")
    @Size(min=4,message = "Title must be of min 4 characters !!")
    private String title;

    @NotBlank(message = "Description required !!")
    private String description;

    private String coverImage;

}
