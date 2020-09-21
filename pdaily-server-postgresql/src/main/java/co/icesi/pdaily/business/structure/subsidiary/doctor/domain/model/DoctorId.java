package co.icesi.pdaily.business.structure.subsidiary.doctor.domain.model;

import static co.haruk.core.domain.model.guards.Guards.checkIsNotNull;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

/**
 * @author andres2508 on 25/11/19
 **/
@Embeddable
public class DoctorId extends Identity {
	protected DoctorId() {
	}

	private DoctorId(String id) {
		super( id );
	}

	private DoctorId(UUID id) {
		super( id );
	}

	public static DoctorId of(Identity model) {
		checkIsNotNull( model, "El identificador del rep. de ventas es requerido." );
		return new DoctorId( model.uuidValue() );
	}

	public static DoctorId ofNotNull(String id) {
		return new DoctorId( id );
	}
}
