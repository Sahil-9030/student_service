package com.school.student_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.student_service.controller.StudentController;
import com.school.student_service.model.Student;
import com.school.student_service.repository.StudentRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

//@SpringBootTest
@WebMvcTest(StudentController.class)
class StudentServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    // ---------------- GET ALL STUDENTS ----------------
    @Test
    void getAllStudents_success() throws Exception {

        Student s1 = new Student(1, "Rahul", "rahul@gmail.com", new Date(), 80);
        Student s2 = new Student(2, "Amit", "amit@gmail.com", new Date(), 70);

        when(studentRepository.findAll()).thenReturn(List.of(s1, s2));

        mockMvc.perform(get("/students/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("rahul@gmail.com"));
    }

    @Test
    void getAllStudents_empty() throws Exception {

        when(studentRepository.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/students/"))
                .andExpect(status().isBadRequest());
    }

    // ---------------- ADD STUDENT ----------------
    @Test
    void addStudent_success() throws Exception {

        Student student = new Student("Rahul", "rahul@gmail.com", new Date(), 90);

        when(studentRepository.findAll()).thenReturn(List.of());
        when(studentRepository.save(student)).thenReturn(student);

        mockMvc.perform(post("/students/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("rahul@gmail.com")));

    }

    @Test
    void addStudent_duplicateEmail() throws Exception {

        Student student = new Student("Rahul", "rahul@gmail.com", new Date(), 90);

        when(studentRepository.findAll()).thenReturn(List.of(student));

        mockMvc.perform(post("/students/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isBadRequest());
    }

    // ---------------- GET STUDENT BY ID ----------------
    @Test
    void getStudentById_success() throws Exception {

        Student student = new Student(1, "Rahul", "rahul@gmail.com", new Date(), 85);

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getStudentById_notFound() throws Exception {

        when(studentRepository.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isBadRequest());
    }

    // ---------------- UPDATE STUDENT ----------------
    @Test
    void updateStudent_success() throws Exception {

        Student student = new Student(1, "Rahul", "rahul@gmail.com", new Date(), 60);

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(studentRepository.save(student)).thenReturn(student);

        mockMvc.perform(put("/students/1")
                .param("id", "1")
                .param("marks", "95"))
                .andExpect(status().isNonAuthoritativeInformation());
    }

    @Test
    void updateStudent_notFound() throws Exception {

        when(studentRepository.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(put("/students/1")
                .param("id", "1")
                .param("marks", "90"))
                .andExpect(status().isUnauthorized());
    }

    // ---------------- DELETE STUDENT ----------------
    @Test
    void deleteStudent_success() throws Exception {

        Student student = new Student(1, "Rahul", "rahul@gmail.com", new Date(), 80);

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).deleteById(1);

        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteStudent_notFound() throws Exception {

        when(studentRepository.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isUnauthorized());
    }
}

