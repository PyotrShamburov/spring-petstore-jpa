package by.home.service;

import by.home.entity.Tag;
import by.home.repository.TagRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TagServiceTest {

    @Autowired
    private TagService tagService;
    @Autowired
    private TagRepository tagRepository;
    private Tag tag;

    @BeforeEach
    void setUp() {
        tag = new Tag(0,"test");
    }

    @Test
    @Order(1)
    void saveTag() {
        tagService.saveTag(tag);
        Tag actualTag = (Tag) tagRepository.getById(1);
        assertEquals(tag, actualTag);
    }

    @Test
    @Order(2)
    void getById() {
        tag.setName("testid");
        tagService.saveTag(tag);
        Tag byId = (Tag) tagRepository.getById(2);
        assertEquals(tag, byId);
    }

    @Test
    @Order(3)
    void contains() {
        Tag otherTag = new Tag(0,"test" );
        boolean actual = tagService.contains(otherTag);
        assertTrue(actual);
    }
}