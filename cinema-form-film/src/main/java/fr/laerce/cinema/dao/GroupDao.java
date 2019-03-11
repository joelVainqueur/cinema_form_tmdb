package fr.laerce.cinema.dao;

import fr.laerce.cinema.model.Groups;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;


@Repository
public interface GroupDao extends CrudRepository<Groups, Long> {

    HashSet<Groups> findAll();




}
