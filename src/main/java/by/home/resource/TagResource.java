package by.home.resource;

import by.home.service.TagService;
import by.home.entity.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/tag")
@Slf4j
public class TagResource {

    @Autowired
    private TagService tagService;

    @PostMapping
    public ResponseEntity<Tag> addNewTag(@Valid @RequestBody Tag tag) {
        tagService.saveTag(tag);
        log.info("Request to add new tag to database "+tag+".");
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }
}
