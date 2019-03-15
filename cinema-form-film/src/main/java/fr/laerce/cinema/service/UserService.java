package fr.laerce.cinema.service;

import fr.laerce.cinema.dao.UserDao;
import fr.laerce.cinema.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService{
    @Autowired
    UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findByEmail(String email) {
        return userDao.findByMail(email);
    }

    public void saveUser(User user) {
        userDao.save(user);
    }
    public User findByConfirmationToken(String confirmationToken) {
        return userDao.findByConfirmationToken(confirmationToken);
    }
}