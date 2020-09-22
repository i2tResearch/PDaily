package co.icesi.pdaily.common.infrastructure.session;

import static co.haruk.core.domain.model.guards.Guards.checkIsNotNull;

import java.util.concurrent.Callable;

import co.icesi.pdaily.business.structure.subsidiary.doctor.domain.model.view.DoctorReadView;
import co.icesi.pdaily.common.model.tenancy.TenantId;
import co.icesi.pdaily.security.user.app.UserDTO;

/**
 * @author andres2508 on 14/11/19
 **/
public final class HarukSession {
	private static final ThreadLocal<TenantId> currentTenant = new ThreadLocal<>();
	private static final ThreadLocal<UserDTO> currentUser = new ThreadLocal<>();
	private static final ThreadLocal<DoctorReadView> currentDoctor = new ThreadLocal<>();

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

	public static DoctorReadView currentDoctor() {
		return checkIsNotNull( currentDoctor.get(), "La solicitud actual no tiene doctor asignado" );
	}

	public static void setCurrentDoctor(DoctorReadView doctor) {
		currentDoctor.set( checkIsNotNull( doctor, "No puede asignar null como doctor actual" ) );
	}

	public static boolean hasDoctor() {
		return currentDoctor.get() != null;
	}

	public static void resetDoctor() {
		currentDoctor.remove();
	}
}
