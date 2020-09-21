package co.icesi.pdaily.clinical.base.levodopa.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class LevodopaMedicineId extends Identity {

	protected LevodopaMedicineId() {
	}

	private LevodopaMedicineId(String id) {
		super( id );
	}

	private LevodopaMedicineId(UUID id) {
		super( id );
	}

	public static LevodopaMedicineId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new LevodopaMedicineId( id );
	}

	public static LevodopaMedicineId ofNotNull(String id) {
		return new LevodopaMedicineId( id );
	}

	public static LevodopaMedicineId generateNew() {
		return new LevodopaMedicineId( UUID.randomUUID() );
	}
}
