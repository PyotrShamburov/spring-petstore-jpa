package by.home.resource;

import by.home.entity.Pet;
import by.home.service.PetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/pet")
@Slf4j
public class PetResource {

    @Autowired
    private PetService petService;

    @PostMapping
    public ResponseEntity<Pet> addNewPet(@Valid @RequestBody Pet pet) {
        Pet createdPet = (Pet) petService.addPetToStorage(pet);
        log.info("Request to add new pet to database "+pet+".");
        return new ResponseEntity<>(createdPet, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Pet> updatePet(@Valid @RequestBody Pet newPet) {
        Pet updatedPet = (Pet) petService.updatePet(newPet);
        log.info("Request to update existing pet in database ["+newPet.getName()+"].");
        return new ResponseEntity<>(updatedPet, HttpStatus.CREATED);
    }

    @GetMapping(path = "/findByStatus")
    public ResponseEntity<List<Pet>> findByStatus(String status) {
        List<Pet> byStatus = (List<Pet>) petService.getByStatus(status);
        log.info("Request for find pet in database by status! Status ["+status+"].");
        return new ResponseEntity<>(byStatus, HttpStatus.OK);
    }

    @GetMapping(path = "/{petId}")
    public ResponseEntity<Pet> findById(@PathVariable long petId) {
        if (petId > 0 ) {
            Pet byId = (Pet) petService.getById(petId);
            log.info("Request for find pet in database by Id! Id ["+petId+"].");
            return new ResponseEntity<>(byId, HttpStatus.OK);
        }
        log.error("Pet id ["+petId+"] - not valid!");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/{petId}")
    public ResponseEntity<String> deleteById(@PathVariable long petId) {
        if (petId > 0) {
            petService.deleteById(petId);
            log.info("Request for delete pet in database by Id! Id ["+petId+"].");
            return new ResponseEntity<>("Pet with ID: " + petId + " - DELETED!", HttpStatus.OK);
        }
        log.error("Pet id ["+petId+"] - not valid!");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
