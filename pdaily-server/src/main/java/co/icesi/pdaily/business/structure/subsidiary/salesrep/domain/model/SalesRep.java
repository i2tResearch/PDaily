package co.icesi.pdaily.business.structure.subsidiary.salesrep.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.business.structure.subsidiary.domain.model.SubsidiaryId;
import co.icesi.pdaily.common.model.Reference;
import co.icesi.pdaily.common.model.tenancy.PdailyTenantEntity;

/**
 * @author cristhiank on 25/11/19
 **/
@Entity
@Table(name = "bs_sales_reps")
@NamedQuery(name = SalesRep.findAllAsRepView, query = "SELECT new co.icesi.pdaily.business.structure.subsidiary.salesrep.domain.model.view.SalesRepReadView(r.id.id,CONCAT(p.givenName,' ',p.lastName),r.reference.text,r.subsidiaryId.id) FROM SalesRep r INNER JOIN User p ON r.id = p.id WHERE r.tenant = :company")
@NamedQuery(name = SalesRep.findByIdAsRepView, query = "SELECT new co.icesi.pdaily.business.structure.subsidiary.salesrep.domain.model.view.SalesRepReadView(r.id.id,CONCAT(p.givenName,' ',p.lastName),r.reference.text,r.subsidiaryId.id) FROM SalesRep r INNER JOIN User p ON r.id = p.id WHERE r.tenant = :company AND r.id = :id")
@NamedQuery(name = SalesRep.findByReference, query = "SELECT r FROM SalesRep r WHERE r.tenant = :company AND UPPER(r.reference.text) = UPPER(:reference)")
@NamedQuery(name = SalesRep.findBySubsidiaryAsRepView, query = "SELECT new co.icesi.pdaily.business.structure.subsidiary.salesrep.domain.model.view.SalesRepReadView(r.id.id,CONCAT(p.givenName,' ',p.lastName),r.reference.text,r.subsidiaryId.id) FROM SalesRep r INNER JOIN User p ON r.id = p.id WHERE r.tenant = :company AND r.subsidiaryId = :subsidiary")
@NamedQuery(name = SalesRep.findBySubsidiary, query = "SELECT r FROM SalesRep r WHERE r.tenant = :company AND r.subsidiaryId = :subsidiary")
@NamedQuery(name = SalesRep.countBySubsidiary, query = "SELECT COUNT(r.id) FROM SalesRep r WHERE r.tenant = :company AND r.subsidiaryId = :subsidiary")
public class SalesRep extends PdailyTenantEntity<SalesRepId> {
	private static final String PREFIX = "SalesRep.";
	public static final String findAllAsRepView = PREFIX + "findAllAsRepView";
	public static final String findByIdAsRepView = PREFIX + "findByIdAsRepView";
	public static final String findByReference = PREFIX + "findByReference";
	public static final String findBySubsidiaryAsRepView = PREFIX + "findBySubsidiaryAsRepView";
	public static final String findBySubsidiary = PREFIX + "findBySubsidiary";
	public static final String countBySubsidiary = PREFIX + "countBySubsidiary";
	@EmbeddedId
	private SalesRepId id;
	@Embedded
	private Reference reference;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "subsidiary_id"))
	private SubsidiaryId subsidiaryId;
	@Transient
	private PlainName fullName;

	protected SalesRep() {
	}

	private SalesRep(SalesRepId id, SubsidiaryId subsidiaryId, PlainName fullName) {
		this.id = id;
		setSubsidiaryId( subsidiaryId );
		setFullName( fullName );
	}

	public static SalesRep of(SalesRepId id, SubsidiaryId subsidiaryId, PlainName fullName) {
		return new SalesRep( id, subsidiaryId, fullName );
	}

	@Override
	public SalesRepId id() {
		return this.id;
	}

	@Override
	public void setId(SalesRepId id) {
		throw new UnsupportedOperationException( "El id del rep. de ventas se asigna desde una usera unicamente" );
	}

	public Reference reference() {
		return reference;
	}

	public void setReference(Reference reference) {
		this.reference = reference;
	}

	public PlainName fullName() {
		return fullName;
	}

	private void setFullName(PlainName fullName) {
		this.fullName = requireNonNull( fullName, "El nombre del rep. de ventas es requerido" );
	}

	public SubsidiaryId subsidiaryId() {
		return subsidiaryId;
	}

	private void setSubsidiaryId(SubsidiaryId subsidiaryId) {
		this.subsidiaryId = requireNonNull( subsidiaryId, "La filial del rep. de ventas es requerida" );
	}
}
