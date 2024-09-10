package com.uzay.securityschool.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "kullanici",uniqueConstraints ={
        @UniqueConstraint(
                name = "uniqusername",columnNames ="username" ),
        @UniqueConstraint(
                name = "uniqemail",columnNames = "email"
        )
}



)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    @NotBlank(message = "username boş olamaz")
    private String username;
    @NotBlank(message = "şifre boş olamaz")
    private String password;
    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonManagedReference
    List<Role> roles;

}
