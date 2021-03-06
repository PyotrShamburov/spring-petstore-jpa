package by.home.service;

import by.home.repository.StoreRepository;
import by.home.entity.StoreOrder;
import by.home.entity.exception.EntityAlreadyExistsException;
import by.home.entity.exception.EntityNotFoundException;
import by.home.entity.status.OrderStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    public StoreOrder addOrder(StoreOrder order) {
        if (!contains(order)) {
            setShipDate(order);
            log.info(order+" added to database.");
            return storeRepository.save(order);
        }
        log.error("Order not added, this Id is busy! Order Id = "+order.getId()+".");
        throw new EntityAlreadyExistsException("StoreOrder with this ID already exists!");
    }

    public void setShipDate(StoreOrder order) {
        Instant instant = Instant.now();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        String date = localDateTime.toString();
        order.setShipDate(date);
        log.info("Date "+date+" added to order with Id = "+order.getId()+".");
    }

    public StoreOrder findById(long id) {
        StoreOrder byId = (StoreOrder) storeRepository.getById(id);
        if (byId != null) {
            log.info("Request to database for order with Id = "+id+".");
            return byId;
        }
        log.error("Order with id = "+id+" not found!");
        throw new EntityNotFoundException("StoreOrder with this ID not found!");
    }

    public void deleteById(long id) {
        if (storeRepository.findById(id).isPresent()) {
            storeRepository.deleteById(id);
            log.info("Request to database for delete order with Id = "+id+". Order deleted!");
            return;
        }
        log.error("Order with id = "+id+" not found!");
        throw new EntityNotFoundException("StoreOrder with this ID not found!");
    }

    public Map<String, Integer> getInventoriesByStatus() {
        Map<String, Integer> statusesAndAmount = new HashMap<>();
        int placedAmount = storeRepository.countByOrderStatus(OrderStatusEnum.PLACED);
        int approvedAmount = storeRepository.countByOrderStatus(OrderStatusEnum.APPROVED);
        int deliveredAmount = storeRepository.countByOrderStatus(OrderStatusEnum.DELIVERED);
        statusesAndAmount.put("placed", placedAmount);
        statusesAndAmount.put("approved", approvedAmount);
        statusesAndAmount.put("delivered", deliveredAmount);
        log.info("Request to database for get inventories!");
        return statusesAndAmount;
    }

    public boolean contains(StoreOrder order) {
        log.warn("Check order for contains in database!");
        return storeRepository.existsById(order.getId());
    }
}
