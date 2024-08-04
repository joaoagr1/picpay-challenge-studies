package com.picpay.challenge.domain.user;

import com.picpay.challenge.dtos.UserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name="users")
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String document;

    @Column(unique = true)
    private String email;

    private String password;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User(UserDTO data) {

        this.firstName = data.firstName();
        this.balance = data.balance();
        this.document = data.document();
        this.email = data.email();
        this.lastName = data.lastName();
        this.password = data.password();
        this.userType = data.userType();

    }
}
