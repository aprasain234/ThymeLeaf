package com.ashishprasain.conroller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ashishprasain.domain.Student;
import com.ashishprasain.repositary.StudentRepository;


@Controller
@RequestMapping("/")
public class StudentController {

    private final StudentRepository studentRepository;
    
	//public static String uploadDirectory = ClassLoader.getSystemClassLoader().getResource("static/uploads");
	public static String uploadDirectory = "C:\\Users\\apras\\OneDrive\\Desktop\\uploads";

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    
    @RequestMapping("/")
    public String homePage() {
    	return "home";
    }

    @GetMapping("/students/signup")
    public String showSignUpForm(Student student) {
        return "add-student";
    }

    @GetMapping("/students/list")
    public String showUpdateForm(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("dir", uploadDirectory);
        return "studentsList";
    }

    @PostMapping("/students/add")
    public String addStudent(@Valid Student student, BindingResult result, Model model, @RequestParam("files") MultipartFile file) {
        if (result.hasErrors()) {
            return "add-student";
        }
        Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
		student.setProfilePicture(file.getOriginalFilename());
        //fileNames.append(file.getOriginalFilename() + " ");
		try {
			Files.write(fileNameAndPath, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
        studentRepository.save(student);
        return "redirect:list";
    }

    @GetMapping("/students/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Optional<Student> student = studentRepository.findById(id);
            //.orElseThrow(() - > new IllegalArgumentException("Invalid student Id:" + id));
        model.addAttribute("student", student);
        return "update-student";

    }

    @PostMapping("/students/update/{id}")
    public String updateStudent(@PathVariable("id") long id, @Valid Student student, BindingResult result,
        Model model) {
        if (result.hasErrors()) {
            student.setId(id);
            return "update-student";
        }
        studentRepository.save(student);
        model.addAttribute("students", studentRepository.findAll());
        return "studentsList";
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") long id, Model model) {
        studentRepository.deleteById(id);
        model.addAttribute("students", studentRepository.findAll());
        return "studentsList";
        
        
        
    }
    
    /*
    @RequestMapping("/students/add")
	public String upload(Model model, @RequestParam("files") MultipartFile[] files) {
		StringBuilder fileNames = new StringBuilder();
		for(MultipartFile file: files) {
			Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
			fileNames.append(file.getOriginalFilename() + " ");
			try {
				Files.write(fileNameAndPath, file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("msg","Files uploaded Successfully"+ fileNames.toString());
		return "uploadstatusview";
		
		*/
	
}