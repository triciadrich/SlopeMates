package com.example.slopemates.Services;

import com.example.slopemates.Models.RequestStatus;
import com.example.slopemates.Models.User;
import com.example.slopemates.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCrypt;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }



    public User editUser(User user){
        return this.userRepository.save(user);
    }

    public void deleteUser(Long id){
        this.userRepository.deleteById(id);
    }



    public void sendConnectionRequest(User requester, User recipient ){
        requester = userRepository.findById(requester.getId()).orElseThrow();
        recipient = userRepository.findById(recipient.getId()).orElseThrow();

        recipient.getConnectionRequests().add(requester);

        userRepository.save(recipient);


    }

    public void addConnection(User req, User rec){
        req = userRepository.findById(req.getId()).orElseThrow();
        rec = userRepository.findById(rec.getId()).orElseThrow();

            req.getConnections().add(rec);
            rec.getConnections().add(req);
            rec.getConnectionRequests().remove(req);
            userRepository.save(req);
            userRepository.save(rec);


        }


    public void declineConnection(User req, User rec){
        rec.getConnectionRequests().remove(req);

        userRepository.save(rec);

    }





}
