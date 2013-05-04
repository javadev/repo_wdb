package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.web.base.EmptyOutputServlet;
import org.wdbuilder.web.base.ServletInput;

import static org.apache.commons.lang.StringUtils.isEmpty;

@WebServlet("/create-link")
public class CreateLinkServlet extends EmptyOutputServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void do4DiagramService(ServletInput input) throws Exception {
		final String diagramKey = BlockParameter.DiagramKey.getString(input);
		if (isEmpty(diagramKey)) {
			return;
		}
		final SocketData begin = getFrom(input, BlockParameter.BeginSocketKey);
		if (null == begin) {
			return;
		}
		final SocketData end = getFrom(input, BlockParameter.EndSocketKey);
		if (null == end) {
			return;
		}
		service.persistLink(diagramKey, begin.block, begin.socket, begin.index, end.block, end.socket, end.index);
	}

	private static SocketData getFrom(ServletInput input, BlockParameter parameter) {
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

}
