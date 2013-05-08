package _.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.junit.Test;
import _.domain.Order;
import _.domain.Position;

import static org.junit.Assert.*;

public class FileOrderServiceTest {

	private IOrderService service = new FileOrderService();

	@Test
	public void testGetOrderListForNull() {

		try {
			Collection<Order> collection = service.retrieveForConsumer(null)
					.getOrders();
			assertNotNull(collection);
			assertTrue(collection.isEmpty());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Test
	public void testGetOrderListForAbsentConsumer() {
		try {
			Collection<Order> collection = service
					.retrieveForConsumer("nobody").getOrders();
			assertNotNull(collection);
			assertTrue(collection.isEmpty());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testGetOrderListForExistingConsumer() {
		try {
			Collection<Order> collection = service.retrieveForConsumer("me")
					.getOrders();
			assertNotNull(collection);
			assertEquals(2, collection.size());
			Iterator<Order> it = collection.iterator();
			assertOrder("id-1", getDate("14/04/2013"), "me", it.next(),
					new ExpectedPosition() {

						@Override
						public int getExpectedCount() {
							return 2;
						}

						@Override
						public String getGoodId(int num) {
							switch (num) {
							case 0:
								return "BREAD";
							case 1:
								return "BUTTER";
							}
							return null;
						}

						@Override
						public BigDecimal getPrice(int num) {
							switch (num) {
							case 0:
								return new BigDecimal(4.0);
							case 1:
								return new BigDecimal(7.0);
							}
							return null;
						}

						@Override
						public int getQuantity(int num) {
							switch (num) {
							case 0:
								return 4;
							case 1:
								return 2;
							}
							return 0;
						}

					});
			assertOrder("id-3", getDate("25/04/2013"), "me", it.next(),
					new ExpectedPosition() {

						@Override
						public int getExpectedCount() {
							return 1;
						}

						@Override
						public String getGoodId(int num) {
							switch (num) {
							case 0:
								return "BIKE";
							}
							return null;
						}

						@Override
						public BigDecimal getPrice(int num) {
							switch (num) {
							case 0:
								return new BigDecimal(120.0);
							}
							return null;
						}

						@Override
						public int getQuantity(int num) {
							switch (num) {
							case 0:
								return 4;
							}
							return 0;
						}
					});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void assertOrder(String expectedId, Date expectedDate,
			String expectedConsumer, Order order,
			ExpectedPosition expectedPosition) {
		assertEquals(expectedId, order.getId());
		assertEquals(expectedDate, order.getDate());
		assertEquals(expectedConsumer, order.getConsumer());
		Collection<Position> positions = order.getPositions();
		assertEquals(expectedPosition.getExpectedCount(), positions.size());
		int n = 0;
		for (Position position : positions) {
			assertEquals(expectedPosition.getGoodId(n), position.getGoodId());
			assertEquals(expectedPosition.getQuantity(n),
					position.getQuantity());
			assertEquals(expectedPosition.getPrice(n), position.getPrice());
			n++;
		}

	}

	private interface ExpectedPosition {
		int getExpectedCount();

		String getGoodId(int num);

		BigDecimal getPrice(int num);

		int getQuantity(int num);
	}

	private static final Date getDate(String str) {
		try {
			return new SimpleDateFormat("dd/MM/yyyy").parse(str);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
