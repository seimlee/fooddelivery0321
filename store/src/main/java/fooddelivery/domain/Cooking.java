package fooddelivery.domain;

import fooddelivery.StoreApplication;
import fooddelivery.domain.OrderRejected;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Cooking_table")
@Data
public class Cooking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String menu;

    private String customerid;

    @PostPersist
    public void onPostPersist() {
        OrderRejected orderRejected = new OrderRejected(this);
        orderRejected.publishAfterCommit();
        // Get request from Cooking
        //fooddelivery.external.Cooking cooking =
        //    Application.applicationContext.getBean(fooddelivery.external.CookingService.class)
        //    .getCooking(/** mapping value needed */);

    }

    public static CookingRepository repository() {
        CookingRepository cookingRepository = StoreApplication.applicationContext.getBean(
            CookingRepository.class
        );
        return cookingRepository;
    }

    public void acceptOrReject(AcceptOrRejectCommand acceptOrRejectCommand) {
        OrderAccepted orderAccepted = new OrderAccepted(this);
        orderAccepted.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        fooddelivery.external.Cooking cooking = new fooddelivery.external.Cooking();
        // mappings goes here
        StoreApplication.applicationContext
            .getBean(fooddelivery.external.CookingService.class)
            .start(cooking);
    }

    public void start() {
        CookStarted cookStarted = new CookStarted(this);
        cookStarted.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        fooddelivery.external.Cooking cooking = new fooddelivery.external.Cooking();
        // mappings goes here
        StoreApplication.applicationContext
            .getBean(fooddelivery.external.CookingService.class)
            .finish(cooking);
    }

    public void finish() {
        CookFinished cookFinished = new CookFinished(this);
        cookFinished.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        fooddelivery.external.Delivery delivery = new fooddelivery.external.Delivery();
        // mappings goes here
        StoreApplication.applicationContext
            .getBean(fooddelivery.external.DeliveryService.class)
            .pickFood(delivery);
    }
}
