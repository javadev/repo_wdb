package _.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@SuppressWarnings("serial")
public class Order implements Serializable {
  private String id;
	private String consumer;
	private Date date;
	private Collection<Position> positions;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getConsumer() {
		return consumer;
	}

	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Collection<Position> getPositions() {
		return positions;
	}

	public void setPositions(Collection<Position> positions) {
		this.positions = positions;
	}

}
