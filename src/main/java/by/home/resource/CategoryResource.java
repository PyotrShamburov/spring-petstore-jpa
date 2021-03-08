package by.home.resource;

import by.home.service.CategoryService;
import by.home.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/category")
public class CategoryResource {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> addNewCategory(@Valid @RequestBody Category category) {
        if (categoryService.addToStorage(category)) {
            return new ResponseEntity<>(category, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
