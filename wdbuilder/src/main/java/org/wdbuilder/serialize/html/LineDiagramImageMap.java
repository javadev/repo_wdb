package org.wdbuilder.serialize.html;

import java.awt.Rectangle;
import java.util.Collection;
import java.util.Set;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.Link;
import org.wdbuilder.domain.LinkSocket;
import org.wdbuilder.domain.helper.Point;
import org.wdbuilder.jaxbhtml.element.Area;
import org.wdbuilder.service.DiagramService;

public class LineDiagramImageMap extends DiagramImageMap {

    protected LineDiagramImageMap(Diagram diagram) {
        super(diagram);
    }

    @Override
    protected void addForLink(Link link) {
        final Point pivot = link.getPivot();
        final Point topLeft = new Point(pivot.getX()
                - DiagramService.LINE_AREA.getWidth() / 2, pivot.getY()
                - DiagramService.LINE_AREA.getHeight() / 2);

        final Area.Rect area = new Area.Rect(topLeft.toAWT(),
                DiagramService.LINE_AREA.toAWT());
        area.setOnMouseDown(createOnMouseDownHandler(link));
        area.setTitle(link.getKey());
        
        add(area);
    }

    private String createOnMouseDownHandler(Link link) {
        final LinkSocket beginSocket = link.getSockets().get(0);
        final LinkSocket endSocket = link.getSockets().get(1);

        boolean isHorizontal = beginSocket.isHorizontal();

        Point beginPoint = beginSocket.getLocation(diagram);
        Point endPoint = endSocket.getLocation(diagram);

        final String result = getOnMouseDownFunctionCall(
                "WDB.LinkArrange.mouseDown", diagram.getKey(), link.getKey(),
                beginPoint, endPoint, isHorizontal);
        return result;
    }

    @Override
    protected void addForBlock(Block block) {
        Set<LinkSocket> usedSockets = block.getUsedLinkSockets(diagram);
        final Collection<LinkSocket> sockets = LinkSocket.getAvailable(
                usedSockets, block);

        // Add possible line start and end points
        for (final LinkSocket socket : sockets) {
            Rectangle rect = socket.getArea(block);

            String id = block.getKey() + ":"
                    + String.valueOf(socket.getDirection()) + ":"
                    + socket.getIndex();

            String onMouseDown = getOnMouseDownFunctionCall(
                    "WDB.LineDraw.mouseDown", diagram.getKey(), id,
                    block.getLocation());

            Area.Rect area = new Area.Rect(rect.getLocation(), rect.getSize());
            area.setOnMouseDown(onMouseDown);
            area.setTitle(block.getName());
            area.setId(id);
            add(area);
        }
    }
}
