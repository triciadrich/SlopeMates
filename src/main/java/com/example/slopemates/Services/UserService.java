package com.example.slopemates.Services;

import com.example.slopemates.Models.User;
import com.example.slopemates.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCrypt;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user){
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        return userRepository.save(user);
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
