package org.wdbuilder.web;

import javax.servlet.annotation.WebServlet;

import org.wdbuilder.web.base.EmptyOutputServlet;
import org.wdbuilder.web.base.ServletInput;

@WebServlet("/switch-mode")
public class SwitchModeServlet extends EmptyOutputServlet {
    private static final long serialVersionUID = 1L;

	@Override
	protected void do4DiagramService( ServletInput input) throws Exception {
		ApplicationState state = input.getState();
		ApplicationState.Mode mode = state.getMode();
		if (ApplicationState.Mode.LINE.equals(mode)) {
			mode = ApplicationState.Mode.BLOCK;
		} else {
			mode = ApplicationState.Mode.LINE;
		}
		state.setMode(mode);
	}
}
