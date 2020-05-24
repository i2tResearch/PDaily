package co.haruk.sms.common.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author cristhiank on 14/11/19
 **/
@DisplayName("HARUK session tests")
class HarukSessionTest {

	@Test
	@DisplayName("Starts multiple threads with different tenants")
	void setCurrentTenant() throws InterruptedException {
		final int threadCount = 10;
		final CountDownLatch latch = new CountDownLatch( threadCount );
		final var list = new CopyOnWriteArrayList<Boolean>();
		for ( int i = 0; i < threadCount; i++ ) {
			new TenantTestThread( "Thread " + i, latch, list ).start();
		}
		latch.await();
		assertEquals( threadCount, list.size() );
		assertTrue( list.stream().allMatch( it -> it ) );
	}
}