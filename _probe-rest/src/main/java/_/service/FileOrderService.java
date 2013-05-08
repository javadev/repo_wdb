package _.service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import _.domain.OrderList;

public class FileOrderService implements IOrderService {
	
	private static final String FILE_NAME = "order-db.xml";

	@Override
	public OrderList retrieveForConsumer(String consumer) throws Exception {
		JAXBContext context = JAXBContext.newInstance(OrderList.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		
		
		
		// TODO Auto-generated method stub
		return null;
	}

}
