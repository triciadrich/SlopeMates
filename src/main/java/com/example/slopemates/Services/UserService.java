package com.example.slopemates.Services;

import com.example.slopemates.Models.User;
import com.example.slopemates.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCrypt;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import static java.time.LocalDate.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user){
    try{
        user.setUserName(user.getUserName());
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        return userRepository.save(user);

    } catch (Exception e) {
        throw new RuntimeException(e);
    }

    }

    public boolean authUser(String email, String password){
        User user = userRepository.findByEmail(email);
        if(user == null){
            return false;
        }else{
            return BCrypt.checkpw(password, user.getPassword());
        }
    }

    public User findByUserId(Long id){
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public User editUser(User user){
        return this.userRepository.save(user);
    }

    public void deleteUser(Long id){
        this.userRepository.deleteById(id);
    }

    public void addConnection(Long userId, Long connectionId){
        User user = userRepository.findById(userId).orElse(null);
        User connection = userRepository.findById(connectionId).orElse(null);
        if (user != null && connection != null){
            user.getConnections().add(connection);
            userRepository.save(user);
        }
    }
}
