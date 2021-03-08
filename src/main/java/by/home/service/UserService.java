package by.home.service;

import by.home.entity.UserDTO;
import by.home.repository.UserRepository;
import by.home.entity.User;
import by.home.entity.exception.EntityAlreadyExistsException;
import by.home.entity.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addNewUser(User user) {
        if (!contains(user)) {
            return userRepository.save(user);
        }
        throw new EntityAlreadyExistsException("User with this username exists!");
    }

    public User getUserByUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            return userRepository.getByUsername(username);
        }
        throw new EntityNotFoundException("User with username not found!");
    }

    public User updateUser(String username, User newUser) {
        User userByUsername = (User) getUserByUsername(username);
        newUser.setId(userByUsername.getId());
        return userRepository.save(newUser);
    }

    public void deleteUserByUsername(String username) {
        User byUsername = (User) userRepository.getByUsername(username);
        if (byUsername != null) {
            userRepository.delete(byUsername);
        } else {
            throw new EntityNotFoundException("User with this username not found!");
        }
    }

    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    public boolean contains(User user) {
        return userRepository.existsByUsername(user.getUsername());
    }

    public boolean authCheck(UserDTO userDTO) {
        User byUsername = (User) userRepository.getByUsername(userDTO.getUsername());
        if (byUsername != null) {
            return byUsername.getPassword().equals(userDTO.getPassword());
        }
        return false;
    }
}
