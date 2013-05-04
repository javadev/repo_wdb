package org.wdbuilder.input;


public abstract class InputAdapter {
	protected abstract String getParameter(String name);

	public String getString(IParameter parameter) {
		return getParameter(parameter.getName());
	}

	public int getInt(IParameter parameter) {
		// TODO: check for number here (2013/04/29)
		return Integer.valueOf(getString(parameter));
	}

	public boolean getBoolean(IParameter parameter) {
		// TODO: check for boolean here (2013/04/29)
		return Boolean.valueOf(getString(parameter));
	}
}