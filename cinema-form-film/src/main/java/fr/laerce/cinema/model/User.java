package fr.laerce.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;
import java.util.Set;
@Entity(name = "User")
@Table(name="user", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Basic
    @Column(name = "name", nullable = false, length = 100)
    @NotEmpty(message = "Please provide your  name")
    private String name;

    @Basic
    @Column(name = "givenname", nullable = true, length = 30)
    @NotEmpty(message = "Please provide your first name")
    private String givenname;

    @Basic
    @Column(name = "login", nullable = false, length = 20)
    private String login;



    @Column(name = "confirmation_token")
    private String confirmationToken;

    @Column(name = "enable")
    private boolean enable;


    @Basic
    @Column(name = "password", nullable = false, length = 120)
    private String password;

    @Basic
    @Column(name = "mail", nullable = false, length = 100)
    @Email(message = "Please provide a valid e-mail")
    @NotBlank(message ="Please provide an e-mail")
    private String mail;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name="user_group",
            joinColumns =@JoinColumn(name = "id_user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_group", referencedColumnName = "id"))
    private Set<Groups>groups;

    public void setGroups(Set<Groups> groups) {
        this.groups = groups;
    }

    public Set<Groups> getGroups() {
        return groups;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGivenname() {
        return givenname;
    }

    public void setGivenname(String givenname) {
        this.givenname = givenname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId() &&
                Objects.equals(getName(), user.getName()) &&
                Objects.equals(getGivenname(), user.getGivenname()) &&
                Objects.equals(getLogin(), user.getLogin()) &&
                Objects.equals(getMail(), user.getMail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getGivenname(), getLogin(), getMail());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", givenname='" + givenname + '\'' +
                ", login='" + login + '\'' +
                ", confirmationToken='" + confirmationToken + '\'' +
                ", enable=" + enable +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                ", groups=" + groups +
                '}';
    }

    //    public String getConfirmationToken() {
//        return confirmationToken;
//    }
//
//    public void setConfirmationToken(String confirmationToken) {
//        this.confirmationToken = confirmationToken;
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getGivenname() {
//        return givenname;
//    }
//
//    public void setGivenname(String givenname) {
//        this.givenname = givenname;
//    }
//
//    public String getLogin() {
//        return login;
//    }
//
//    public void setLogin(String login) {
//        this.login = login;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getMail() {
//        return mail;
//    }
//
//    public void setMail(String mail) {
//        this.mail = mail;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof User)) return false;
//        User user = (User) o;
//        return getId() == user.getId() &&
//                Objects.equals(getName(), user.getName()) &&
//                Objects.equals(getGivenname(), user.getGivenname()) &&
//                Objects.equals(getLogin(), user.getLogin()) &&
//                Objects.equals(getPassword(), user.getPassword()) &&
//                Objects.equals(getMail(), user.getMail());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getId(), getName(), getGivenname(), getLogin(), getPassword(), getMail());
//    }
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", givenname='" + givenname + '\'' +
//                ", login='" + login + '\'' +
//                ", password='" + password + '\'' +
//                ", mail='" + mail + '\'' +
//                '}';
//    }
}
