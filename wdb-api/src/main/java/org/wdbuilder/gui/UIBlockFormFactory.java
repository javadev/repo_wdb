package org.wdbuilder.gui;

import org.wdbuilder.input.IParameter;

abstract class UIBlockFormFactory {

	public abstract TwoColumnForm getForm();

	public abstract String getSubmitCall();

	public abstract String getTitle();

	protected final String diagramKey;

	UIBlockFormFactory(String diagramKey) {
		this.diagramKey = diagramKey;
	}

	protected void appendFieldNames(StringBuilder sb,
			Iterable<IParameter> params) {
		sb.append('[');
		for (final IParameter param : params) {
			sb.append("'").append(param.getName()).append("',");
		}
		int len = sb.length();
		sb.replace(len - 1, len, "]");
	}

}
