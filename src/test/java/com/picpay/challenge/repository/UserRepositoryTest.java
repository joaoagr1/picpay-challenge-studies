package com.picpay.challenge.repository;

import com.picpay.challenge.domain.user.User;
import com.picpay.challenge.domain.user.UserType;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import com.picpay.challenge.dtos.UserDTO;
import java.math.BigDecimal;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;



@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get user successfuly from database")
    void findUserByDocumentSuccess() {

        String document = "123456789";

        UserDTO data = new UserDTO(
                "João",
                "Rollo",
                document,
                new BigDecimal(1000),
                "joao@gmail.com",
                "senha123",
                UserType.COMMON
                );

        this.createUser(data);

        Optional<User> result = this.userRepository.findUserByDocument(document);

        assertThat(result.isPresent()).isTrue();


    }

    @Test
    @DisplayName("Should get user failed from database")
    void findUserByFocumentFailed(){

        String document = "23456789";
        String wrongDocument = "987654321";

        UserDTO data = new UserDTO(
                "Maria",
                "Moreira",
                document,
                new BigDecimal(1000),
                "mmoreira@gmail.com",
                "moreira123",
                UserType.COMMON
        );

        this.createUser(data);

        Optional<User> result = this.userRepository.findUserByDocument(wrongDocument);

        assertThat(result.isPresent()).isFalse();

    }



    private User createUser(UserDTO data){

        User newUser = new User(data);
        this.entityManager.persist(newUser);
        return newUser;

    }

}