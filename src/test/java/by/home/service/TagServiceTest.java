package by.home.service;

import by.home.entity.Tag;
import by.home.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TagServiceTest {

    @Autowired
    private TagRepository tagRepository;
    private Tag tag;

    @BeforeEach
    void setUp() {
        tag = new Tag(0,"test");
        tagRepository.save(tag);
    }

    @Test
    void saveTag() {
        Tag actualTag = (Tag) tagRepository.getOne(3L);
        assertEquals(tag, actualTag);
    }

    @Test
    void getById() {
        Tag byId = (Tag) tagRepository.getById(2);
        assertEquals(tag, byId);
    }

    @Test
    void contains() {
        String otherTagName = "test";
        boolean actual = (boolean) tagRepository.existsByName(otherTagName);
        assertTrue(actual);
    }
}