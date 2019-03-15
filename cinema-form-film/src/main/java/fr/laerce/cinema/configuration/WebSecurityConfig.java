package fr.laerce.cinema.configuration;

import fr.laerce.cinema.service.JpaUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Projet thyme-security
 * Pour LAERCE SAS
 * <p>
 * Créé le  21/03/2017.
 *
 * @author fred
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
   private JpaUserDetailsService jpaUserDetailsService;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpSession httpSession;



    @Resource
    public void setUserDetailsService(JpaUserDetailsService jpaUserDetailsService){
        this.jpaUserDetailsService = jpaUserDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/resources/**").anyRequest();
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/", "/home","/css/**","/register","/confirm").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")

                .permitAll().defaultSuccessUrl("/")
                .and()
                .logout().logoutUrl("/logout")
//                .logoutSuccessUrl(("/"))
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/login");

    }




    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("recup").password(bCryptPasswordEncoder().encode("recup")).roles("ADMIN","USER").authorities("WITHDRAW","DEPOSIT","ADMIN")
//                .and()
//                .withUser("joe").password("123").roles("ADMIN");
        auth.userDetailsService(jpaUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
    @EventListener
    public  void saveSession(InteractiveAuthenticationSuccessEvent event){
        System.out.println("fgfhjgjkjkjkh");
        UserDetails userDetails = (UserDetails)event.getAuthentication().getPrincipal();
        httpSession.setAttribute("name", userDetails.getUsername());
    }

}
