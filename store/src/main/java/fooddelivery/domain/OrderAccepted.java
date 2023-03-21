package fooddelivery.domain;

import fooddelivery.domain.*;
import fooddelivery.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class OrderAccepted extends AbstractEvent {

    private Long id;

    public OrderAccepted(Cooking aggregate) {
        super(aggregate);
    }

    public OrderAccepted() {
        super();
    }
}
