package com.example.spring.boot_security.service;

import com.example.spring.boot_security.dao.UserDao;
import com.example.spring.boot_security.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class UserServiceImpl implements UserService  {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        userDao.addUser(user);
    }
    @Override
    @Transactional
    public void editUser(User user) {
        userDao.editUser(user);
    }
    @Override
    @Transactional
    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }
    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
    @Transactional
    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }


}

