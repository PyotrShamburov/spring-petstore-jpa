package by.home.service;

import by.home.entity.Address;
import by.home.entity.Role;
import by.home.entity.User;
import by.home.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    void setUp() {
        List<String> phones = new ArrayList<>();
        phones.add("3456789");
        phones.add("567890-0987");
        Address address = new Address(0, "belarus", "minsk", "street",
                234, 222000);
        user = new User(0, "test", "test", "test", "test@mail.ru", "1234", phones,
                address, 2, Role.USER );
        userRepository.save(user);
    }


    @Test
    void addNewUser() {
        User actualUser = (User) userRepository.getOne(4L);
        assertEquals(user, actualUser);
    }

    @Test
    void getUserByUsername() {
        User byUsername = (User) userRepository.getByUsername(user.getUsername());
        assertEquals(user, byUsername);
    }

    @Test
    void updateUser() {
        List<String> phonesUpdate = new ArrayList<>();
        phonesUpdate.add("3456789");
        phonesUpdate.add("5678900987");
        Address addressUpdate = new Address(0, "Changed", "rome", "street",
                2344, 221000);

        User byUsername = (User) userRepository.getByUsername(user.getUsername());
        User userForUpdate = new User(byUsername.getId(), "name", "newname", "newlastname", "newmail@mail.ru",
                "12345", phonesUpdate, addressUpdate, 1, Role.ADMIN);
        userRepository.save(userForUpdate);
        User updatedUser = (User) userRepository.getOne(userForUpdate.getId());
        assertEquals(userForUpdate, updatedUser);

    }

    @Test
    void deleteUserByUsername() {
        userRepository.delete(user);
        boolean actualExist = (boolean) userRepository.existsByUsername(user.getUsername());
        assertFalse(actualExist);
    }

    @Test
    void getUserById() {
        userRepository.save(user);
        User actualUser = (User) userRepository.getOne(6L);
        assertEquals(user, actualUser);
    }

    @Test
    void contains() {
        boolean actualExist = (boolean) userRepository.existsByUsername(user.getUsername());
        assertTrue(actualExist);
    }
}