package org.wdbuilder.input;

import static org.apache.commons.lang.StringUtils.isNumeric;

public abstract class InputAdapter {
	protected abstract String getParameter(String name);

	public String getString(IParameter parameter) {
		return getParameter(parameter.getName());
	}

	public int getInt(IParameter parameter) {
		final String str = getString(parameter);
		if (!isNumeric(str)) {
			return 0; // default value
		}
		return Integer.valueOf(str);
	}

	public boolean getBoolean(IParameter parameter) {
		final String str = getString(parameter);
		if (!"true".equals(str) && !"false".equals(str)) {
			return false;
		}
		return Boolean.valueOf(str);
	}
}