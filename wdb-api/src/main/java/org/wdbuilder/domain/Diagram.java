package org.wdbuilder.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * Diagram, that contains child elements (blocks)
 * 
 * @author o.pavloschuk
 * 
 */

@XmlRootElement
//@XmlSeeAlso({ CommonBlock.class, IconBlock.class, Block.class, Diagram.class, LinkSocket.class, Link.class })
@XmlSeeAlso({ Block.class, Diagram.class, LinkSocket.class, Link.class })
public class Diagram extends DiagramEntity {
	private static final long serialVersionUID = 1L;

	private Collection<Block> blocks;

	private Collection<Link> links;

	private DiagramBackground background;

	public Collection<Block> getBlocks() {
		return blocks;
	}

	public void setBlocks(Collection<Block> blocks) {
		this.blocks = null==blocks ?  new ArrayList<Block>(1) : blocks;
	}

	public Collection<Link> getLinks() {
		return links;
	}

	public void setLinks(Collection<Link> links) {
		this.links = null==links ? new ArrayList<Link>(1) : links;
	}

	@XmlAttribute
	public DiagramBackground getBackground() {
		return background;
	}

	public void setBackground(DiagramBackground background) {
		if( null==background ) {
			throw new IllegalArgumentException( "Background can't be null");
		}
		this.background = background;
	}
}
