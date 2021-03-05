package by.home.service;

import by.home.repository.StoreRepository;
import by.home.model.Order;
import by.home.model.exception.EntityAlreadyExistsException;
import by.home.model.exception.EntityNotFoundException;
import by.home.model.status.OrderStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    public Order addOrder(Order order) {
        if (!contains(order)) {
            setShipDate(order);
            return storeRepository.save(order);
        }
        throw new EntityAlreadyExistsException("Order with this ID already exists!");
    }

    public void setShipDate(Order order) {
        Instant instant = Instant.now();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        String date = localDateTime.toString();
        order.setShipDate(date);
    }

    public Order findById(long id) {
        Order byId = (Order) storeRepository.getById(id);
        if (byId != null) {
            return byId;
        }
        throw new EntityNotFoundException("Order with this ID not found!");
    }

    public boolean deleteById(long id) {
        Order orderById = (Order) storeRepository.getById(id);
        if (orderById != null) {
            storeRepository.delete(orderById);
            return true;
        }
        throw new EntityNotFoundException("Order with this ID not found!");
    }

    public Map<String, Integer> getInventoriesByStatus() {
        Map<String, Integer> statusesAndAmount = new HashMap<>();
        int placedAmount = storeRepository.countByOrderStatus(OrderStatusEnum.PLACED);
        int approvedAmount = storeRepository.countByOrderStatus(OrderStatusEnum.APPROVED);
        int deliveredAmount = storeRepository.countByOrderStatus(OrderStatusEnum.DELIVERED);
        statusesAndAmount.put("placed", placedAmount);
        statusesAndAmount.put("approved", approvedAmount);
        statusesAndAmount.put("delivered", deliveredAmount);
        return statusesAndAmount;
    }
    public boolean contains(Order order) {
        return storeRepository.existsById(order.getId());
    }
}
