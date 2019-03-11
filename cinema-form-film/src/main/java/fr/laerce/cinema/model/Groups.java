package fr.laerce.cinema.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name= "GROUPS")
public class Groups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Basic
    @Column(name = "name", nullable = true, length = 60)
    private String name;

    @Basic
    @Column(name = "role", nullable = true, length = 60)
    private String role;

    @ManyToMany(mappedBy = "groups")
    private Set<User> users;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Groups)) return false;
        Groups groups = (Groups) o;
        return getId() == groups.getId() &&
                Objects.equals(getName(), groups.getName()) &&
                Objects.equals(getRole(), groups.getRole()) &&
                Objects.equals(getUsers(), groups.getUsers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getRole(), getUsers());
    }

    @Override
    public String toString() {
        return "Groups{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", users=" + users +
                '}';
    }
}
