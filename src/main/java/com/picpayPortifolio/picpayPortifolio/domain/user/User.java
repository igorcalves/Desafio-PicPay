package com.picpayPortifolio.picpayPortifolio.domain.user;

import com.picpayPortifolio.picpayPortifolio.dtos.UserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class User{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firsName;
    private String lastName;
    @Column(unique = true)
    private String document;
    @Column(unique = true)
    private String email;
    private String passWord;
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private UserType userType;

    public  User(UserDTO data) {
        this.firsName = data.firstName();
        this.lastName = data.lastName();
        this.document = data.document();
        this.balance = data.balance();
        this.userType = data.userType();
        this.passWord = data.password();
        this.email = data.email();

    }


}
