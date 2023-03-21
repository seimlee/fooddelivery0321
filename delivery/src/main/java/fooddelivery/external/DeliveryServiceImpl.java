package fooddelivery.external;

import org.springframework.stereotype.Service;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    /**
     * Fallback
     */
    public Delivery getDelivery(Long id) {
        Delivery delivery = new Delivery();
        return delivery;
    }
}
