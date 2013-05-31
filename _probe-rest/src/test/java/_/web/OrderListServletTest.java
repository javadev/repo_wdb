package _.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OrderListServletTest {
	
	private OrderListServlet servlet= new OrderListServlet();

	@Test
	public void testNoParameter() throws ServletException, IOException {
		HttpServletRequest request = mock( HttpServletRequest.class);
		HttpServletResponse response = mock( HttpServletResponse.class);

		StringWriter out = new StringWriter();
		when( response.getWriter()).thenReturn( new PrintWriter( new PrintWriter(out) ));
				
		servlet.doGet(request, response);
		
		out.close();		
		assertEquals("{\"orders\":[]}", out.toString());
	}
	
	@Test
	public void testAbsentConsumer() throws ServletException, IOException {
		HttpServletRequest request = mock( HttpServletRequest.class);
		when( request.getParameter("c")).thenReturn("nobody");
		
		HttpServletResponse response = mock( HttpServletResponse.class);
		StringWriter out = new StringWriter();
		when( response.getWriter()).thenReturn( new PrintWriter( new PrintWriter(out) ));
				
		servlet.doGet(request, response);
		
		out.close();		
		assertEquals("{\"orders\":[]}", out.toString());
	}	
	
	@Test
	public void testMe() throws ServletException, IOException {
		HttpServletRequest request = mock( HttpServletRequest.class);
		when( request.getParameter("c")).thenReturn("me");
		
		HttpServletResponse response = mock( HttpServletResponse.class);
		StringWriter out = new StringWriter();
		when( response.getWriter()).thenReturn( new PrintWriter( new PrintWriter(out) ));
				
		servlet.doGet(request, response);
		
		out.close();		
		assertEquals("{\"orders\":[" +
				"{\"id\":\"id-1\",\"consumer\":\"me\"," +
				"\"positions\":[" +
				"{\"goodId\":\"BREAD\",\"quantity\":4,\"price\":0.67}," +
				"{\"goodId\":\"BUTTER\",\"quantity\":2,\"price\":7.0}" +
				"]}," +
				"{\"id\":\"id-3\",\"consumer\":\"me\"," +
				"\"positions\":[" +
				"{\"goodId\":\"BIKE\",\"quantity\":4,\"price\":120.0}" +
				"]}" +
				"]}", out.toString());
	}		
	
	@Before
	public void setUp() throws ServletException {
		servlet.init();
	}
	
	@After
	public void tearDown() {
		servlet.destroy();
	}
	
}