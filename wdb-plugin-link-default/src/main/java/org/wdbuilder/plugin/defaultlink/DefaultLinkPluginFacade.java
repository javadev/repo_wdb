package org.wdbuilder.plugin.defaultlink;

import static org.apache.commons.lang.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;

import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.Link;
import org.wdbuilder.gui.UIExistingEntityFormFactory;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.input.InputAdapter;
import org.wdbuilder.plugin.ILinkPluginFacade;
import org.wdbuilder.plugin.ILinkRenderContext;
import org.wdbuilder.plugin.IRenderer;
import org.wdbuilder.validator.CompositeValidator;
import org.wdbuilder.validator.IValidator;
import org.wdbuilder.view.LinkRenderer;

public class DefaultLinkPluginFacade implements ILinkPluginFacade {

	@Override
	public Class<?> getEntityClass() {
		return Link.class;
	}

	@Override
	public IValidator<Link> getValidator() {
		return new CompositeValidator<Link>(null) {

			@Override
			protected Iterable<IValidator<Link>> getNestedValidators() {
				final List<IValidator<Link>> result = new ArrayList<IValidator<Link>>(
						2);
				result.add(new IValidator<Link>() {

					@Override
					public void validate(Diagram diagram, Link link)
							throws IllegalArgumentException {
						if (null == link) {
							throw new IllegalArgumentException(
									"Link can't be null");
						}
						if (isEmpty(link.getKey())) {
							throw new IllegalArgumentException(
									"Link key can't be empty");

						}
						if (null == link.getSockets()) {
							throw new IllegalArgumentException(
									"Socket list cat be null");

						}
					}
				});
				result.add(new IValidator<Link>() {

					@Override
					public void validate(Diagram diagram, Link link)
							throws IllegalArgumentException {
						if (2 > link.getSockets().size()) {
							new IllegalArgumentException(
									"Link should have at least 2 ends");
						}
					}

				});

				return result;
			}
		};
	}

	@Override
	public UIExistingEntityFormFactory<Link> getEditFormFactory(
			String diagramKey, Link entity) {
		return new EditFormFactory(diagramKey, entity);
	}

	@Override
	public IRenderer<Link, ILinkRenderContext> getRenderer() {
		return new LinkRenderer();
	}

	@Override
	public Link create(InputAdapter input) {
		Link result = new Link();
		result.setName( BlockParameter.Name.getString(input));
		return result;
	}

}
