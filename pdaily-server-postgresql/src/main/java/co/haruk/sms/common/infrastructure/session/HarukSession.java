package co.haruk.sms.common.infrastructure.session;

import static co.haruk.core.domain.model.guards.Guards.checkIsNotNull;

import java.util.concurrent.Callable;

import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.view.SalesRepReadView;
import co.haruk.sms.common.model.tenancy.TenantId;
import co.haruk.sms.security.user.app.UserDTO;

/**
 * @author andres2508 on 14/11/19
 **/
public final class HarukSession {
	private static final ThreadLocal<TenantId> currentTenant = new ThreadLocal<>();
	private static final ThreadLocal<UserDTO> currentUser = new ThreadLocal<>();
	private static final ThreadLocal<SalesRepReadView> currentSalesRep = new ThreadLocal<>();

	private HarukSession() {
	}

	public static boolean hasTenant() {
		return currentTenant.get() != null;
	}

	public static TenantId currentTenant() {
		return checkIsNotNull( currentTenant.get(), "La solicitud actual no tiene tenant asignada" );
	}

	public static void runInTenant(TenantId tenant, Runnable body) {
		try {
			setCurrentTenant( tenant );
			body.run();
		} finally {
			resetTenant();
		}
	}

	public static <T> T executeInTenant(TenantId tenant, Callable<T> body) {
		try {
			setCurrentTenant( tenant );
			return body.call();
		} catch (Exception e) {
			throw new IllegalStateException( e );
		} finally {
			resetTenant();
		}
	}

	public static void setCurrentTenant(TenantId tenant) {
		currentTenant.set( checkIsNotNull( tenant, "No puede asignar null como tenant actual" ) );
	}

	public static void resetTenant() {
		currentTenant.remove();
	}

	public static void setCurrentUser(UserDTO user) {
		currentUser.set( checkIsNotNull( user, "No puede asignar null como usuario actual" ) );
	}

	public static UserDTO currentUser() {
		return checkIsNotNull( currentUser.get(), "La solicitud actual no tiene usuario asignado" );
	}

	public static boolean hasUser() {
		return currentUser.get() != null;
	}

	public static void resetUser() {
		currentUser.remove();
	}

	public static SalesRepReadView currentSalesRep() {
		return checkIsNotNull( currentSalesRep.get(), "La solicitud actual no tiene rep de ventas asignado" );
	}

	public static void setCurrentSalesRep(SalesRepReadView salesRep) {
		currentSalesRep.set( checkIsNotNull( salesRep, "No puede asignar null como rep de ventas actual" ) );
	}

	public static boolean hasSalesRep() {
		return currentSalesRep.get() != null;
	}

	public static void resetSalesRep() {
		currentSalesRep.remove();
	}
}
