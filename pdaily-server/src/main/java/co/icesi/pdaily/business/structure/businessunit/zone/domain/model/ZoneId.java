package co.icesi.pdaily.business.structure.businessunit.zone.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

/**
 * @author cristhiank on 24/11/19
 **/
@Embeddable
public class ZoneId extends Identity {
	protected ZoneId() {
	}

	private ZoneId(String id) {
		super( id );
	}

	private ZoneId(UUID id) {
		super( id );
	}

	public static ZoneId ofNotNull(String id) {
		return new ZoneId( id );
	}

	public static ZoneId of(String id) {
		return id == null ? null : new ZoneId( id );
	}

	public static ZoneId generateNew() {
		return new ZoneId( UUID.randomUUID() );
	}
}
