package by.home.entity;

import by.home.entity.status.PetStatusEnum;
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
    @OneToOne
    private Category category;

    @Pattern(regexp = "^[\\p{Upper}]?[\\p{Lower}]{3,15}$", message = "Wrong format! Only characters(3 - 15)!")
    private String name;
    @ManyToMany
    private List<Tag> tags;
    @Enumerated(value = EnumType.STRING)
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
