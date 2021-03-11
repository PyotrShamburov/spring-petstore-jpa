package by.home.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Pattern(regexp = "^[A-Za-z]{3,15}[0-9]{0,5}$", message = "Wrong format! Use characters (3 - 15) and numbers(0 - 5)!")
    private String username;

    @Pattern(regexp = "^[\\p{Upper}]?[\\p{Lower}]{3,15}$", message = "Wrong format! Only characters(3 - 15)!")
    private String firstName;

    @Pattern(regexp = "^[\\p{Upper}]?[\\p{Lower}]{3,15}$", message = "Wrong format! Only characters(3 - 15)!")
    private String lastName;

    @Pattern(regexp = "[A-Za-z0-9._%+-]{2,10}@[A-Za-z0-9.-]{3,6}\\.[A-Za-z]{2,4}",
            message = "Invalid email address!")
    private String email;

    @Pattern(regexp = "^\\w{4,10}$", message = "Invalid password! (Char. and numbers 4 - 10)")
    private String password;

//    @Pattern(regexp = "^(\\+\\d{12})|(\\d{11})$")
    @ElementCollection
    private List<String> phones;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @Positive
    private int userStatus;
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
