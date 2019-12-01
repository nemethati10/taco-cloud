package tacos.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import tacos.Order;
import tacos.User;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    public List<Order> findByUserOrderByPlacedAtDesc(final User user, final Pageable pageable);

    // List<Order> findByDeliveryZip(final String deliveryZip);

    // List<Order> readOrdersByDeliveryZipAndPlacedAtBetween(final String deliveryZip, final Date startDate,
    // final Date endDate);

    // List<Order> findByDeliveryToAndDeliveryCityAllIgnoresCase(final String final deliveryTo,
    // final String deliveryCity);

    // List<Order> findByDeliveryCityOrderByDeliveryTo(String city);

    // @Query("Order o where o.deliveryCity='Barcelona'")
    // List<Order> readOrdersDeliveredInSeattle();
}
