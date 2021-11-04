package com.example.springbootapitest.controller;

import com.example.springbootapitest.exception.ResourceNotFoundException;
import com.example.springbootapitest.model.User;
import com.example.springbootapitest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/users")

public class userController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    //get user by id REST API
    @GetMapping("{id}")
    public ResponseEntity<User> getUserByID(@PathVariable long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id: " + id));
        return ResponseEntity.ok(user);
    }

    // update user REST API
    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User userDetails){
        User updateUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id: " + id));

        updateUser.setEMail(userDetails.getEMail());
        updateUser.setUserName(userDetails.getUserName());
        updateUser.setFirstName(userDetails.getFirstName());
        updateUser.setLastName(userDetails.getLastName());
        updateUser.setPassWord(updateUser.getPassWord());

        userRepository.save(updateUser);

        return ResponseEntity.ok(updateUser);
    }

    // delete user REST API
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id: " + id));

        userRepository.delete(user);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Create new user REST API
    @PostMapping
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }
}
