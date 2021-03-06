package fr.laerce.cinema.dao;


import fr.laerce.cinema.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
    User findByName(String name);
    User findByMail(String mail);
    List<User> findAll();
    User findByConfirmationToken(String confirmationToken);
}
