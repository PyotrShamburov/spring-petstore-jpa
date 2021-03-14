package by.home.service;

import by.home.entity.Tag;
import by.home.entity.exception.EntityAlreadyExistsException;
import by.home.repository.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TagService {


    @Autowired
    private TagRepository tagRepository;

    public Tag saveTag(Tag tag) {
        if (!contains(tag)) {
            log.info(tag.getName()+" tag has been add to database!");
            return tagRepository.save(tag);
        }
        log.error("Tag "+tag.getName()+" already exists in system!");
        throw new EntityAlreadyExistsException("Tag with this name already exists!");
    }

    public Tag getById(long id) {
        log.info("Request to database for find tag with ID = "+id+".");
        return tagRepository.getById(id);
    }

    public boolean contains(Tag tag) {
        log.warn("Check tag for contains in database!");
        return tagRepository.existsByName(tag.getName());
    }
}
