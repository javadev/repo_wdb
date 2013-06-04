package org.wdbuilder.gui;

import org.wdbuilder.domain.DisplayNameAware;
import org.wdbuilder.jaxbhtml.element.A;
import org.wdbuilder.jaxbhtml.element.B;
import org.wdbuilder.jaxbhtml.element.Div;
import org.wdbuilder.jaxbhtml.element.Input;
import org.wdbuilder.jaxbhtml.element.Li;
import org.wdbuilder.jaxbhtml.element.Span;
import org.wdbuilder.jaxbhtml.element.Ul;

public class PredefinedSelect<T extends DisplayNameAware> extends Div {
	
	private static final String HIDDEN_FIELD_ID_PREFIX = "ddf-";
  
	private final T[] values;
	private final T defaultValue;
	
	public PredefinedSelect( T[] values, T defaultValue ) {
		this.values = values;
		this.defaultValue = defaultValue;
	}
	

	public final Div create(String name) {
		final Div result = new Div( "dropdown" );
		result.setId(name);
		
		String valueStr = String.valueOf(defaultValue);
		
		// Add the hidden field:
		final Input hiddenField = new Input.Hidden();
		hiddenField.setName(name);
		hiddenField.setValue( valueStr );
		hiddenField.setId(HIDDEN_FIELD_ID_PREFIX + name );
		result.add(hiddenField);
		
		final A button = new A( "dropdown-toggle btn btn-mini" );
		button.setDataToggle("dropdown");
		button.setHref("#");
		
		final Span text = new Span();
		text.setText(valueStr + "  ");		
		button.add( text );
		
		final B downArrow = new B( "caret");
		downArrow.setText( " " );
		button.add( downArrow );
		
		result.add( button );
		
		Ul ul = new Ul("dropdown-menu");
		for( final T item : this.values ) {
			ul.add(createOption(item));
		}
		result.add( ul );
		return result;
		
	}

	private Li createOption(final T item) {
		final Li result = new Li();
		A a = new A();
		a.setText(String.valueOf(item));
		a.setHref("#");
		
		// TODO Set on click: change hidden field value
		
		result.add(a);
		
		return result;
	}
}
