package com.ShareDear.project;

import com.ShareDear.project.entities.Post;
import com.ShareDear.project.entities.Request;
import com.ShareDear.project.entities.User;
import com.ShareDear.project.service.ServiceDao;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args); }
}


