package com.bikkadit.electronic.store.ElectronicStore.dtos;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto extends  BaseDto{


    private Long productId;
    private  String title;
    private String description;
    private  Double price;
    private  Double discountedPrice;
    private Integer quantity;
    private Date addedDate;
    private boolean Live;
    private boolean stock;
}
