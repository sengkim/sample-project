package com.sengkim.study.sample.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sengkim.study.sample.dao.UserDao;
import com.sengkim.study.sample.domain.User;

/**
 * @author sengkim
 * @Date Sep 8, 2015
 * @Email sengkim.it@gmail.com
 */
@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/dummy", method = RequestMethod.GET)
    public List<User> dummy() {

        List<User> users = new ArrayList<User>();
        User user = null;
        for (int i = 1; i <= 10; i++) {
            user = new User();
            user.setEmail("email_" + i);
            user.setFirstname("firstname_" + i);
            user.setLastname("lastname_" + i);
            user.setPassword("password_" + i);
            userDao.add(user);
            users.add(user);
        }

        return users;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return (List<User>) userDao.getAll();
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable("id") Long id) {
        return userDao.findById(id);
    }

    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public User updateUser(@RequestBody User user) {
        userDao.update(user);
        return user;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public User deleteUser(@PathVariable("id") Long id) {
        User user = userDao.findById(id);
        userDao.remove(id);
        return user;
    }
}
