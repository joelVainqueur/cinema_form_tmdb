package fr.laerce.cinema.web;


import fr.laerce.cinema.dao.StudentPageRepository;

import fr.laerce.cinema.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Created on 2018/6/15.
 */
@Controller
//@RequestMapping("/studentsApi")
public class StudentPageController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentPageRepository studentPageRepository;
//    @RequestMapping(value = "/studentPage", method = RequestMethod.GET)
//    public Page<Student> queryByPage(Pageable pageable) {
//        Page<Student> pageInfo = studentService.listByPage(pageable);
//        return pageInfo;
//    }
    @GetMapping("/pagination")
    public String page(Model model){
//        model.addAttribute("data", studentPageRepository.findAll(Pageable.unpaged()));
        Pageable pageable = PageRequest.of(0 , 20);
        model.addAttribute("table", studentPageRepository.findAll(pageable));
        return "testpagination";
    }




}