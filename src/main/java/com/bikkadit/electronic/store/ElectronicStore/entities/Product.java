package com.bikkadit.electronic.store.ElectronicStore.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends  BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
