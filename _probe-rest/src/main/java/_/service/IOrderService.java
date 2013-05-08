package _.service;

import _.domain.OrderList;

public interface IOrderService {
  OrderList retrieveForConsumer(String consumer) throws Exception;
}
