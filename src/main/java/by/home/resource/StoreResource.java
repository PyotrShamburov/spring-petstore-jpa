package by.home.resource;

import by.home.entity.StoreOrder;
import by.home.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(path = "/store")
@Slf4j
public class StoreResource {

    @Autowired
    private StoreService storeService;

    @PostMapping(path = "/order")
    public ResponseEntity<StoreOrder> placePetOrder(@Valid @RequestBody StoreOrder order) {
        StoreOrder addOrder = (StoreOrder) storeService.addOrder(order);
        log.info("Request to add new order to database "+order+".");
        return new ResponseEntity<>(addOrder, HttpStatus.OK);
    }

    @GetMapping(path = "/order/{orderId}")
    public ResponseEntity<StoreOrder> getOrderById(@PathVariable long orderId) {
        if (orderId >= 1 && orderId <= 10) {
            StoreOrder byId = (StoreOrder) storeService.findById(orderId);
            log.info("Request for find order in database by Id! Id ["+orderId+"].");
            return new ResponseEntity<>(byId, HttpStatus.OK);
        }
        log.error("Order id ["+orderId+"] - not valid!");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/order/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable long orderId) {
        if (orderId > 0) {
            storeService.deleteById(orderId);
            log.info("Request for delete order in database by Id! Id ["+orderId+"].");
            return new ResponseEntity<>("StoreOrder with ID:" + orderId + " - DELETED!", HttpStatus.OK);
        }
        log.error("Order id ["+orderId+"] - not valid!");
        return new ResponseEntity<>("Invalid ID supplied", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/inventory")
    public ResponseEntity<Map<String, Integer>> getStatusesAmount() {
        Map<String, Integer> inventoriesByStatus = (Map<String, Integer>) storeService.getInventoriesByStatus();
        log.info("Request for get order inventory in database.");
        return new ResponseEntity<>(inventoriesByStatus, HttpStatus.OK);
    }
}
