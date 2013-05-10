package _.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@SuppressWarnings("serial")
@XmlRootElement
@XmlSeeAlso(Order.class)
public class OrderList implements Serializable {
  private Collection<Order> orders;

	public Collection<Order> getOrders() {
		return orders;
	}

	public void setOrders(Collection<Order> orders) {
		this.orders = orders;
	}
	
	
}
