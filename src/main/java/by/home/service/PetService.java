package by.home.service;

import by.home.repository.PetRepository;
import by.home.model.Category;
import by.home.model.Pet;
import by.home.model.Tag;
import by.home.model.exception.EntityAlreadyExistsException;
import by.home.model.exception.EntityNotFoundException;
import by.home.model.status.PetStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
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
            return petRepository.save(pet);
        }
        throw new EntityAlreadyExistsException("Pet with this name already exists!");
    }


    public void addCategoryToPet(Pet pet) {
        Category category = (Category) categoryService.getById(pet.getCategory().getId());
        if (category != null) {
            pet.setCategory(category);
        }
    }

    public void addTagsToPet(Pet pet) {
        List<Tag> petTags = new ArrayList<>();
        for (Tag tag : pet.getTags()) {
            Tag byId = tagService.getById(tag.getId());
            petTags.add(byId);
        }
        pet.setTags(petTags);
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
        return byId;
    }

    public Pet getById(long id) {
        Optional<Pet> optionalPet = (Optional<Pet>) petRepository.findById(id);
        if (optionalPet.isPresent()) {
            return (Pet) optionalPet.get();
        }
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
                throw new EntityNotFoundException("This status not found");
        }
        return listPetWithSameStatus;
    }

    public void deleteById(long id) {
        if (petRepository.existsById(id)) {
            petRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Pet with this ID not found!");
        }
    }

    public boolean contains(Pet pet) {
        return petRepository.existsByName(pet.getName());
    }
}
