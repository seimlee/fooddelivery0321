package fooddelivery.domain;

import fooddelivery.CustomerApplication;
import fooddelivery.domain.OrderPlaced;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Order_table")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String menuid;

    private String customerid;

    private String quantity;

    private String status;

    @PostPersist
    public void onPostPersist() {
        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        fooddelivery.external.AcceptOrRejectCommand acceptOrRejectCommand = new fooddelivery.external.AcceptOrRejectCommand();
        // mappings goes here
        CustomerApplication.applicationContext
            .getBean(fooddelivery.external.CookingService.class)
            .acceptOrReject(/* get???(), */acceptOrRejectCommand);

        OrderPlaced orderPlaced = new OrderPlaced(this);
        orderPlaced.publishAfterCommit();
    }

    public static OrderRepository repository() {
        OrderRepository orderRepository = CustomerApplication.applicationContext.getBean(
            OrderRepository.class
        );
        return orderRepository;
    }
}
