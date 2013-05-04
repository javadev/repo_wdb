package org.wdbuilder.view;

import java.awt.Graphics2D;

import org.wdbuilder.domain.DiagramEntity;
import org.wdbuilder.domain.ISimpleBackgroundProvider;


public interface IBackgroundRenderer<B extends ISimpleBackgroundProvider> {
  void render( Graphics2D gr, DiagramEntity entity,  B background);
} 
