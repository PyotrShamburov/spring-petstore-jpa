package by.home.resource;

import by.home.model.User;
import by.home.model.UserDTO;
import by.home.model.XToken;
import by.home.service.XTokenService;
import by.home.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/user")
public class UserResource {

    @Autowired
    private UserService userService;
    @Autowired
    private XTokenService tokenService;

    @PostMapping(path = "/auth")
    public ResponseEntity<String> authorization(@Valid @RequestBody UserDTO userDTO) {
        if (userService.authCheck(userDTO)) {
            long userId = (long) userService.getUserByUsername(userDTO.getUsername()).getId();
            String xToken = (String) tokenService.addToRepository(userId);
            return new ResponseEntity<>(xToken, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = (User) userService.addNewUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{username}")
    public ResponseEntity<User> getUserByName(@PathVariable String username) {
        if (username.matches("^[a-zA-Z0-9]{3,25}$")) {
            User userByUsername = (User) userService.getUserByUsername(username);
            return new ResponseEntity<>(userByUsername, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username, @Valid @RequestBody User newUser) {
        User updatedUser = (User) userService.updateUser(username, newUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        if (username.matches("^[a-zA-Z0-9]{3,25}$")) {
            userService.deleteUserByUsername(username);
            return new ResponseEntity<>(username + " - DELETED!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid username supplied!", HttpStatus.BAD_REQUEST);
        }
    }
}
