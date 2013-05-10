package _.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;

@SuppressWarnings("serial")
@XmlSeeAlso(Position.class)
public class Order implements Serializable {
	private String id;
	private String consumer;
	private Collection<Position> positions;

	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlAttribute
	public String getConsumer() {
		return consumer;
	}

	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}

	public Collection<Position> getPositions() {
		return positions;
	}

	public void setPositions(Collection<Position> positions) {
		this.positions = positions;
	}

}
