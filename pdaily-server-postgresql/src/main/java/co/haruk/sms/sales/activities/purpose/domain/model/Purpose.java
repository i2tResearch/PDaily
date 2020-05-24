package co.haruk.sms.sales.activities.purpose.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.common.model.tenancy.PdailyTenantEntity;

@Entity
@Table(name = "sales_activities_purposes")
@NamedQuery(name = Purpose.findByName, query = "SELECT t FROM Purpose t WHERE t.tenant = :company AND UPPER(t.name) = UPPER(:name)")
public class Purpose extends PdailyTenantEntity<PurposeId> {
	private static final String PREFIX = "Purpose.";
	public static final String findByName = PREFIX + "findByName";

	@EmbeddedId
	private PurposeId id;
	@Embedded
	private PlainName name;

	protected Purpose() {
	}

	private Purpose(PurposeId id, PlainName name) {
		setId( id );
		setName( name );
	}

	public static Purpose of(PurposeId id, PlainName name) {
		return new Purpose( id, name );
	}

	@Override
	public PurposeId id() {
		return id;
	}

	@Override
	public void setId(PurposeId id) {
		this.id = id;
	}

	public PlainName name() {
		return this.name;
	}

	public void setName(PlainName name) {
		this.name = requireNonNull( name, "El nombre es requerido" );
	}

}
