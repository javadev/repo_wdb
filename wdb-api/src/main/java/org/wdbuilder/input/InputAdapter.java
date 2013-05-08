package org.wdbuilder.input;

import org.wdbuilder.domain.DisplayNameAware;


public interface IParameter extends DisplayNameAware {
	
	public String getName();
	
	public String getString(InputAdapter input);

	public int getInt(InputAdapter input);
	
	public boolean getBoolean(InputAdapter input);
	
}
