package org.wdbuilder.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Collection;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.LinkSocket;
import org.wdbuilder.plugin.IRenderContext;
import org.wdbuilder.plugin.IRenderer;

public abstract class BlockRenderer<T extends Block> implements
        IRenderer<T, IRenderContext> {
    protected static final Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 12);

    protected static void renderLinkSockets(Block block,
            IRenderContext renderCtx) {
        if (renderCtx.isBlockMode()) {
            return;
        }

        // Draw sockets for line mode:
        final Collection<LinkSocket> forbidenLinkSockets = renderCtx
                .getUsedLinkSockets(block);
        final Collection<LinkSocket> sockets = LinkSocket.getAvailable(
                forbidenLinkSockets, block);
        drawLinkSockets(renderCtx.getGraphics(), block, sockets);
    }

    private static void drawLinkSockets(Graphics2D gr, Block block,
            Collection<LinkSocket> sockets) {
        for (final LinkSocket socket : sockets) {
            Rectangle area = socket.getArea(block);
            gr.setColor(Color.black);
            gr.fillRect(area.x, area.y, area.width, area.height);
            gr.setColor(Color.white);
            gr.fillRect(area.x + 1, area.y + 1, area.width - 2, area.height - 2);
        }
    }
}
