package com.gitlab.rideau7c2.backendlab;

import com.gitlab.rideau7c2.backendlab.user.repository.InMemoryUserRepository;
import com.gitlab.rideau7c2.backendlab.user.repository.User;
import com.gitlab.rideau7c2.backendlab.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

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
        Optional<User> user = userRepository.findById(existsUserId);
        assertEquals("Test", user.orElseThrow().name());
    }

    @Test
    void findNonExistingUser() {
        Optional<User> user = userRepository.findById(NOT_EXISTS_USER_ID);
        assertTrue(user.isEmpty());
    }

    @Test
    void saveNewUser() {
        User user = new User(null, "NameOne", "NameOne@ot.co");
        user = userRepository.save(user);
        Optional<User> userFromDb = userRepository.findById(user.id());
        assertEquals("NameOne", userFromDb.orElseThrow().name());
    }

    @Test
    void updateUser() {
        userRepository.save(new User(existsUserId, "Updated", "updated@ot.co"));
        Optional<User> updatedUser = userRepository.findById(existsUserId);
        assertEquals("Updated", updatedUser.orElseThrow().name());
        assertEquals("updated@ot.co", updatedUser.orElseThrow().email());
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
        Optional<User> userFromDb = userRepository.findById(existsUserId);
        assertTrue(userFromDb.isEmpty());
    }

    @Test
    void deleteNotExistUser(){
        User user = userRepository.delete(NOT_EXISTS_USER_ID);
        assertNull(user);
    }

    @Test
    void saveShouldGenerateSequentialIds() {
        User firstUser = userRepository.save(new User(null, "first", "first@ot.co"));
        User secondUser = userRepository.save(new User(null, "second", "second@ot.co"));
        assertEquals(firstUser.id() + 1, secondUser.id());
        userRepository.delete(secondUser.id());
        User thirdUser = userRepository.save(new User(null, "third", "third@ot.co"));
        assertEquals(secondUser.id() + 1, thirdUser.id());
    }
}
