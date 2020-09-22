package co.icesi.pdaily.business.structure.subsidiary.doctor.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.*;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.business.structure.subsidiary.domain.model.SubsidiaryId;
import co.icesi.pdaily.common.model.Reference;
import co.icesi.pdaily.common.model.tenancy.PdailyTenantEntity;

/**
 * @author andres2508 on 25/11/19
 **/
@Entity
@Table(name = "bs_doctors")
@NamedQuery(name = Doctor.findAllAsRepView, query = "SELECT new co.icesi.pdaily.business.structure.subsidiary.doctor.domain.model.view.DoctorReadView(r.id.id,CONCAT(p.givenName,' ',p.lastName),r.reference.text,r.subsidiaryId.id) FROM Doctor r INNER JOIN User p ON r.id = p.id WHERE r.tenant = :company")
@NamedQuery(name = Doctor.findByIdAsRepView, query = "SELECT new co.icesi.pdaily.business.structure.subsidiary.doctor.domain.model.view.DoctorReadView(r.id.id,CONCAT(p.givenName,' ',p.lastName),r.reference.text,r.subsidiaryId.id) FROM Doctor r INNER JOIN User p ON r.id = p.id WHERE r.id = :id")
@NamedQuery(name = Doctor.findByReference, query = "SELECT r FROM Doctor r WHERE r.tenant = :company AND UPPER(r.reference.text) = UPPER(:reference)")
@NamedQuery(name = Doctor.findBySubsidiaryAsRepView, query = "SELECT new co.icesi.pdaily.business.structure.subsidiary.doctor.domain.model.view.DoctorReadView(r.id.id,CONCAT(p.givenName,' ',p.lastName),r.reference.text,r.subsidiaryId.id) FROM Doctor r INNER JOIN User p ON r.id = p.id WHERE r.tenant = :company AND r.subsidiaryId = :subsidiary")
@NamedQuery(name = Doctor.findBySubsidiary, query = "SELECT r FROM Doctor r WHERE r.tenant = :company AND r.subsidiaryId = :subsidiary")
@NamedQuery(name = Doctor.countBySubsidiary, query = "SELECT COUNT(r.id) FROM Doctor r WHERE r.tenant = :company AND r.subsidiaryId = :subsidiary")
public class Doctor extends PdailyTenantEntity<DoctorId> {
	private static final String PREFIX = "Doctor.";
	public static final String findAllAsRepView = PREFIX + "findAllAsRepView";
	public static final String findByIdAsRepView = PREFIX + "findByIdAsRepView";
	public static final String findByReference = PREFIX + "findByReference";
	public static final String findBySubsidiaryAsRepView = PREFIX + "findBySubsidiaryAsRepView";
	public static final String findBySubsidiary = PREFIX + "findBySubsidiary";
	public static final String countBySubsidiary = PREFIX + "countBySubsidiary";
	@EmbeddedId
	private DoctorId id;
	@Embedded
	private Reference reference;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "subsidiary_id"))
	private SubsidiaryId subsidiaryId;
	@Transient
	private PlainName fullName;

	protected Doctor() {
	}

	private Doctor(DoctorId id, SubsidiaryId subsidiaryId, PlainName fullName) {
		this.id = id;
		setSubsidiaryId( subsidiaryId );
		setFullName( fullName );
	}

	private Doctor(DoctorId id, SubsidiaryId subsidiaryId, PlainName fullName, Reference reference) {
		this.id = id;
		setSubsidiaryId( subsidiaryId );
		setFullName( fullName );
		setReference( reference );
	}

	public static Doctor of(DoctorId id, SubsidiaryId subsidiaryId, PlainName fullName) {
		return new Doctor( id, subsidiaryId, fullName );
	}

	public static Doctor of(DoctorId id, SubsidiaryId subsidiaryId, PlainName fullName, Reference reference) {
		return new Doctor( id, subsidiaryId, fullName, reference );
	}

	@Override
	public DoctorId id() {
		return this.id;
	}

	@Override
	public void setId(DoctorId id) {
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
