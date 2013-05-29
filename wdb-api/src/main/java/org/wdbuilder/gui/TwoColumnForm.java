package org.wdbuilder.gui;

import org.wdbuilder.domain.DisplayNameAware;
import org.wdbuilder.input.IParameter;
import org.wdbuilder.jaxbhtml.IHtml;
import org.wdbuilder.jaxbhtml.element.A;
import org.wdbuilder.jaxbhtml.element.Form;
import org.wdbuilder.jaxbhtml.element.Input;
import org.wdbuilder.jaxbhtml.element.NoBr;
import org.wdbuilder.jaxbhtml.element.Span;
import org.wdbuilder.jaxbhtml.element.Table;
import org.wdbuilder.jaxbhtml.element.Td;
import org.wdbuilder.jaxbhtml.element.Tr;

public class TwoColumnForm extends Form {

	public static final String CLASS = "form";
	public static final String CLASS_LINKBUTTON = "btn";
	private static final String ID = "formId";

	public static class LinkButton {
		private final String title;
		private final String onClickHandler;

		public LinkButton(String title, String onClickHandler) {
			this.onClickHandler = onClickHandler;
			this.title = title;
		}

		private A toHtml() {
			A result = new A(CLASS_LINKBUTTON);
			Span span = new Span();
			span.setText(title);
			result.add(span);
			result.setOnClick(onClickHandler);
			return result;
		}
	}

	private final Table table;

	public TwoColumnForm(String action) {
		super(CLASS);
		setId(ID);
		add(table = new Table(CLASS));
	}

	public TwoColumnForm addLinks(LinkButton[] links) {
		Td td = new Td(CLASS);
		td.setColSpan(2);
		Td.HAlign.right.set(td);
		for (LinkButton link : links) {
			td.add(link.toHtml());
		}
		Tr tr = new Tr();
		tr.add(td);
		table.add(tr);
		return this;
	}

	public TwoColumnForm addTextField(IParameter parameter, String value) {
		return addField(parameter.getDisplayName(),
				createTextField(parameter, value));
	}

	public TwoColumnForm addHiddenField(IParameter parameter, String value) {
		add(createHiddenField(parameter, value));
		return this;
	}

	public <T extends DisplayNameAware> TwoColumnForm addSelectField(
			IParameter parameter, PredefinedSelect<T> select) {
		return addField(parameter.getDisplayName(),
				select.create(parameter.getName()));
	}

	public TwoColumnForm addReadOnlyField(IParameter parameter, String value) {
		Tr tr = new Tr();
		tr.add(createLabelTd(parameter.getDisplayName()));
		Td td = new Td(CLASS);
		Td.HAlign.left.set(td);

		NoBr wrapper = new NoBr();
		wrapper.setText(value);
		td.add(wrapper).add(createHiddenField(parameter, value));
		tr.add(td);
		table.add(tr);
		return this;
	}

	public TwoColumnForm addFooter(String submitHandler, String closeHandler) {
		addLinks(new TwoColumnForm.LinkButton[] {
				new TwoColumnForm.LinkButton("Save", submitHandler),
				new TwoColumnForm.LinkButton("Reset", "resetForm()"),
				new TwoColumnForm.LinkButton("Close", closeHandler) });
		return this;
	}

	private static Input.Hidden createHiddenField(IParameter parameter,
			String value) {
		final Input.Hidden input = new Input.Hidden();
		input.setName(parameter.getName());
		input.setValue(value);
		return input;
	}

	private static Input.Text createTextField(IParameter parameter, String value) {
		final Input.Text input = new Input.Text();
		input.setName(parameter.getName());
		input.setValue(value);
		return input;
	}

	private TwoColumnForm addField(String label, IHtml... element) {
		Tr tr = new Tr();
		tr.add(createLabelTd(label)).add(createFieldTd(element));
		table.add(tr);
		return this;
	}

	private Td createFieldTd(IHtml... element) {
		Td fieldTd = new Td(CLASS);
		Td.HAlign.left.set(fieldTd);
		fieldTd.add(element);
		return fieldTd;
	}

	private Td createLabelTd(String label) {
		Td labelTd = new Td(CLASS);
		Td.HAlign.right.set(labelTd);
		labelTd.setText(label + ":");
		return labelTd;
	}

}
