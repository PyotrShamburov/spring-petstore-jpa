package by.home.service;

import by.home.entity.Pet;
import by.home.entity.Tag;
import by.home.entity.status.PetStatusEnum;
import by.home.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PetServiceTest {

    @Autowired
    private PetRepository petRepository;
    private Pet pet;

    @BeforeEach
    void setUp() {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1, "first"));
        tags.add(new Tag(2, "second"));
        pet = new Pet(0, null, "petname", tags, PetStatusEnum.AVAILABLE);
        petRepository.save(pet);
    }

    @Test
    void addPetToStorage() {
        Pet actualPet = (Pet) petRepository.getOne(4L);
        assertEquals(pet, actualPet);
    }

    @Test
    void updatePet() {
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag(0, "updated"));
        tagList.add(new Tag(0, "new"));
        Pet petForUpdate = new Pet(1, null, "updated", tagList, PetStatusEnum.SOLD);
        petRepository.save(petForUpdate);
        Pet updatedPet = (Pet) petRepository.getOne(1L);
        assertEquals(petForUpdate, updatedPet);
    }

    @Test
    void getById() {
        Pet petById = (Pet) petRepository.getOne(5L);
        assertEquals(pet, petById);
    }

    @Test
    void getByStatus() {
        List<Pet> byPetStatus = petRepository.getByPetStatus(PetStatusEnum.AVAILABLE);
        int actualAmount = byPetStatus.size();
        int expected = 1;
        assertEquals(expected, actualAmount);
    }

    @Test
    void deleteById() {
        petRepository.deleteById(6L);
        boolean actualExist = (boolean) petRepository.existsById(6L);
        assertFalse(actualExist);
    }

    @Test
    void contains() {
        String testName = "petname";
        boolean actualExist = (boolean) petRepository.existsByName(testName);
        assertTrue(actualExist);
    }
}