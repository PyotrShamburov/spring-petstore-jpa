package by.home.service;

import by.home.model.UserDTO;
import by.home.repository.UserRepository;
import by.home.model.User;
import by.home.model.exception.EntityAlreadyExistsException;
import by.home.model.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        userByUsername.setUsername(newUser.getUsername());
        userByUsername.setFirstName(newUser.getFirstName());
        userByUsername.setLastName(newUser.getLastName());
        userByUsername.setEmail(newUser.getEmail());
        userByUsername.setPassword(newUser.getPassword());
        userByUsername.setPhone(newUser.getPhone());
        userByUsername.setUserStatus(newUser.getUserStatus());
        userRepository.save(userByUsername);
        return userByUsername;
    }

    public void deleteUserByUsername(String username) {
        User byUsername = (User) userRepository.getByUsername(username);
        if (byUsername != null) {
            userRepository.delete(byUsername);
        } else {
            throw new EntityNotFoundException("User with this username not found!");
        }
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
