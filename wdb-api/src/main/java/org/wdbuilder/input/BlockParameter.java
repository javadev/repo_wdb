package org.wdbuilder.input;


// TODO: rename it into "simple" Parameter (2013/04/30)
public enum BlockParameter implements IParameter {
	DiagramKey("dkey", "Diagram Key"), 
	BlockKey("bkey", "Block Key"), 
	LinkKey("lkey", "Link Key"),
	Width("width", "Width"), 
	Height("height", "Height"), 
	Name( "name", "Name"), 
	BlockClass("blockClass", "Block Class"), 
	BeginSocketKey("beginSocketId", "Begin Socket Key"), 
	EndSocketKey( "endSocketId", "End Socket Key"), 
	Full("full", "Full/Condensed Diagram List"), 
	BlockOnly("blockOnly", "load block only"), 
	X("x", "X"), 
	Y("y", "Y"),
	Background("background", "Diagram Background");
	
	private final String name;
	private final String label;

	BlockParameter(String name, String label) {
		this.name = name;
		this.label = label;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String getLabel() {
		return label;
	}
	

	@Override
	public String getString(InputAdapter input) {
		return input.getString(this);
	}

	@Override
	public int getInt(InputAdapter input) {
		return input.getInt(this);
	}

	@Override
	public boolean getBoolean(InputAdapter input) {
		return input.getBoolean(this);
	}

}
