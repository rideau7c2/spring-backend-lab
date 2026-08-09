package com.gitlab.rideau7c2.backendlab;

import com.gitlab.rideau7c2.backendlab.user.InMemoryUserRepository;
import com.gitlab.rideau7c2.backendlab.user.User;
import com.gitlab.rideau7c2.backendlab.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryUserRepositoryTests {

    private  Long existsUserId;

    private final Long NOT_EXISTS_USER_ID = -1L;

    private UserRepository userRepository;

    @BeforeEach
    void prepareUsers() {
        userRepository = new InMemoryUserRepository();
        existsUserId = userRepository.save(new User(null, "Test", "test@ot.co")).id();
    }

    @Test
    void findExistingUser() {
        User user = userRepository.findById(existsUserId);
        assertEquals("Test", user.name());
    }

    @Test
    void findNonExistingUser() {
        User user = userRepository.findById(NOT_EXISTS_USER_ID);
        assertNull(user);
    }

    @Test
    void saveNewUser() {
        User user = new User(null, "NameOne", "NameOne@ot.co");
        user = userRepository.save(user);
        user = userRepository.findById(user.id());
        assertEquals("NameOne", user.name());
    }

    @Test
    void updateUser() {
        userRepository.save(new User(existsUserId, "Updated", "updated@ot.co"));
        User updatedUser = userRepository.findById(existsUserId);
        assertEquals("Updated", updatedUser.name());
        assertEquals("updated@ot.co", updatedUser.email());
    }

    @Test
    void updateNotExistingUser() {
        User user = new User(NOT_EXISTS_USER_ID, "Updated", "updated@ot.co");
        NoSuchElementException e = assertThrows(
                NoSuchElementException.class,
                () -> userRepository.save(user));
        assertEquals("user with id:%d not exists".formatted(NOT_EXISTS_USER_ID), e.getMessage());
    }

    @Test
    void deleteExistUser(){
        User user = userRepository.delete(existsUserId);
        assertNotNull(user);
        user = userRepository.findById(existsUserId);
        assertNull(user);
    }

    @Test
    void deleteNotExistUser(){
        User user = userRepository.delete(NOT_EXISTS_USER_ID);
        assertNull(user);
    }

    @Test
    void saveShouldGenerateSequentialIds() {
        User firstUser = userRepository.save(new User(null, "first", "first@ot.co"));
        User secoundUser = userRepository.save(new User(null, "secound", "secound@ot.co"));
        assertEquals(firstUser.id() + 1, secoundUser.id());
        userRepository.delete(secoundUser.id());
        User thirdUser = userRepository.save(new User(null, "third", "third@ot.co"));
        assertEquals(secoundUser.id() + 1, thirdUser.id());
    }
}
