package fr.laerce.cinema.web;

import fr.laerce.cinema.dao.UserDao;
import fr.laerce.cinema.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserDao userDao;

    @GetMapping("/connection")
    public String connect(){
        return "user/connection";
    }
    @PostMapping("/connection")
    public String validation(@RequestParam("login") String login, @RequestParam("pass") String pass, HttpSession session){
        for (User user:userDao.findAll ())
        {
            if (user.getLogin ().equals (login)) {
                if (user.getPassword ().equals (pass)) {
                    session.setAttribute ("user", user);
                }
            }
        }
        return "redirect:/";
    }
    @GetMapping("/form")
    public String inscrit(Model model){
        model.addAttribute ("user", new User ());
        return "user/form";
    }
    @PostMapping("/form")
    public String form (@ModelAttribute User newuser) {
        userDao.save(newuser);
        return "redirect:/";
    }
}
