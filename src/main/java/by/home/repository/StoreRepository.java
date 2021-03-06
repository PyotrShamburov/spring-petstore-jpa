package by.home.repository;

import by.home.model.StoreOrder;
import by.home.model.status.OrderStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<StoreOrder, Long> {
    int countByOrderStatus(OrderStatusEnum statusEnum);
    StoreOrder getById(long id);
}
