package by.home.service;

import by.home.entity.StoreOrder;
import by.home.entity.status.OrderStatusEnum;
import by.home.repository.StoreRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StoreServiceTest {

    @Autowired
    private StoreService storeService;
    @Autowired
    private StoreRepository storeRepository;
    private StoreOrder storeOrder;

    @BeforeEach
    void setUp() {
        storeOrder = new StoreOrder(0, 2, 2, "",
                OrderStatusEnum.PLACED, true);
    }

    @Test
    @Order(1)
    void addOrder() {
        storeService.addOrder(storeOrder);
        StoreOrder actualOrder = storeService.findById(1);
        assertEquals(storeOrder, actualOrder);
    }

    @Test
    @Order(2)
    void findById() {
        storeService.addOrder(storeOrder);
        StoreOrder actualOrder = (StoreOrder) storeService.findById(2);
        assertEquals(storeOrder, actualOrder);
    }

    @Test
    @Order(3)
    void deleteById() {
        storeService.deleteById(2);
        boolean orderExist = (boolean) storeRepository.existsById(2L);
        assertFalse(orderExist);
    }

    @Test
    @Order(4)
    void getInventoriesByStatus() {
        Map<String, Integer> inventoriesByStatus = (Map<String, Integer>) storeService.getInventoriesByStatus();
        Map<String, Integer> actualStatuses = new HashMap<>();
        actualStatuses.put("approved", 0);
        actualStatuses.put("placed", 1);
        actualStatuses.put("delivered", 0);
        assertEquals(inventoriesByStatus, actualStatuses);
    }

    @Test
    @Order(5)
    void contains() {
        storeService.addOrder(storeOrder);
       StoreOrder storeOrderSame =  new StoreOrder(3, 1, 2,
               "", OrderStatusEnum.PLACED, true);
        boolean actualExist = (boolean) storeService.contains(storeOrder);
        assertTrue(actualExist);
    }
}