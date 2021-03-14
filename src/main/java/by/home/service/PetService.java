package by.home.service;

import by.home.repository.PetRepository;
import by.home.entity.Category;
import by.home.entity.Pet;
import by.home.entity.Tag;
import by.home.entity.exception.EntityAlreadyExistsException;
import by.home.entity.exception.EntityNotFoundException;
import by.home.entity.status.PetStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PetService {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private TagService tagService;
    @Autowired
    private CategoryService categoryService;

    public Pet addPetToStorage(Pet pet) {
        if (!contains(pet)) {
            addCategoryToPet(pet);
            addTagsToPet(pet);
            log.info(pet+" add to database!");
            return petRepository.save(pet);
        }
        log.error(pet.getName()+" already exists in database.");
        throw new EntityAlreadyExistsException("Pet with this name already exists!");
    }


    public void addCategoryToPet(Pet pet) {
        Category category = (Category) categoryService.getById(pet.getCategory().getId());
        pet.setCategory(category);
        log.info(category+ "has been add to pet with name "+pet.getName());
    }

    public void addTagsToPet(Pet pet) {
        List<Tag> petTags = new ArrayList<>();
        for (Tag tag : pet.getTags()) {
            Tag byId = tagService.getById(tag.getId());
            petTags.add(byId);
            log.info(tag+ "has been add to pet with name "+pet.getName());
        }
        pet.setTags(petTags);
        log.info("All available tags set to "+pet.getName());
    }

    public Pet updatePet(Pet newPet) {
        Pet byId = (Pet) getById(newPet.getId());
        byId.setName(newPet.getName());
        byId.setPetStatus(newPet.getPetStatus());
        byId.setCategory(newPet.getCategory());
        addCategoryToPet(byId);
        byId.setTags(newPet.getTags());
        addTagsToPet(byId);
        petRepository.save(byId);
        log.info("Pet with ID "+newPet.getId()+" updated to "+byId);
        return byId;
    }

    public Pet getById(long id) {
        Optional<Pet> optionalPet = (Optional<Pet>) petRepository.findById(id);
        if (optionalPet.isPresent()) {
            log.info("Request pet by id. ID = "+id);
            return (Pet) optionalPet.get();
        }
        log.error("Pet with Id "+id+" not found!");
        throw new EntityNotFoundException("Pet with this ID not found");
    }

    public List<Pet> getByStatus(String status) {
        List<Pet> listPetWithSameStatus;
        switch (status) {
            case "available":
                listPetWithSameStatus = petRepository.getByPetStatus(PetStatusEnum.AVAILABLE);
                break;
            case "pending":
                listPetWithSameStatus = petRepository.getByPetStatus(PetStatusEnum.PENDING);
                break;
            case "sold":
                listPetWithSameStatus = petRepository.getByPetStatus(PetStatusEnum.SOLD);
                break;
            default:
                log.error("Status of pets not found!");
                throw new EntityNotFoundException("This status not found");
        }
        log.info("Request for list of pet's status!");
        return listPetWithSameStatus;
    }

    public void deleteById(long id) {
        if (petRepository.existsById(id)) {
            petRepository.deleteById(id);
            log.info("Pet with ID "+id+" has been deleted!");
        } else {
            log.error("Pet with ID "+id+" not found for delete!");
            throw new EntityNotFoundException("Pet with this ID not found!");
        }
    }

    public boolean contains(Pet pet) {
        log.warn("Check pet for contains in database!");
        return petRepository.existsByName(pet.getName());
    }
}
