package com.bikkadit.electronic.store.ElectronicStore.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends  BaseEntity{

    @Id
    private Long productId;
    private  String title;
    @Column(length = 10000)
    private String description;
    private  Double price;
    private  Double discountedPrice;
    private Integer quantity;
    private Date addedDate;
    private boolean Live;
    private boolean stock;


}
