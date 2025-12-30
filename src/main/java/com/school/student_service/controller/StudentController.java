package com.school.student_service.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.school.student_service.model.Student;
import com.school.student_service.repository.StudentRepository;

@RestController
@RequestMapping("/students")
public class StudentController {
	
	@Autowired
	StudentRepository studentRepository;
	
	@GetMapping("/")
	public ResponseEntity<Object> getAllStudent(){
		
		try {
			List<Student> list = studentRepository.findAll();
			
			if(list.isEmpty()) {
				return ResponseEntity.status(400).body("No Students to show");
			}
			
			return ResponseEntity.status(200).body(list);
		}catch(Exception e) {
			return ResponseEntity.status(401).body("Error while getting all the students");
		}
		
	}
	
	@PostMapping("/")
	public ResponseEntity<Object> addStudent(@RequestBody Student student){
		
		try {
			List<Student> list = studentRepository.findAll();
			
			Optional<Student> s1 = list.stream().filter(f-> f.getEmail().equalsIgnoreCase(student.getEmail())).findAny();
			
			if(s1.isPresent()) {
				return ResponseEntity.status(400).body("Student with this email already exists");
			}
			
			return ResponseEntity.status(201).body(studentRepository.save(student));
		}catch(Exception e) {
			return ResponseEntity.status(402).body("Error occured" + e.getMessage());
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getStudentByID(@PathVariable int id){
		Optional<Student> s1 = studentRepository.findById(id);
		
		if(s1.isPresent()) {
			return ResponseEntity.status(200).body(s1);
		}
		return ResponseEntity.status(400).body("No student exist with this id");
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateStudent(@RequestParam int id, int marks){
		
		try {
			Optional<Student> s1 = studentRepository.findById(id);
			
			if(s1.isEmpty()) {
				return ResponseEntity.status(401).body("Student not found with this id");
			}
			
			Student s2 = s1.get();
			
			s2.setMarks(marks);
			
			return ResponseEntity.status(203).body(studentRepository.save(s2));
			
		}catch(Exception e) {
			return ResponseEntity.status(400).body("Error occured " + e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteStudentByID(@PathVariable int id){
		
		Optional<Student> s1 = studentRepository.findById(id);
		
		if(s1.isEmpty()) {
			return ResponseEntity.status(401).body("There is No Student with this id");
		}
		
		studentRepository.deleteById(id);
		
		return ResponseEntity.status(200).body("Student with - " + id + " successfully deleted");
		
	}
}
