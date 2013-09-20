package org.wdbuilder.web;

import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.io.Resources;

@WebServlet("/resource-image")
public class ResourceImageServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

    public static final String IMAGE_FORMAT = "png";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            final String path = request.getParameter("path");
            final URL url = Resources.getResource(path);
            response.setContentType("image/" + IMAGE_FORMAT);
            Resources.copy(url, response.getOutputStream());
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
}
