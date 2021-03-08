package by.home.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Pattern(regexp = "^[A-Z]?[a-z]{3,15}$", message = "Wrong format! Only characters(3 - 15)!")
    private String country;
    @Pattern(regexp = "^[A-Z]?[a-z]{3,15}$", message = "Wrong format! Only characters(3 - 15)!")
    private String city;
    @Pattern(regexp = "^[A-Z]?[a-z]{3,15}$", message = "Wrong format! Only characters(3 - 15)!")
    private String street;
    @Positive
    private int houseNumber;
    private int postIndex;
}
