package by.home.resource;

import by.home.entity.User;
import by.home.entity.UserDTO;
import by.home.service.XTokenService;
import by.home.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/user")
@Slf4j
public class UserResource {

    @Autowired
    private UserService userService;
    @Autowired
    private XTokenService tokenService;

    @PostMapping(path = "/auth")
    public ResponseEntity<String> authorization(@Valid @RequestBody UserDTO userDTO) {
        log.info("Authorization start with input data "+userDTO+".");
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
        log.info("Request to add new user to database "+user+".");
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{username}")
    public ResponseEntity<User> getUserByName(@PathVariable String username) {
        if (username.matches("^[a-zA-Z0-9]{3,25}$")) {
            User userByUsername = (User) userService.getUserByUsername(username);
            log.info("Request for find user in database by username! Username ["+username+"].");
            return new ResponseEntity<>(userByUsername, HttpStatus.OK);
        } else {
            log.error("Username is not valid! Username ["+username+"].");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username, @Valid @RequestBody User newUser) {
        User updatedUser = (User) userService.updateUser(username, newUser);
        log.info("Request for update existing user in database by username!" +
                " Username ["+username+"].");
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        if (username.matches("^[a-zA-Z0-9]{3,25}$")) {
            userService.deleteUserByUsername(username);
            log.info("Request for delete user in database by username! Username ["+username+"].");
            return new ResponseEntity<>(username + " - DELETED!", HttpStatus.OK);
        } else {
            log.error("Username is not valid! Username ["+username+"].");
            return new ResponseEntity<>("Invalid username supplied!", HttpStatus.BAD_REQUEST);
        }
    }
}
