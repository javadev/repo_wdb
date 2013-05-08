package _.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

import _.domain.Order;
import _.domain.Position;

public class FileOrderServiceTest {

	private IOrderService service = new FileOrderService();

	@Test
	public void testGetOrderListForNull() throws Exception {

		Collection<Order> collection = service.retrieveForConsumer(null)
				.getOrders();
		assertNotNull(collection);
		assertTrue(collection.isEmpty());

	}

	@Test
	public void testGetOrderListForAbsentConsumer() throws Exception {
		Collection<Order> collection = service.retrieveForConsumer("nobody")
				.getOrders();
		assertNotNull(collection);
		assertTrue(collection.isEmpty());
	}

	@Test
	public void testGetOrderListForExistingConsumer() throws Exception {
		Collection<Order> collection = service.retrieveForConsumer("me")
				.getOrders();
		assertNotNull(collection);
		assertEquals(2, collection.size());
		Iterator<Order> it = collection.iterator();
		assertOrder("id-1", "me", it.next(),
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
					public double getPrice(int num) {
						switch (num) {
						case 0:
							return .67;
						case 1:
							return 7.0;
						}
						return 0;
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
		assertOrder("id-3", "me", it.next(),
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
					public double getPrice(int num) {
						switch (num) {
						case 0:
							return 120.0;
						}
						return 0;
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
	}

	private void assertOrder(String expectedId, 
			String expectedConsumer, Order order,
			ExpectedPosition expectedPosition) {
		assertEquals(expectedId, order.getId());
		assertEquals(expectedConsumer, order.getConsumer());
		Collection<Position> positions = order.getPositions();
		assertEquals(expectedPosition.getExpectedCount(), positions.size());
		int n = 0;
		for (Position position : positions) {
			assertEquals(expectedPosition.getGoodId(n), position.getGoodId());
			assertEquals(expectedPosition.getQuantity(n),
					position.getQuantity());
			assertEquals(expectedPosition.getPrice(n), position.getPrice(), 0.001 );
			n++;
		}

	}

	private interface ExpectedPosition {
		int getExpectedCount();

		String getGoodId(int num);

		double getPrice(int num);

		int getQuantity(int num);
	}
}
