package org.wdbuilder.plugin;

public interface IRenderer<T, S> {
	void draw(T object, S context);
}
