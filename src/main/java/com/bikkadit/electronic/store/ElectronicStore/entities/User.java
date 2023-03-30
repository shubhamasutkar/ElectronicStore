package com.bikkadit.electronic.store.ElectronicStore.entities;

import lombok.*;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.apache.catalina.WebResourceRoot;
import org.hibernate.id.IdentityGenerator;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")

public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name="user_name")
    private String name;

    @Column(name="user_email")
    private String email;

    @Column(name="user_password",length = 100)
    private String password;

    private String gender;

    @Column(name="user_about",length= 1000)
    private String about;

    @Column(name="user_image_name")
    private String imageName;
}
