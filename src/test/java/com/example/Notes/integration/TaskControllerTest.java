package com.example.Notes.integration;


import lombok.RequiredArgsConstructor;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static com.example.Notes.dto.TaskCreateEditDto.Fields.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@RequiredArgsConstructor
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DisplayName("TaskControllerTest")
@ActiveProfiles("test")
@Transactional
public class TaskControllerTest {
    private final MockMvc mockMvc;

    @Test
    void findById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("task/task"))
                .andExpect(model().attributeExists("notes"))
                .andExpect(model().attribute("notes", IsCollectionWithSize.hasSize(5)));

    }


    @Test
    @DisplayName("GET create")
    void createGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("task/create"))
                .andExpect(model().attributeExists("notes"))
                .andExpect(model().attributeExists("task"));
    }

    @Test
    @DisplayName("POST create")
    void createPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/tasks").param(taskName,"aaa")
                .param(taskText,"bbb")
                .param(noteId, String.valueOf(1)))
                .andExpect(redirectedUrl("/notes/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());



    }
}
