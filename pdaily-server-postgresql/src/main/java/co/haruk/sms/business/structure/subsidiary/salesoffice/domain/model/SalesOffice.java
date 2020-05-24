package co.haruk.sms.business.structure.subsidiary.salesoffice.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.business.structure.subsidiary.domain.model.SubsidiaryId;
import co.haruk.sms.common.model.Reference;
import co.haruk.sms.common.model.tenancy.PdailyTenantEntity;

/**
 * @author andres2508 on 21/11/19
 **/
@Entity
@Table(name = "bs_sales_offices")
@NamedQuery(name = SalesOffice.findByReference, query = "SELECT s FROM SalesOffice s WHERE s.tenant = :company AND UPPER(s.reference.text)=UPPER(:reference)")
@NamedQuery(name = SalesOffice.findByName, query = "SELECT s FROM SalesOffice s WHERE s.tenant = :company AND UPPER(s.name.name)=UPPER(:name)")
@NamedQuery(name = SalesOffice.findBySubsidiary, query = "SELECT s FROM SalesOffice s WHERE s.tenant = :company AND s.subsidiaryId = :subsidiaryId")
@NamedQuery(name = SalesOffice.countBySubsidiary, query = "SELECT COUNT(s.id) FROM SalesOffice s WHERE s.tenant = :company AND s.subsidiaryId = :subsidiaryId")
public class SalesOffice extends PdailyTenantEntity<SalesOfficeId> {
	private static final String PREFIX = "SalesOffice.";
	public static final String findByReference = PREFIX + "findByReference";
	public static final String findByName = PREFIX + "findByName";
	public static final String findBySubsidiary = PREFIX + "findBySubsidiary";
	public static final String countBySubsidiary = PREFIX + "countBySubsidiary";

	@EmbeddedId
	private SalesOfficeId id;
	@Embedded
	private Reference reference;
	@Embedded
	private PlainName name;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "subsidiary_id"))
	private SubsidiaryId subsidiaryId;

	protected SalesOffice() {
	}

	private SalesOffice(
			SalesOfficeId id,
			Reference reference,
			PlainName name,
			SubsidiaryId subsidiaryId) {
		setId( id );
		setReference( reference );
		setName( name );
		setSubsidiaryId( subsidiaryId );
	}

	public static SalesOffice of(
			SalesOfficeId id,
			Reference reference,
			PlainName name,
			SubsidiaryId subsidiaryId) {
		return new SalesOffice( id, reference, name, subsidiaryId );
	}

	@Override
	public SalesOfficeId id() {
		return this.id;
	}

	@Override
	public void setId(SalesOfficeId id) {
		this.id = id;
	}

	public PlainName name() {
		return name;
	}

	private void setName(PlainName name) {
		this.name = requireNonNull( name, "El nombre es requerido" );
	}

	public Reference reference() {
		return reference;
	}

	private void setReference(Reference reference) {
		this.reference = reference;
	}

	public SubsidiaryId subsidiaryId() {
		return subsidiaryId;
	}

	private void setSubsidiaryId(SubsidiaryId subsidiaryId) {
		this.subsidiaryId = requireNonNull( subsidiaryId, "La filial es requerida" );
	}

	public void updateFrom(SalesOffice changed) {
		setName( changed.name );
		setReference( changed.reference );
	}
}
