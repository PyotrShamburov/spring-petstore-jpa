package by.home.resource;

import by.home.entity.Pet;
import by.home.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/pet")
public class PetResource {

    @Autowired
    private PetService petService;

    @PostMapping
    public ResponseEntity<Pet> addNewPet(@Valid @RequestBody Pet pet) {
        Pet createdPet = (Pet) petService.addPetToStorage(pet);
        return new ResponseEntity<>(createdPet, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Pet> updatePet(@Valid @RequestBody Pet newPet) {
        Pet updatedPet = (Pet) petService.updatePet(newPet);
        return new ResponseEntity<>(updatedPet, HttpStatus.CREATED);
    }

    @GetMapping(path = "/findByStatus")
    public ResponseEntity<List<Pet>> findByStatus(String status) {
        List<Pet> byStatus = (List<Pet>) petService.getByStatus(status);
        return new ResponseEntity<>(byStatus, HttpStatus.OK);
    }

    @GetMapping(path = "/{petId}")
    public ResponseEntity<Pet> findById(@PathVariable long petId) {
        if (petId > 0 ) {
            Pet byId = (Pet) petService.getById(petId);
            return new ResponseEntity<>(byId, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/{petId}")
    public ResponseEntity<String> deleteById(@PathVariable long petId) {
        if (petId > 0) {
            petService.deleteById(petId);
            return new ResponseEntity<>("Pet with ID: " + petId + " - DELETED!", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
