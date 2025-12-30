package com.school.student_service.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Student")
public class Student {

	public Student(int id, String s_name, String email, Date dob, int marks) {
		super();
		this.id = id;
		this.s_name = s_name;
		this.email = email;
		this.dob = dob;
		this.marks = marks;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private String s_name;
	private String email;
	private Date dob;
	private int marks;
	public String getS_name() {
		return s_name;
	}
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public int getMarks() {
		return marks;
	}
	public void setMarks(int marks) {
		this.marks = marks;
	}
	
	public Student() {}
	public Student(String s_name, String email, Date dob, int marks) {
		super();
		this.s_name = s_name;
		this.email = email;
		this.dob = dob;
		this.marks = marks;
	};
	
	
}
