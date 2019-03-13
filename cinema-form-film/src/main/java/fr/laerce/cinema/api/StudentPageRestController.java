package fr.laerce.cinema.api;


import fr.laerce.cinema.domain.Student;
import fr.laerce.cinema.model.Play;
import fr.laerce.cinema.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2018/6/15.
 */
@RestController
//@RequestMapping("/studentsApi")
public class StudentPageRestController {
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/studentPage", method = RequestMethod.GET)
    public Page<Student> queryByPage(Pageable pageable) {
        Page<Student> pageInfo = studentService.listByPage(pageable);
        return pageInfo;

    }
}