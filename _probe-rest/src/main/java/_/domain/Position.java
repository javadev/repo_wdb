package _.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;

@SuppressWarnings("serial")
public class Position implements Serializable {
	private String goodId;
	private int quantity;
	private double price;

	@XmlAttribute
	public String getGoodId() {
		return goodId;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	@XmlAttribute
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@XmlAttribute
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
