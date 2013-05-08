package _.service;

import java.util.Collection;

import _.domain.Order;

public interface IOrderService {
  Collection<Order> retrieveForConsumer(String consumer);
}
