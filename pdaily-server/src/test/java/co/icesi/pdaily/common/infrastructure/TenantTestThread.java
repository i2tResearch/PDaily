package co.icesi.pdaily.common.infrastructure;

import static org.awaitility.Awaitility.await;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import co.haruk.core.domain.model.entity.Identity;
import co.icesi.pdaily.common.infrastructure.session.HarukSession;
import co.icesi.pdaily.common.model.tenancy.TenantId;

/**
 * @author cristhiank on 14/11/19
 **/
public class TenantTestThread extends Thread {
	private final TenantId thisTenant = TenantId.of( Identity.generateNew() );
	private final CountDownLatch latch;
	private final CopyOnWriteArrayList<Boolean> list;

	public TenantTestThread(
			String name,
			CountDownLatch latch,
			CopyOnWriteArrayList<Boolean> list) {
		super( name );
		this.latch = latch;
		this.list = list;
	}

	@Override
	public void run() {
		HarukSession.setCurrentTenant( thisTenant );
		// Pause X milliseconds to simulate some "real operation"
		list.add( this.thisTenant.equals( HarukSession.currentTenant() ) );
		await().pollInSameThread().pollDelay( 200, TimeUnit.MILLISECONDS ).until( () -> true );
		System.out.println( getName() + " executing with tenant id " + thisTenant );
		latch.countDown();
	}
}
