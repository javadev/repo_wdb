package org.wdbuilder.plugin.defaultlink;

import static org.apache.commons.lang.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;

import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.Link;
import org.wdbuilder.domain.LinkSocket;
import org.wdbuilder.gui.UIExistingEntityFormFactory;
import org.wdbuilder.input.InputParameter;
import org.wdbuilder.input.IParameter;
import org.wdbuilder.input.InputAdapter;
import org.wdbuilder.plugin.ILinkPluginFacade;
import org.wdbuilder.plugin.ILinkRenderContext;
import org.wdbuilder.plugin.IRenderer;
import org.wdbuilder.validator.CompositeValidator;
import org.wdbuilder.validator.IValidator;
import org.wdbuilder.view.LinkRenderer;
import org.wdbuilder.view.line.LineStyle;
import org.wdbuilder.view.line.end.LineEnd;

public class DefaultLinkPluginFacade implements ILinkPluginFacade {

    private final class ValidatorCompositeValidator extends CompositeValidator<Link> {
        private final class NestedValidatorsIValidator2 implements IValidator<Link> {
            @Override
            public void validate(Diagram diagram, Link link)
                    throws IllegalArgumentException {
                if (2 > link.getSockets().size()) {
                    new IllegalArgumentException(
                            "Link should have at least 2 ends");
                }
            }
        }

        private final class NestedValidatorsIValidator implements IValidator<Link> {
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
        }

        private ValidatorCompositeValidator(IValidator<Link> baseValidator) {
            super(baseValidator);
        }

        @Override
        protected Iterable<IValidator<Link>> getNestedValidators() {
            final List<IValidator<Link>> result = new ArrayList<IValidator<Link>>(
                    2);
            result.add(new NestedValidatorsIValidator());
            result.add(new NestedValidatorsIValidator2());

            return result;
        }
    }

    public enum Parameter implements IParameter {
        LineColor("lineColor", "Line Color"), LineStyle("lineStyle",
                "Line Style"), StartType("s0", "Line Start Type"), EndType(
                "s1", "Line End Type");

        private final String name;
        private final String label;

        Parameter(String name, String label) {
            this.name = name;
            this.label = label;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getDisplayName() {
            return label;
        }

        @Override
        public String getString(InputAdapter input) {
            return input.getString(this);
        }

        @Override
        public int getInt(InputAdapter input) {
            return input.getInt(this);
        }

        @Override
        public boolean getBoolean(InputAdapter input) {
            return input.getBoolean(this);
        }

    }

    @Override
    public Class<?> getEntityClass() {
        return Link.class;
    }

    @Override
    public IValidator<Link> getValidator() {
        return new ValidatorCompositeValidator(null);
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
        result.setName(InputParameter.Name.getString(input));
        result.setLineColor(Link.LineColor.valueOf(Parameter.LineColor
                .getString(input)));
        result.setLineStyle(LineStyle.valueOf(Parameter.LineStyle
                .getString(input)));

        // Create fake sockets: (TODO: not the best approach (2013/05/07))
        List<LinkSocket> socketList = new ArrayList<LinkSocket>(2);
        socketList.add(createLinkSocket(Parameter.StartType, input));
        socketList.add(createLinkSocket(Parameter.EndType, input));
        result.setSockets(socketList);

        return result;
    }

    private static LinkSocket createLinkSocket(Parameter param,
            InputAdapter input) {
        LinkSocket result = new LinkSocket();
        result.setLineEnd(LineEnd.valueOf(param.getString(input)));
        return result;
    }
}
