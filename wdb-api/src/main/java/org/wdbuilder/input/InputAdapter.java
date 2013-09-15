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
            // default value
            return 0;
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
