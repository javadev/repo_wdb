package org.wdbuilder.plugin.common.domain;

import java.awt.Color;

import org.wdbuilder.domain.ISimpleBackgroundProvider;

public interface IGradientBackgroundProvider extends ISimpleBackgroundProvider {
  Color getSecondaryBackgroundColor();
}
