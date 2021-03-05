package by.home.service;

import by.home.model.Tag;
import by.home.model.exception.EntityAlreadyExistsException;
import by.home.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {


    @Autowired
    private TagRepository tagRepository;

    public Tag saveTag(Tag tag) {
        if (!contains(tag)) {
            return tagRepository.save(tag);
        }
        throw new EntityAlreadyExistsException("Tag with this name already exists!");
    }

    public Tag getById(long id) {
        return tagRepository.getOne(id);
    }
    public boolean contains(Tag tag) {
        return tagRepository.existsByName(tag.getName());
    }
}
