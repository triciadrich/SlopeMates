package com.example.slopemates.Services;

import com.example.slopemates.Models.ConnectionRequest;
import com.example.slopemates.Models.RequestStatus;
import com.example.slopemates.Models.User;
import com.example.slopemates.Repositories.ConnectionRequestRepository;
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
    @Autowired
    private ConnectionRequestRepository connectionRequestRepository;

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



    public void sendConnectionRequest(Long requesterId, Long recipientId ){
        User requester = userRepository.findById(requesterId).orElseThrow();
        User recipient = userRepository.findById(recipientId).orElseThrow();

        ConnectionRequest connectionRequest = new ConnectionRequest();
        connectionRequest.setRequester(requester);
        connectionRequest.setRecipient(recipient);
        connectionRequest.setRequestStatus(RequestStatus.PENDING);
        connectionRequestRepository.save(connectionRequest);


    }

    public void addConnection(Long requestId){
        ConnectionRequest request = connectionRequestRepository.findById(requestId).orElseThrow();

        if (request.getRequestStatus() == RequestStatus.PENDING){
            request.setRequestStatus(RequestStatus.ACCEPTED);

            User requester = request.getRequester();
            User recipient = request.getRecipient();

            requester.getConnections().add(recipient);
            recipient.getConnections().add(requester);

            userRepository.save(requester);
            userRepository.save(recipient);

            connectionRequestRepository.deleteById(requestId);
        }
    }

    public void declineConnection(Long requestId){
        ConnectionRequest request = connectionRequestRepository.findById(requestId).orElseThrow();

        if (request.getRequestStatus() == RequestStatus.PENDING){
            request.setRequestStatus(RequestStatus.DECLINED);
            connectionRequestRepository.deleteById(requestId);

        }
    }

    public List<ConnectionRequest> getConnectionRequests(Long userId){
        return connectionRequestRepository.findAllById(userId);
    }



}
