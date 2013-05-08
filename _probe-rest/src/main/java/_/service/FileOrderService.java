package _.service;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import _.domain.OrderList;

public class FileOrderService implements IOrderService {

	private static final String FILE_NAME = "order-db.xml";

	@Override
	public OrderList retrieveForConsumer(String consumer) throws Exception {
		JAXBContext context = JAXBContext.newInstance(OrderList.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Object obj = unmarshaller.unmarshal(new File(FILE_NAME));
		OrderList result = OrderList.class.cast(obj);

		return result;
	}

}
