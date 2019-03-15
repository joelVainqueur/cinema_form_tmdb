package fr.laerce.cinema.web;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import fr.laerce.cinema.dao.UserDao;
import fr.laerce.cinema.model.User;
import fr.laerce.cinema.service.EmailService;
import fr.laerce.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@Controller
//@RequestMapping(value = "/user")
public class UserController {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;
    private EmailService emailService;

    @Autowired
    UserDao userDao;

    @Autowired
    private HttpSession httpSession;


    @Autowired
    public UserController(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService, EmailService emailService){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.emailService = emailService;
    }



    // Return registration form template
    @RequestMapping(value="register", method = RequestMethod.GET)
    public ModelAndView showRegistrationPage(ModelAndView modelAndView, User user){
        modelAndView.addObject("user", user);
        modelAndView.setViewName("register");
        return modelAndView;
    }


    // Process form input data
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ModelAndView processRegistrationForm(ModelAndView modelAndView, @Valid User user, BindingResult bindingResult, HttpServletRequest request) {

        // Lookup user in database by e-mail
        User userExists = userService.findByEmail(user.getMail());

        System.out.println(userExists);

        if (userExists != null) {
            modelAndView.addObject("alreadyRegisteredMessage", "Oops!  There is already a user registered with the email provided.");
            modelAndView.setViewName("register");
            bindingResult.reject("mail");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("register");
        } else { // new user so we create user and send confirmation e-mail

            // Disable user until they click on confirmation link in email
            user.setEnable(true);

            // Generate random 36-character string token for confirmation link
            /*Générer un jeton de chaîne aléatoire de 36 caractères pour le lien de confirmation*/
            user.setConfirmationToken(UUID.randomUUID().toString());

            userService.saveUser(user);

            String appUrl = request.getScheme() + "://" + request.getServerName();
            String port = request.getLocalPort()+"";
            System.out.println(port);
            SimpleMailMessage registrationEmail = new SimpleMailMessage();
            registrationEmail.setTo(user.getMail());
            registrationEmail.setSubject("Registration Confirmation");
            registrationEmail.setText("To confirm your e-mail address, please click the link below:\n"
                    + appUrl +":"+port+ "/confirm?token=" + user.getConfirmationToken());
            registrationEmail.setFrom("joelseri@gmail.com");

            emailService.sendEmail(registrationEmail);

            modelAndView.addObject("confirmationMessage", "A confirmation e-mail has been sent to " + user.getMail());
            modelAndView.setViewName("register");
        }

        return modelAndView;
    }



    // Process confirmation link
    @RequestMapping(value="confirm", method = RequestMethod.GET)
    public ModelAndView showConfirmationPage(ModelAndView modelAndView, @RequestParam("token") String token) {

        User user = userService.findByConfirmationToken(token);

        if (user == null) { // No token found in DB
            modelAndView.addObject("invalidToken", "Oops!  This is an invalid confirmation link.");
        } else { // Token found
            modelAndView.addObject("confirmationToken", user.getConfirmationToken());
        }

        modelAndView.setViewName("confirm");
        return modelAndView;
    }


    // Process confirmation link
    @RequestMapping(value="confirm", method = RequestMethod.POST)
    public ModelAndView processConfirmationForm(ModelAndView modelAndView, BindingResult bindingResult, @RequestParam Map requestParams, RedirectAttributes redir) {

        modelAndView.setViewName("confirm");
        //vérifier le mot de l'utisateur
        Zxcvbn passwordCheck = new Zxcvbn();

        Strength strength = passwordCheck.measure(requestParams.get("password").toString());

        if (strength.getScore() < 3) {
            bindingResult.reject("password");

            redir.addFlashAttribute("errorMessage", "Your password is too weak.  Choose a stronger one.");

            modelAndView.setViewName("redirect:/confirm?token=" + requestParams.get("token"));
            System.out.println(requestParams.get("token"));
            return modelAndView;
        }

        // Find the user associated with the reset token
        User user = userService.findByConfirmationToken(requestParams.get("token").toString());

        // Set new password
        user.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password").toString()));

        // Set user to enabled
        user.setEnable(false);

        // Save user
        userService.saveUser(user);

        modelAndView.addObject("successMessage", "Your password has been set!");
        return modelAndView;
    }



//    @GetMapping("/connection")
//    public String connect(){
//        return "user/connection";
//    }
//    @PostMapping("/connection")
//    public String validation(@RequestParam("login") String login, @RequestParam("pass") String pass, HttpSession session){
//        for (User user:userDao.findAll ())
//        {
//            if (user.getLogin ().equals (login)) {
//                if (user.getPassword ().equals (pass)) {
//                    session.setAttribute ("user", user);
//                }
//            }
//        }
//        return "redirect:/";
//    }
//    @GetMapping("/form")
//    public String inscrit(Model model){
//        model.addAttribute ("user", new User ());
//        return "user/form";
//    }
//    @GetMapping("/register")
//    public String register(Model model){
//        model.addAttribute ("user", new User ());
//        return "register";
//    }
    @GetMapping("/login")
    public String login(Model model){
//        session.setAttribute ("user", new User ());
//        session.setAttribute ("user", userDao.findAll());
//        model.addAttribute ("user", userDao.findByName("bientot"));
        return "login";
    }
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth !=null)
        {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "index";
    }
//    @PostMapping("/form")
//    public String form (@ModelAttribute User newuser) {
//        userDao.save(newuser);
//        return "redirect:/";
//    }
//    @GetMapping("/oublie")
//    public String oublie(Model model){
//        model.addAttribute ("user", new User ());
//        return "forgot-password";
//    }
}
