package com.leverx.lms.learningmanagementsystem.course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.leverx.lms.learningmanagementsystem.course.dto.CourseDto;
import com.leverx.lms.learningmanagementsystem.course.dto.CourseSettingsDto;
import com.leverx.lms.learningmanagementsystem.course.service.CourseService;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CourseService courseService;

    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private CourseDto sampleCourse() {
        return CourseDto.builder()
                .id(UUID.randomUUID())
                .title("Java 101")
                .description("Intro course")
                .price(BigDecimal.valueOf(49.99))
                .settings(CourseSettingsDto.builder()
                        .id(UUID.randomUUID())
                        .startDate(LocalDateTime.now())
                        .endDate(LocalDateTime.now().plusDays(30))
                        .isPublic(false)
                        .build())
                .build();
    }


    @Test
    @DisplayName("GET /api/v1/courses returns 200 and page metadata")
    void getAllCourses() throws Exception {
        CourseDto dto = sampleCourse();
        Page<CourseDto> page = new PageImpl<>(List.of(dto), PageRequest.of(0, 10), 1);
        given(courseService.getAll(any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/api/v1/courses")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].id").value(dto.id().toString()))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1));

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(courseService).getAll(captor.capture());
        assertThat(captor.getValue().getPageNumber()).isZero();
        assertThat(captor.getValue().getPageSize()).isEqualTo(10);
    }


    @Test
    @DisplayName("GET /api/v1/courses/{id} returns 200 and course payload")
    void getCourseById() throws Exception {
        CourseDto dto = sampleCourse();
        given(courseService.getById(dto.id())).willReturn(dto);

        mockMvc.perform(get("/api/v1/courses/{id}", dto.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(dto.id().toString()));

        verify(courseService).getById(dto.id());
    }

    @Test
    @DisplayName("POST /api/v1/courses returns 201 when created")
    void createCourse() throws Exception {
        CourseDto dto = sampleCourse();
        given(courseService.create(any(CourseDto.class))).willReturn(dto);

        mockMvc.perform(post("/api/v1/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.title").value("Java 101"));

        ArgumentCaptor<CourseDto> captor = ArgumentCaptor.forClass(CourseDto.class);
        verify(courseService).create(captor.capture());
        assertThat(captor.getValue().title()).isEqualTo("Java 101");
    }

    @Test
    @DisplayName("PUT /api/v1/courses/{id} returns 200 and updated payload")
    void updateCourse() throws Exception {
        UUID id = UUID.randomUUID();

        var payload = CourseDto.builder()
                .id(null)
                .title("Java 101")
                .description("Intro course")
                .price(BigDecimal.valueOf(49.99))
                .coinsPaid(null)
                .settings(null)
                .lessons(null)
                .students(null)
                .build();

        var returned = CourseDto.builder()
                .id(id)
                .title(payload.title())
                .description(payload.description())
                .price(payload.price())
                .coinsPaid(payload.coinsPaid())
                .settings(payload.settings())
                .lessons(payload.lessons())
                .students(payload.students())
                .build();
        given(courseService.update(eq(id), any(CourseDto.class))).willReturn(returned);

        mockMvc.perform(put("/api/v1/courses/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id.toString()));


        ArgumentCaptor<CourseDto> captor = ArgumentCaptor.forClass(CourseDto.class);
        verify(courseService).update(eq(id), captor.capture());

        CourseDto sent = captor.getValue();
        assertThat(sent.id()).isNull();
        assertThat(sent.title()).isEqualTo("Java 101");
    }


    @Test
    @DisplayName("DELETE /api/v1/courses/{id} returns 204 and triggers service")
    void deleteCourse() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/courses/{id}", id))
                .andExpect(status().isNoContent());

        verify(courseService).delete(id);
    }
}