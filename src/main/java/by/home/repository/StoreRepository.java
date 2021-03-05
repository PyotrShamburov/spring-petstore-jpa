package by.home.repository;

import by.home.model.Order;
import by.home.model.status.OrderStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Order, Long> {
    int countByOrderStatus(OrderStatusEnum statusEnum);
    Order getById(long id);
}
