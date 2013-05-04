package org.wdbuilder.jaxbhtml;

import java.util.Arrays;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;

public abstract class AHtmlContainer<T extends IHtml> extends HtmlElement {

	public AHtmlContainer() {
		super();
	}

	public AHtmlContainer(String className) {
		super(className);
	}

	public abstract Collection<T> getContent();

	@XmlElement
	private String span;

	public final AHtmlContainer<T> add(Collection<T> content) {
		if (null != content) {
			getContent().addAll(content);
		}
		return this;
	}

	public final AHtmlContainer<T> add(T[] content) {
		if (null != content) {
			add(Arrays.asList(content));
		}
		return this;
	}

	public final AHtmlContainer<T> add(T content) {
		if (null != content) {
			getContent().add(content);
		}
		return this;
	}

	public final AHtmlContainer<T> setText(String text) {
		getContent().clear();
		this.span = text;
		return this;
	}
}
