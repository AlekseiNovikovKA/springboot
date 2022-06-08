package com.example.springboot.service;

import com.example.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.springboot.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @Transactional
    @Override
    public User getUserById(Long id) {
        return userRepository.getOne(id);
    }
    @Transactional
    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }
    @Transactional
    @Override
    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }
    @Transactional
    @Override
    public void updateUser(User user, Long id) {
        userRepository.saveAndFlush(user);
    }
}
