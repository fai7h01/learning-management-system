package com.leverx.lms.learningmanagementsystem.student.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.lms.learningmanagementsystem.lesson.controller.LessonController;
import com.leverx.lms.learningmanagementsystem.student.dto.StudentDto;
import com.leverx.lms.learningmanagementsystem.student.service.StudentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper mapper;


    private StudentDto sampleStudent(UUID id) {
        return StudentDto.builder()
                .id(id)
                .firstName("John")
                .lastName("Doe")
                .email("luka@example.com")
                .dateOfBirth(LocalDate.of(1997, 4, 12))
                .coins(null)
                .build();
    }

    @Test
    @DisplayName("GET /api/v1/students returns 200 and page metadata")
    void getAllStudents() throws Exception {
        Page<StudentDto> page = new PageImpl<>(
                List.of(sampleStudent(UUID.randomUUID())),
                PageRequest.of(0, 10),
                1
        );
        given(studentService.getAll(any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.totalElements").value(1));

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(studentService).getAll(captor.capture());
        assertThat(captor.getValue().getPageSize()).isEqualTo(10);
    }

    @Test
    @DisplayName("GET /api/v1/students/{id} returns student payload")
    void getStudentById() throws Exception {
        UUID id = UUID.randomUUID();
        StudentDto dto = sampleStudent(id);
        given(studentService.getById(id)).willReturn(dto);

        mockMvc.perform(get("/api/v1/students/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id.toString()));

        verify(studentService).getById(id);
    }

    @Test
    @DisplayName("POST /api/v1/students returns 201 when created")
    void createStudent() throws Exception {
        StudentDto payload = sampleStudent(null);
        UUID newId = UUID.randomUUID();
        StudentDto returned = sampleStudent(newId);

        given(studentService.create(any(StudentDto.class))).willReturn(returned);

        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(newId.toString()));

        verify(studentService).create(any(StudentDto.class));
    }

    @Test
    @DisplayName("PUT /api/v1/students/{id} updates a student and returns 200")
    void updateStudent() throws Exception {
        UUID id = UUID.randomUUID();
        StudentDto payload = sampleStudent(null);
        StudentDto returned = sampleStudent(id);

        given(studentService.update(eq(id), any(StudentDto.class))).willReturn(returned);

        mockMvc.perform(put("/api/v1/students/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id.toString()));

        verify(studentService).update(eq(id), any(StudentDto.class));
    }

    @Test
    @DisplayName("DELETE /api/v1/students/{id} returns 204")
    void deleteStudent() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/students/{id}", id))
                .andExpect(status().isNoContent());

        verify(studentService).delete(id);
    }

    @Test
    @DisplayName("POST /api/v1/students/{studentId}/courses/{courseId} buys course and returns 204")
    void buyCourse() throws Exception {
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();

        mockMvc.perform(post("/api/v1/students/{sid}/courses/{cid}", studentId, courseId))
                .andExpect(status().isNoContent());

        verify(studentService).buyCourse(studentId, courseId);
    }

}