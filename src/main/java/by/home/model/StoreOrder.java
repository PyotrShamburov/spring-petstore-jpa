package by.home.model;

import by.home.model.status.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private OrderStatusEnum orderStatus;
    private boolean complete;
}
