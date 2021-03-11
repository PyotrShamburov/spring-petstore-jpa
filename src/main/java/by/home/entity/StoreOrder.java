package by.home.entity;

import by.home.entity.status.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StoreOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Positive(message = "Can be only positive number!")
    private long petId;

    @Positive(message = "Can be only positive number!")
    private int quantity;
    private String shipDate;
    @Enumerated(value = EnumType.STRING)
    private OrderStatusEnum orderStatus;
    private boolean complete;
}
