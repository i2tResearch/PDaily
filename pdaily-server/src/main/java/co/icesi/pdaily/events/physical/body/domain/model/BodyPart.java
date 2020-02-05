package co.icesi.pdaily.events.physical.body.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.*;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.common.model.tenancy.HarukTenantEntity;

@Entity
@Table(name = "event_physical_body_parts")
@NamedQuery(name = BodyPart.findByName, query = "SELECT b FROM BodyPart b WHERE b.tenant = :company AND UPPER(b.name) = UPPER(:name)")
public class BodyPart extends HarukTenantEntity<BodyPartId> {
	private static final String PREFIX = "BodyPart.";
	public static final String findByName = PREFIX + "findByName";

	@EmbeddedId
	private BodyPartId id;
	@Embedded
	private PlainName name;

	protected BodyPart() {
	}

	private BodyPart(BodyPartId id, PlainName name) {
		setId( id );
		setName( name );
	}

	public static BodyPart of(BodyPartId id, PlainName name) {
		return new BodyPart( id, name );
	}

	@Override
	public BodyPartId id() {
		return id;
	}

	@Override
	public void setId(BodyPartId id) {
		this.id = id;
	}

	public PlainName name() {
		return this.name;
	}

	public void setName(PlainName name) {
		this.name = requireNonNull( name, "El nombre de la parte del cuerpo es requerido" );
	}
}
