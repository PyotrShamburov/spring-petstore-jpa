package by.home.repository;

import by.home.entity.StoreOrder;
import by.home.entity.status.OrderStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<StoreOrder, Long> {
    int countByOrderStatus(OrderStatusEnum statusEnum);
    StoreOrder getById(long id);
}
