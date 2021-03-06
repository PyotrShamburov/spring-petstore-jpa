package by.home.repository;

import by.home.model.Pet;
import by.home.model.status.PetStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> getByPetStatus(PetStatusEnum statusEnum);
    boolean existsByName(String name);
    @Override
    void deleteById(Long id);
}
