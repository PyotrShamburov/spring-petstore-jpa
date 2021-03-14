package by.home.service;

import by.home.entity.Category;
import by.home.entity.Pet;
import by.home.entity.Tag;
import by.home.entity.status.PetStatusEnum;
import by.home.repository.PetRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PetServiceTest {

    @Autowired
    private PetService petService;
    @Autowired
    private PetRepository petRepository;
    private Pet pet;

    @BeforeEach
    void setUp() {
        List<Tag> tags = new ArrayList<>();
        tags.add(Mockito.mock(Tag.class));
        tags.add(Mockito.mock(Tag.class));
        pet = new Pet(0, Mockito.mock(Category.class), "petname", tags, PetStatusEnum.AVAILABLE);
    }

    @Test
    @Order(1)
    void addPetToStorage() {
        petService.addPetToStorage(pet);
        Pet actualPet = petService.getById(1);
        assertEquals(pet, actualPet);
    }

    @Test
    @Order(2)
    void updatePet() {
        List<Tag> tagList = new ArrayList<>();
        tagList.add(Mockito.mock(Tag.class));
        tagList.add(Mockito.mock(Tag.class));
        Pet petForUpdate = new Pet(1, Mockito.mock(Category.class), "updated", tagList, PetStatusEnum.SOLD);
        petService.updatePet(petForUpdate);
        Pet updatedPet = petService.getById(1);
        assertEquals(petForUpdate, updatedPet);
    }

    @Test
    @Order(3)
    void getById() {
        petService.addPetToStorage(pet);
        Pet petById = petService.getById(2);
        assertEquals(pet, petById);
    }

    @Test
    @Order(4)
    void getByStatus() {
        List<Pet> availablePet = (List<Pet>) petService.getByStatus("available");
        int availableAmount = availablePet.size();
        int availableExpected = 1;
        assertEquals(availableExpected, availableAmount);

    }

    @Test
    @Order(5)
    void deleteById() {
        pet.setName("petdelete");
        petService.addPetToStorage(pet);
        petService.deleteById(3);
        boolean actualExist = petRepository.existsById(3L);
        assertFalse(actualExist);
    }

    @Test
    @Order(6)
    void contains() {
        pet.setName("pet");
        petService.addPetToStorage(pet);
        Pet samePet = new Pet(4, null, "pet", null, PetStatusEnum.SOLD);
        boolean actualExist = petService.contains(samePet);
        assertTrue(actualExist);
    }
}