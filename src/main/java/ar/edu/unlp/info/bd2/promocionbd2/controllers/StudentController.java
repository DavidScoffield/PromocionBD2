package ar.edu.unlp.info.bd2.promocionbd2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlp.info.bd2.promocionbd2.models.StudentModel;
import ar.edu.unlp.info.bd2.promocionbd2.services.StudentService;

import java.util.List;

/*TODO delete this class*/

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @RequestMapping(value = "info", method = RequestMethod.GET)
    public String info() {
        return "The application is up...";
    }

    @RequestMapping(value = "createresource", method = RequestMethod.POST)
    public String createStudent(@RequestBody StudentModel studentModel) {
        return studentService.createResource(studentModel);
    }

    @RequestMapping(value = "readresources", method = RequestMethod.GET)
    public List<StudentModel> readResources() {
        return studentService.readResources();
    }

    @RequestMapping(value = "updateresource", method = RequestMethod.PUT)
    public String updateStudent(@RequestBody StudentModel studentModel) {
        return studentService.updateResource(studentModel);
    }

    @RequestMapping(value = "deleteresource", method = RequestMethod.DELETE)
    public String deleteStudent(@RequestBody StudentModel studentModel) {
        return studentService.deleteResource(studentModel);
    }
}
