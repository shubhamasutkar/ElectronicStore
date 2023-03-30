package com.bikkadit.electronic.store.ElectronicStore.dtos;

import com.bikkadit.electronic.store.ElectronicStore.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseDto {


    private LocalDate createDate;
    private LocalDate updateDate;
    private String isactive;
}
