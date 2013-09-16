package org.wdbuilder.plugin.common;

import static org.wdbuilder.util.ImageUtility.getGraphics;
import static org.wdbuilder.util.ImageUtility.getImageObserver;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.wdbuilder.domain.Block;
import org.wdbuilder.plugin.IRenderContext;
import org.wdbuilder.plugin.common.domain.CommonBlock;
import org.wdbuilder.plugin.common.domain.IGradientBackgroundProvider;
import org.wdbuilder.view.BlockRenderer;
import org.wdbuilder.view.IBackgroundRenderer;

class CommonBlockRenderer extends BlockRenderer<Block> {

    // Helper methods and values:
    @Override
    public void draw(Block blockRaw, IRenderContext renderCtx) {
        if (!CommonBlock.class.isInstance(blockRaw)) {
            return;
        }
        CommonBlock block = CommonBlock.class.cast(blockRaw);

        // Use additional image:
        final BufferedImage blockImage = new BufferedImage(block.getSize()
                .getWidth(), block.getSize().getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        final Graphics2D blockGraphics = getGraphics(blockImage);

        renderBlockInSeparateGraphicsContext(blockGraphics, block, renderCtx);

        Graphics2D gr = renderCtx.getGraphics();

        if (!renderCtx.isOpaque()) {
            gr.setComposite(AlphaComposite
                    .getInstance(AlphaComposite.SRC, 0.5f));
        }

        java.awt.Dimension size = block.getSize().toAWT();
        int x = renderCtx.getOffset().getX() - size.width / 2;
        int y = renderCtx.getOffset().getY() - size.height / 2;

        // Copy the image:
        gr.drawImage(blockImage, x, y, getImageObserver());

        renderLinkSockets(block, renderCtx);
    }

    private static void drawText(Graphics2D gr, CommonBlock block) {
        gr.setComposite(AlphaComposite.Src);
        gr.setColor(block.getBackground().getForegroundColor());
        gr.setFont(FONT);
        final String str = block.getName();
        final FontMetrics metrics = gr.getFontMetrics(FONT);
        final int width = metrics.stringWidth(str);
        final int height = metrics.getHeight();

        final int centerX = block.getSize().getWidth() / 2;
        final int centerY = block.getSize().getHeight() / 2;

        gr.drawString(str, centerX - width / 2, centerY + height / 4);
    }

    private static void renderBlockInSeparateGraphicsContext(Graphics2D gr,
            CommonBlock block, IRenderContext renderCtx) {
        final java.awt.Dimension size = block.getSize().toAWT();

        gr.setComposite(AlphaComposite.Xor);
        gr.setColor(Color.white);
        gr.fillRect(0, 0, size.width, size.height);
        gr.setColor(Color.black);

        final Rectangle rect = new Rectangle(0, 0, size.width, size.height);
        block.getShape().fill(gr, rect);

        IBackgroundRenderer<IGradientBackgroundProvider> backgroundRenderer =
                new VerticalGradientBackgroundRenderer();
        backgroundRenderer.render(gr, block, block.getBackground());

        drawText(gr, block);
    }

}
