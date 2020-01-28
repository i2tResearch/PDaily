package co.icesi.pdaily.subscription.license.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.common.model.HarukEntity;

/**
 * @author cristhiank on 15/11/19
 **/
@Entity
@Table(name = "subs_licenses")
@NamedQuery(name = License.findByName, query = "SELECT c FROM License c WHERE UPPER(c.name.name) = UPPER(:name)")
public class License extends HarukEntity<LicenseId> {
	private static final String PREFIX = "License.";
	public static final String findByName = PREFIX + "findByName";
	@EmbeddedId
	private LicenseId id;
	@Embedded
	private PlainName name;

	protected License() {
	}

	private License(LicenseId id, PlainName name) {
		setId( id );
		setName( name );
	}

	public static License of(LicenseId id, PlainName name) {
		return new License( id, name );
	}

	@Override
	public LicenseId id() {
		return id;
	}

	@Override
	public void setId(LicenseId id) {
		this.id = id;
	}

	public void setName(PlainName name) {
		this.name = requireNonNull( name, "El nombre es requerido" );
	}

	public PlainName name() {
		return name;
	}
}
