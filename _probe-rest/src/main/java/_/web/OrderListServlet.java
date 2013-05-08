package _.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import _.domain.OrderList;
import _.service.FileOrderService;
import _.service.IOrderService;

@SuppressWarnings("serial")
@WebServlet("/order-list")
public class OrderListServlet extends HttpServlet {

  // TODO: replace it by @Inject (2013/05/08)
	private static IOrderService service = new FileOrderService();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		try {
			OrderList list = service.retrieveForConsumer(request.getParameter("c"));
			Gson gson = new Gson();
			String str = gson.toJson(list);
			response.setStatus(200);
			response.setContentLength(str.length());
			response.setContentType("text/json");
			response.getWriter().print(str);
			response.flushBuffer();
			
		} catch( Exception ex ) {
			throw new ServletException( ex );
		}

	}

}
