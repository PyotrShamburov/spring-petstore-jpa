package by.home.service;

import by.home.entity.UserDTO;
import by.home.repository.UserRepository;
import by.home.entity.User;
import by.home.entity.exception.EntityAlreadyExistsException;
import by.home.entity.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addNewUser(User user) {
        if (!contains(user)) {
            log.info(user+" added to database!");
            return userRepository.save(user);
        }
        log.error(user+" didn't add to database! User with name already exist!");
        throw new EntityAlreadyExistsException("User with this username exists!");
    }

    public User getUserByUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            log.info("Request to database for get user by name - "+username);
            return userRepository.getByUsername(username);
        }
        log.error("User with name ["+username+"] not found!");
        throw new EntityNotFoundException("User with username not found!");
    }

    public User updateUser(String username, User newUser) {
        User userByUsername = (User) getUserByUsername(username);
        newUser.setId(userByUsername.getId());
        log.info("User with name ["+username+"] updated to "+newUser+".");
        return userRepository.save(newUser);
    }

    public void deleteUserByUsername(String username) {
        User byUsername = (User) userRepository.getByUsername(username);
        if (byUsername != null) {
            log.info("User with username ["+username+"] has been deleted!");
            userRepository.delete(byUsername);
        } else {
            log.error("User with username ["+username+"] not found!");
            throw new EntityNotFoundException("User with this username not found!");
        }
    }

    public Optional<User> getUserById(long id) {
        log.info("Request to database for get user by Id - "+id+".");
        return userRepository.findById(id);
    }

    public boolean contains(User user) {
        log.warn("Check user for contains in database!");
        return userRepository.existsByUsername(user.getUsername());
    }

    public boolean authCheck(UserDTO userDTO) {
        log.info("Authorization check has been started!");
        User byUsername = (User) userRepository.getByUsername(userDTO.getUsername());
        if (byUsername != null) {
            return byUsername.getPassword().equals(userDTO.getPassword());
        }
        log.error("Authorization has been failed!");
        return false;
    }
}
