package tacos.data;

import org.springframework.data.repository.CrudRepository;
import tacos.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
    // List<Order> findByDeliveryZip(final String deliveryZip);

    // List<Order> readOrdersByDeliveryZipAndPlacedAtBetween(final String deliveryZip, final Date startDate,
    // final Date endDate);

    // List<Order> findByDeliveryToAndDeliveryCityAllIgnoresCase(final String final deliveryTo,
    // final String deliveryCity);

    // List<Order> findByDeliveryCityOrderByDeliveryTo(String city);

    // @Query("Order o where o.deliveryCity='Barcelona'")
    // List<Order> readOrdersDeliveredInSeattle();
}
