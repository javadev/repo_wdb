package org.wdbuilder.input;


public interface IParameter {
	
	public String getName();
	
	// TODO: pass the Locale there (2013/04/30)
	public String getLabel();
	
	public String getString(InputAdapter input);

	public int getInt(InputAdapter input);
	
	public boolean getBoolean(InputAdapter input);
	
}
