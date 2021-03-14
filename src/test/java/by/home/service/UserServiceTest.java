package by.home.service;

import by.home.entity.Address;
import by.home.entity.Role;
import by.home.entity.User;
import by.home.entity.UserDTO;
import by.home.repository.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    void setUp() {
        List<String> phones = new ArrayList<>();
        phones.add("3456789");
        phones.add("567890-0987");
        Address address = new Address(0, "england", "london", "street", 22, 2314);
        user = new User(0, "test", "test", "test", "test@mail.ru", "1234", phones,
                address, 2, Role.USER );
    }


    @Test
    @Order(1)
    void addNewUser() {
        userService.addNewUser(user);
        User actualUser = userRepository.findById(1L).get();
        assertEquals(user, actualUser);
    }

    @Test
    @Order(2)
    void getUserByUsername() {
        User byUsername = userService.getUserByUsername("test");
        assertEquals(user, byUsername);
    }

    @Test
    @Order(3)
    void updateUser() {
        List<String> phonesUpdate = new ArrayList<>();
        phonesUpdate.add("3456789");
        phonesUpdate.add("5678900987");
        Address addressUpdate = new Address(0, "england", "london",
                "street", 223, 2314);
        User userForUpdate = new User(0, "name", "newname", "newlastname", "newmail@mail.ru",
                "12345", phonesUpdate, addressUpdate, 1, Role.ADMIN);
        userService.updateUser("test", userForUpdate);
        User updatedUser = userRepository.findById(1L).get();
        assertEquals(userForUpdate, updatedUser);
    }

    @Test
    @Order(4)
    void deleteUserByUsername() {
        userService.deleteUserByUsername("name");
        boolean actualExist = (boolean) userRepository.existsByUsername("name");
        assertFalse(actualExist);
    }

    @Test
    @Order(5)
    void getUserById() {
        userService.addNewUser(user);
        User actualUser = userService.getUserById(2).get();
        assertEquals(user, actualUser);
    }

    @Test
    @Order(6)
    void contains() {
        boolean actualExist =userService.contains(user);
        assertTrue(actualExist);
    }

    @Test
    @Order(7)
    void authCheck() {
        UserDTO userDTO = new UserDTO("test", "1234");
        boolean actualCheck = (boolean) userService.authCheck(userDTO);
        assertTrue(actualCheck);
    }
}