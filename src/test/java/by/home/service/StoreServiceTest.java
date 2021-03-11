package by.home.service;

import by.home.entity.StoreOrder;
import by.home.entity.status.OrderStatusEnum;
import by.home.repository.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StoreServiceTest {

    @Autowired
    private StoreRepository storeRepository;
    private StoreOrder storeOrder;

    @BeforeEach
    void setUp() {
        storeOrder = new StoreOrder(0, 2, 2, "10/03/21",
                OrderStatusEnum.PLACED, true);
        storeRepository.save(storeOrder);
    }

    @Test
    void addOrder() {
        StoreOrder actualOrder = (StoreOrder) storeRepository.getOne(2L);
        assertEquals(storeOrder, actualOrder);
    }

    @Test
    void findById() {
        StoreOrder actualOrder = new StoreOrder();
        Optional<StoreOrder> byId = (Optional<StoreOrder>) storeRepository.findById(3L);
        if (byId.isPresent()) {
            actualOrder = (StoreOrder) byId.get();
        }
        assertEquals(storeOrder, actualOrder);
    }

    @Test
    void deleteById() {
        storeRepository.deleteById(5L);
        boolean orderExist = (boolean) storeRepository.existsById(5L);
        assertFalse(orderExist);
    }

    @Test
    void getInventoriesByStatus() {
        int actual = (int) storeRepository.countByOrderStatus(OrderStatusEnum.PLACED);
        int expected = 1;
        assertEquals(expected, actual);
    }

    @Test
    void contains() {
        boolean actualExist = (boolean) storeRepository.existsById(4L);
        assertTrue(actualExist);
    }
}