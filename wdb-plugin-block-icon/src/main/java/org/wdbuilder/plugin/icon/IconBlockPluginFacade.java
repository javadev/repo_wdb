package org.wdbuilder.plugin.icon;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.helper.Dimension;
import org.wdbuilder.gui.IUIActionClick;
import org.wdbuilder.gui.UIExistingEntityFormFactory;
import org.wdbuilder.gui.UINewBlockFormFactory;
import org.wdbuilder.input.InputParameter;
import org.wdbuilder.input.IParameter;
import org.wdbuilder.input.InputAdapter;
import org.wdbuilder.plugin.IBlockPluginFacade;
import org.wdbuilder.plugin.IRenderContext;
import org.wdbuilder.plugin.IRenderer;
import org.wdbuilder.plugin.icon.domain.IconBlock;
import org.wdbuilder.validator.BlockValidator;
import org.wdbuilder.validator.IValidator;

public class IconBlockPluginFacade implements IBlockPluginFacade {

    private final class UIActionCreateIUIActionClick extends IUIActionClick {
        private final String diagramKey;

        private UIActionCreateIUIActionClick(String diagramKey) {
            this.diagramKey = diagramKey;
        }

        @Override
        public String getTitle() {
            return "New Icon Diagram Block";
        }

        @Override
        public String getResourceId() {
            return "icon-picture";
        }

        @Override
        public String getOnClickHandler() {
            return "openCreateBlockDialog(" + diagramKey + ", '"
                    + getEntityClass().getCanonicalName() + "' )";
        }

        @Override
        public String getClassName() {
            return "btn-success";
        }
    }

    static enum Parameter implements IParameter {
        IconID("iconID", "Icon");

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

        @Override
        public String getDisplayName() {
            return label;
        }
    }

    @Override
    public Class<?> getEntityClass() {
        return IconBlock.class;
    }

    @Override
    public IRenderer<Block, IRenderContext> getRenderer() {
        return new IconBlockRenderer();
    }

    @Override
    public Block create(InputAdapter input) {
        final IconBlock result = new IconBlock();
        final String iconIdStr = Parameter.IconID.getString(input);
        final Icon icon = Icon.valueOf(iconIdStr);
        Dimension size = icon.getSize();
        result.setSize(size);
        result.setName(InputParameter.Name.getString(input));
        result.setIcon(icon);
        return result;
    }

    @Override
    public IUIActionClick getUIActionCreate(final String diagramKey) {
        return new UIActionCreateIUIActionClick(diagramKey);

    }

    @Override
    public IValidator<Block> getValidator() {
        return new BlockValidator();
    }

    @Override
    public UINewBlockFormFactory getCreateFormFactory(String diagramKey) {
        return new CreateFormFactory(diagramKey, getEntityClass());
    }

    @Override
    public UIExistingEntityFormFactory<Block> getEditFormFactory(
            String diagramKey, Block block) {
        return new EditFormFactory(diagramKey, block);
    }
}
