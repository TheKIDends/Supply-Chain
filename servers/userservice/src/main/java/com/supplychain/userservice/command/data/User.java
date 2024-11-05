package com.supplychain.userservice.command.data;

import lombok.*;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User {
    @Id
    private String id;

    @Column(name = "username", length = 30, unique = true)
    private String username;
    private String password;
    private String email;

    @Column(name = "phoneNumber", length = 30, unique = true)
    private String phoneNumber;
    private String fullName;
    private boolean enabled;
    private String role;
    private String designation;

}
