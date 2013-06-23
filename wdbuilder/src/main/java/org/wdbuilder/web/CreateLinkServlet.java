package org.wdbuilder.web;

import static org.apache.commons.lang.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.Link;
import org.wdbuilder.domain.LinkSocket;
import org.wdbuilder.input.InputParameter;
import org.wdbuilder.view.line.end.LineEnd;
import org.wdbuilder.web.base.EmptyOutputServlet;
import org.wdbuilder.web.base.ServletInput;

@WebServlet("/create-link")
public class CreateLinkServlet extends EmptyOutputServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void do4DiagramService(ServletInput input) throws Exception {
		final String diagramKey = InputParameter.DiagramKey.getString(input);
		if (isEmpty(diagramKey)) {
			return;
		}
		final SocketData begin = getFrom(input, InputParameter.BeginSocketKey);
		if (null == begin) {
			return;
		}
		final SocketData end = getFrom(input, InputParameter.EndSocketKey);
		if (null == end) {
			return;
		}

		Link link = createLink(input.getState().getDiagram(), begin, end);
		if (null == link) {
			return;
		}
		serviceFacade.getDiagramService().getLinkService(diagramKey)
				.persist(link);
	}

	private static SocketData getFrom(ServletInput input,
			InputParameter parameter) {
		final String str = parameter.getString(input);
		if (isEmpty(str)) {
			return null;
		}
		final String[] arr = str.split("[:]");
		if (3 > arr.length) {
			return null;
		}
		SocketData result = new SocketData();
		result.block = arr[0].trim();
		result.socket = arr[1].trim();
		result.index = Integer.valueOf(arr[2].trim());
		return result;
	}

	private static class SocketData {
		public String socket;
		public String block;
		public int index;
	}

	private Link createLink(Diagram diagram, SocketData begin, SocketData end) {
		final Block beginBlock = diagram.getBlock(begin.block);
		if (null == beginBlock) {
			return null;
		}
		final Block endBlock = diagram.getBlock(end.block);
		if (null == endBlock) {
			return null;
		}
		final LinkSocket beginSocket = new LinkSocket(begin.block,
				LinkSocket.Direction.valueOf(begin.socket), begin.index);
		beginSocket.setLineEnd(LineEnd.SIMPLE);

		final LinkSocket endSocket = new LinkSocket(end.block,
				LinkSocket.Direction.valueOf(end.socket), end.index);
		endSocket.setLineEnd(LineEnd.SOLID_ARROW);

		Link link = new Link();
		link.setKey(UUID.randomUUID().toString());
		link.setLineColor(Link.LineColor.Black);

		link.setSockets(new ArrayList<LinkSocket>(2));
		link.getSockets().add(beginSocket);
		link.getSockets().add(endSocket);

		link.calculatePivot(diagram);
		return link;
	}

}
