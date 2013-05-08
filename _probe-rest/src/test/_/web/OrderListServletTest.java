package _.web;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

public class OrderListServletTest {
  
	private OrderListServlet servlet;

	@Test
	public void testNoParameter() throws ServletException {
		HttpServletRequest request = mock( HttpServletRequest.class);
		HttpServletResponse response = mock( HttpServletResponse.class);
		
		servlet.doGet(request, response);
	}
	
}
