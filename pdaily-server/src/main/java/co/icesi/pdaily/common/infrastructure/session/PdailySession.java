package co.icesi.pdaily.common.infrastructure.session;

import static co.haruk.core.domain.model.guards.Guards.checkIsNotNull;

import co.icesi.pdaily.common.model.tenancy.TenantId;

/**
 * @author cristhiank on 14/11/19
 **/
public final class PdailySession {
	private static ThreadLocal<TenantId> currentTenant = new ThreadLocal<>();

	private PdailySession() {
	}

	public static boolean hasTenant() {
		return currentTenant.get() != null;
	}

	public static TenantId currentTenant() {
		return checkIsNotNull( currentTenant.get(), "La solicitud actual no tiene tenant asignada" );
	}

	public static void setCurrentTenant(TenantId tenant) {
		currentTenant.set( checkIsNotNull( tenant, "No puede asignar null como tenant actual" ) );
	}

	public static void resetTenant() {
		currentTenant.remove();
	}
}
