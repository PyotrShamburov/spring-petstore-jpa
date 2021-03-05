package by.home.model;

import by.home.model.status.PetStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    private Category category;

    @Pattern(regexp = "^[\\p{Upper}]?[\\p{Lower}]{3,15}$", message = "Wrong format! Only characters(3 - 15)!")
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Tag> tags;
    private PetStatusEnum petStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(name, pet.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
