package by.home.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @Pattern(regexp = "^[A-Za-z]{3,15}[0-9]{0,5}$",
            message = "Wrong format! Use characters (3 - 15) and numbers(0 - 5)!")
    private String username;

    @Pattern(regexp = "^\\w{4,10}$",
            message = "Invalid password! (Char. and numbers 4 - 10)")
    private String password;
}
