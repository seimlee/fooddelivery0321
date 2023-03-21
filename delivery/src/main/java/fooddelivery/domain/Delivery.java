package fooddelivery.domain;

import fooddelivery.DeliveryApplication;
import fooddelivery.domain.DeliveryConfirmed;
import fooddelivery.domain.FoodPicked;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Delivery_table")
@Data
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String menuid;

    private String pay;

    private String address;

    @PostPersist
    public void onPostPersist() {
        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        fooddelivery.external.Delivery delivery = new fooddelivery.external.Delivery();
        // mappings goes here
        DeliveryApplication.applicationContext
            .getBean(fooddelivery.external.DeliveryService.class)
            .confirm(delivery);

        FoodPicked foodPicked = new FoodPicked(this);
        foodPicked.publishAfterCommit();

        DeliveryConfirmed deliveryConfirmed = new DeliveryConfirmed(this);
        deliveryConfirmed.publishAfterCommit();
    }

    public static DeliveryRepository repository() {
        DeliveryRepository deliveryRepository = DeliveryApplication.applicationContext.getBean(
            DeliveryRepository.class
        );
        return deliveryRepository;
    }
}
