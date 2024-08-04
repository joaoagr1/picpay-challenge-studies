package com.picpay.challenge.services;

import com.picpay.challenge.domain.user.User;
import com.picpay.challenge.domain.user.UserType;
import com.picpay.challenge.dtos.UserDTO;
import com.picpay.challenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if(sender.getUserType() == UserType.MERCHANT){
            throw new Exception("Usuário do tipo logista não esta autorizado a realizar tranzações");
        }

        if(sender.getBalance().compareTo(amount)<0){
            throw new Exception("Saldo insuficiente");
        }

    }

    public User findUserById(Long id) throws Exception {

        return this.userRepository.findById(id).orElseThrow(()-> new Exception("Usuário não encontrado"));

    }


    public User saveUser(User user){

        return this.userRepository.save(user);

    }

    public User createUser(UserDTO data) {

    User newUser = new User(data);
    this.saveUser(newUser);
    return newUser;

    }


    public List<User> getAllUsers(){

        return this.userRepository.findAll();

    }


}
