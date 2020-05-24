package co.haruk.sms.business.structure.subsidiary.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.common.model.Reference;
import co.haruk.sms.common.model.tenancy.PdailyTenantEntity;

/**
 * @author cristhiank on 19/11/19
 **/
@Entity
@Table(name = "bs_subsidiaries")
@NamedQuery(name = Subsidiary.findByName, query = "SELECT s FROM Subsidiary s WHERE s.tenant = :company AND UPPER(s.name.name) = UPPER(:name)")
@NamedQuery(name = Subsidiary.findByReference, query = "SELECT s FROM Subsidiary s WHERE s.tenant = :company AND UPPER(s.reference.text) = UPPER(:reference)")
public class Subsidiary extends PdailyTenantEntity<SubsidiaryId> {
	private static final String PREFIX = "PREFIX.";
	public static final String findByName = PREFIX + "findByName";
	public static final String findByReference = PREFIX + "findByReference";

	@EmbeddedId
	private SubsidiaryId id;
	@Embedded
	private Reference reference;
	@Embedded
	private PlainName name;

	protected Subsidiary() {
	}

	private Subsidiary(
			SubsidiaryId id,
			Reference reference,
			PlainName name) {
		this.id = id;
		this.reference = reference;
		this.name = name;
	}

	public static Subsidiary of(
			SubsidiaryId id,
			Reference reference,
			PlainName name) {
		return new Subsidiary( id, reference, name );
	}

	@Override
	public SubsidiaryId id() {
		return this.id;
	}

	@Override
	public void setId(SubsidiaryId id) {
		this.id = id;
	}

	public Reference reference() {
		return reference;
	}

	public void setReference(Reference reference) {
		this.reference = reference;
	}

	public PlainName name() {
		return name;
	}

	public void setName(PlainName name) {
		this.name = requireNonNull( name, "El nombre es requerido" );
	}

	public void updateFrom(Subsidiary changed) {
		setReference( changed.reference );
		setName( changed.name );
	}
}
