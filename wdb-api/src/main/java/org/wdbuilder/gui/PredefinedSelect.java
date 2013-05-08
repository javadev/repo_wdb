package org.wdbuilder.gui;

import org.wdbuilder.domain.DisplayNameAware;
import org.wdbuilder.jaxbhtml.element.Option;
import org.wdbuilder.jaxbhtml.element.Select;

public class PredefinedSelect<T extends DisplayNameAware> extends Select {
  
	private final T[] values;
	private final T defaultValue;
	
	public PredefinedSelect( T[] values, T defaultValue ) {
		this.values = values;
		this.defaultValue = defaultValue;
	}

	public final Select create(String name) {
		final Select result = new Select();
		result.setName(name);
		result.setId(name);
		for( final T item : this.values ) {
			result.add(createOption(item));
		}
		return result;
	}

	private Option createOption(final T item) {
		final boolean selected = item.equals(defaultValue);
		final Option option = new Option(item.getDisplayName());
		option.setValue( String.valueOf(item) );
		option.setSelected(selected);
		return option;
	}
}
