package org.wdbuilder.plugin.icon.domain;

import javax.xml.bind.annotation.XmlAttribute;

import org.wdbuilder.domain.Block;
import org.wdbuilder.plugin.icon.Icon;

public class IconBlock extends Block {
  private static final long serialVersionUID = 1L;

  private Icon icon;

  @XmlAttribute
  public Icon getIcon() {
    return icon;
  }

  public void setIcon(Icon icon) {
    if( null==icon ) {
      throw new IllegalArgumentException("Icon can't be empty" );
    }
    this.icon = icon;
  }

  @Override
    public int getMaxLinkSocketNumX() {
      return 0;
    }

  @Override
    public int getMaxLinkSocketNumY() {
      return 0;
    }

} 
