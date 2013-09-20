package org.wdbuilder.web.base;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.wdbuilder.input.InputAdapter;
import org.wdbuilder.web.ApplicationState;

public class ServletInput extends InputAdapter {
    private static final String APP_STATE_ATTR = "APP_STATE";

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final ApplicationState state;

    public ServletInput(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        HttpSession session = request.getSession(true);
        if (null == session) {
            this.state = new ApplicationState();
        } else {
            Object resultObj = session.getAttribute(APP_STATE_ATTR);
            if (null == resultObj) {
                state = new ApplicationState();
                session.setAttribute(APP_STATE_ATTR, state);
            } else {
                state = (ApplicationState) resultObj;
            }
        }
    }

    public ApplicationState getState() {
        return state;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public ServletResponse getResponse() {
        return response;
    }

    @Override
    protected String getParameter(String name) {
        return request.getParameter(name);
    }

}
