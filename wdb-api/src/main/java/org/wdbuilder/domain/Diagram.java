package org.wdbuilder.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso({ Block.class, Diagram.class, LinkSocket.class, Link.class })
public class Diagram extends SizedEntity {
    private static final long serialVersionUID = 1L;

    private Collection<Block> blocks = new ArrayList<Block>(1);

    private Collection<Link> links = new ArrayList<Link>(1);

    private DiagramBackground background;

    public Collection<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(Collection<Block> blocks) {
        this.blocks = null == blocks ? new ArrayList<Block>(1) : blocks;
    }

    public Collection<Link> getLinks() {
        return links;
    }

    public void setLinks(Collection<Link> links) {
        this.links = null == links ? new ArrayList<Link>(1) : links;
    }

    @XmlAttribute
    public DiagramBackground getBackground() {
        return background;
    }

    public void setBackground(DiagramBackground background) {
        if (null == background) {
            throw new IllegalArgumentException("Background can't be null");
        }
        this.background = background;
    }

    public Link getLink(String key) {
        return getByKey(key, links);
    }

    public Block getBlock(String key) {
        return getByKey(key, blocks);
    }

    private static <T extends Entity> T getByKey(String key, Collection<T> seq) {
        if (null == seq || null == key) {
            return null;
        }
        for (T result : seq) {
            if (key.equals(result.getKey())) {
                return result;
            }
        }
        return null;
    }

}
