package fr.laerce.cinema.dao;



import fr.laerce.cinema.domain.Student;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created on 2018/6/15.
 */
/*PagingAndSortingRepository*/
public interface StudentPageRepository extends PagingAndSortingRepository<Student,Long> {

}