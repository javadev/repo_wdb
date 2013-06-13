package org.wdbuilder.gui;

import org.apache.commons.lang.StringUtils;
import org.wdbuilder.domain.DisplayNameAware;
import org.wdbuilder.input.IParameter;
import org.wdbuilder.jaxbhtml.IHtml;
import org.wdbuilder.jaxbhtml.element.A;
import org.wdbuilder.jaxbhtml.element.Form;
import org.wdbuilder.jaxbhtml.element.Input;
import org.wdbuilder.jaxbhtml.element.Label;
import org.wdbuilder.jaxbhtml.element.NoBr;
import org.wdbuilder.jaxbhtml.element.Table;
import org.wdbuilder.jaxbhtml.element.Td;
import org.wdbuilder.jaxbhtml.element.Tr;

public class TwoColumnForm extends Form {

	public static final String CLASS = "form";
	public static final String CLASS_LINKBUTTON = "btn btn-small";
	public static final String CLASS_TITLE = "title";
	private static final String ID = "formId";

	public static class LinkButton {
		private final String title;
		private final String onClickHandler;
		private final String className;

		public LinkButton(String title, String onClickHandler, String className) {
			this.onClickHandler = onClickHandler;
			this.title = title;
			this.className = className;
		}

		private A toHtml() {
			String className = StringUtils.isEmpty(this.className) ? CLASS_LINKBUTTON
					+ " " + this.className
					: CLASS_LINKBUTTON;

			A result = new A( className );
			result.setText(title);
			result.setOnClick(onClickHandler);
			return result;
		}
	}

	private final Table table;

	public TwoColumnForm(String action, String title) {
		super(CLASS);
		setId(ID);
		add(table = new Table(CLASS));

		// Create the form title:
		Td td = new Td();
		td.setColSpan(2);
		td.setText(title);
		td.setClassName(CLASS_TITLE);

		Tr tr = new Tr();
		tr.add(td);
		table.add(tr);
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

	public TwoColumnForm addFileField(IParameter parameter) {
		return addField(parameter.getDisplayName(), createFileField(parameter));
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
				new TwoColumnForm.LinkButton("Save", submitHandler,
						"btn-success"),
				new TwoColumnForm.LinkButton("Reset", "resetForm()",
						"btn-warning"),
				new TwoColumnForm.LinkButton("Close", closeHandler, "") });
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

	private static Input.File createFileField(IParameter parameter) {
		final Input.File input = new Input.File();
		input.setName(parameter.getName());
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
		Td.VAlign.top.set(fieldTd);
		fieldTd.add(element);
		return fieldTd;
	}

	private Td createLabelTd(String text) {
		Td labelTd = new Td(CLASS);
		Td.HAlign.right.set(labelTd);
		Td.VAlign.top.set(labelTd);

		Label label = new Label();
		label.setText(text + ": ");
		labelTd.add(label);
		return labelTd;
	}

}
