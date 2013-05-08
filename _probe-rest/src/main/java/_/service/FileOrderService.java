package _.service;

import java.util.Collection;

import javax.xml.bind.JAXBContext;

import _.domain.Order;
import _.domain.Position;

public class FileOrderService implements IOrderService {
  
	private static final String FILE_NAME = "order-db.xml";

	@Override
	public Collection<Order> retrieveForConsumer(String consumer) throws Exception {
		JAXBContext context = JAXBContext.newInstance(Order.class, Position.class);
		
		// TODO Auto-generated method stub
		return null;
	}

}
