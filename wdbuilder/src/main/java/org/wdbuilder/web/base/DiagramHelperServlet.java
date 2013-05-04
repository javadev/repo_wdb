package org.wdbuilder.web.base;

import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.utility.DiagramHelper;

import static org.apache.commons.lang.StringUtils.isEmpty;

public abstract class DiagramHelperServlet extends DiagramServiceServlet {
	private static final long serialVersionUID = 1L;

	protected DiagramHelper diagramHelper = null;

	protected abstract void do4DiagramHelper(ServletInput input)
			throws Exception;

	@Override
	protected void do4DiagramService(ServletInput input) throws Exception {
		if (null == service) {
			throw new IllegalArgumentException(
					"Diagram service is not initialized");
		}
		final String key = BlockParameter.DiagramKey.getString(input);
		if (isEmpty(key)) {
			throw new IllegalArgumentException("Diagram key is undefined");
		}
		this.diagramHelper = createDiagramHelper(key);
		do4DiagramHelper(input);
	}

}
