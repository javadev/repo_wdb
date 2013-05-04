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

	public final Select create(String name, String valueRaw ) {
		final Select result = new Select();
		result.setName(name);
		result.setId(name);
		final String value = getValueFrom(valueRaw);
		for( final T item : this.values ) {
			result.add(createOption(value, item));
		}
		return result;
	}

	private Option createOption(final String value, final T item) {
		String valueStr = String.valueOf(item);
		final boolean selected = valueStr.equals(value);
		final Option option = new Option(item.getDisplayName());
		option.setValue( valueStr );
		option.setSelected(selected);
		return option;
	}

	private String getValueFrom(String valueRaw) {
		return null == valueRaw || valueRaw.trim().isEmpty() ? String.valueOf( this.defaultValue )
		        : valueRaw;
	}
}
