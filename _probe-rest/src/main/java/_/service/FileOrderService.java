package _.service;

import java.net.URL;
import java.util.Collection;

import javax.annotation.Nullable;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import _.domain.Order;
import _.domain.OrderList;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.io.Resources;

public class FileOrderService implements IOrderService {

	private static final String FILE_NAME = "order-db.xml";

	@Override
	public OrderList retrieveForConsumer(final String consumer) throws Exception {
		JAXBContext context = JAXBContext.newInstance(OrderList.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		URL url = Resources.getResource(FILE_NAME);
		Object obj = unmarshaller.unmarshal( url.openStream());
		OrderList result = OrderList.class.cast(obj);
		
		Collection<Order> list = result.getOrders();
		Collection<Order> filtered = Collections2.filter(list, new Predicate<Order>() {

			@Override
			public boolean apply(@Nullable Order order) {
				if( null==consumer ) {
					return false;
				}
				return consumer.equals(order.getConsumer());
			}
		});
		result.setOrders(filtered);

		return result;
	}

}
