package com.leverx.lms.learningmanagementsystem.lesson.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.lms.learningmanagementsystem.lesson.dto.LessonDto;
import com.leverx.lms.learningmanagementsystem.lesson.service.LessonService;
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

@WebMvcTest(LessonController.class)
class LessonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LessonService lessonService;

    @Autowired
    private ObjectMapper mapper;

    private LessonDto sampleLesson(UUID id) {
        return LessonDto.builder()
                .id(id)
                .title("Lesson 1")
                .duration(4)
                .build();
    }


    @Test
    @DisplayName("GET /api/v1/lessons returns 200 and delegates to service")
    void getAllLessons() throws Exception {
        Page<LessonDto> page = new PageImpl<>(
                List.of(sampleLesson(UUID.randomUUID())),
                PageRequest.of(0, 10),
                1
        );
        given(lessonService.getAll(any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/api/v1/lessons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(lessonService).getAll(captor.capture());
        assertThat(captor.getValue().getPageSize()).isEqualTo(10);
    }


    @Test
    @DisplayName("GET /api/v1/lessons/{id} returns lesson payload")
    void getLessonById() throws Exception {
        UUID id = UUID.randomUUID();
        LessonDto dto = sampleLesson(id);
        given(lessonService.getById(id)).willReturn(dto);

        mockMvc.perform(get("/api/v1/lessons/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id.toString()));

        verify(lessonService).getById(id);
    }

    @Test
    @DisplayName("POST /api/v1/lessons creates lesson and returns 201")
    void createLesson() throws Exception {
        LessonDto payload = sampleLesson(null);
        UUID newId = UUID.randomUUID();
        LessonDto returned = sampleLesson(newId);

        given(lessonService.create(any(LessonDto.class))).willReturn(returned);

        mockMvc.perform(post("/api/v1/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(newId.toString()));

        verify(lessonService).create(any(LessonDto.class));
    }

    @Test
    @DisplayName("PUT /api/v1/lessons/{id} updates lesson and returns 200")
    void updateLesson() throws Exception {
        UUID id = UUID.randomUUID();
        LessonDto payload = sampleLesson(null);
        LessonDto returned = sampleLesson(id);

        given(lessonService.update(eq(id), any(LessonDto.class))).willReturn(returned);

        mockMvc.perform(put("/api/v1/lessons/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id.toString()));

        verify(lessonService).update(eq(id), any(LessonDto.class));
    }

    @Test
    @DisplayName("DELETE /api/v1/lessons/{id} returns 204")
    void deleteLesson() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/lessons/{id}", id))
                .andExpect(status().isNoContent());

        verify(lessonService).delete(id);
    }

}