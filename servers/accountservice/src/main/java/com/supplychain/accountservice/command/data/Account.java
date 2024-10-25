package com.supplychain.accountservice.command.data;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Account{
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(columnDefinition = "CHAR(32)")
    private String id;

    private String username;
    private String password;
    private String email;

//    @Column(name = "phoneNumber", length = 30, unique = true)
    private String phoneNumber;
    private String fullName;
    private boolean enabled;
    private String role;
    private String designation;

}
