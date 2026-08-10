package com.gitlab.rideau7c2.backendlab;

import com.gitlab.rideau7c2.backendlab.exception.UserNotFoundException;
import com.gitlab.rideau7c2.backendlab.user.controller.UserController;
import com.gitlab.rideau7c2.backendlab.user.repository.User;
import com.gitlab.rideau7c2.backendlab.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    UserService userService;

    @BeforeEach
    void mockUsers() {
        User user = new User(1L, "test", "test");
        when(userService.getUser(1L)).thenReturn(user);
        when(userService.getUser(2L)).thenThrow(new UserNotFoundException(2L));
    }

    @Test
    void getExistingUser() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.name").value("test")
                );
    }

    @Test
    void getNotExistingUser() throws Exception {
        mockMvc.perform(get("/users/2"))
                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE),
                        jsonPath("$.detail").value("Uzytkownik o id:2 nie istnieje"),
                        jsonPath("$.title").value("User not found")
                );
    }

    @Test
    void getUserBadRequest() throws Exception {
        mockMvc.perform(get("/users/abc"))
                .andExpect(
                        status().isNotFound()
                );
    }

}
