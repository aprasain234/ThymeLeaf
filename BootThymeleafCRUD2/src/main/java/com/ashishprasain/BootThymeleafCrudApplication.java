package com.ashishprasain;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ashishprasain.conroller.StudentController;

@SpringBootApplication
public class BootThymeleafCrudApplication {

	public static void main(String[] args) {
		new File(StudentController.uploadDirectory).mkdir();
		SpringApplication.run(BootThymeleafCrudApplication.class, args);
	}

}
