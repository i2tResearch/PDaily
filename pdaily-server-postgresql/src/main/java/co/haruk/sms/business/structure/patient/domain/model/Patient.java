package co.haruk.sms.business.structure.patient.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.*;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.common.model.tenancy.PdailyTenantEntity;

@Entity
@Table(name = "bs_patients")
public class Patient extends PdailyTenantEntity<PatientId> {

	@EmbeddedId
	private PatientId id;
	@Embedded
	@AttributeOverride(name = "name", column = @Column(name = "full_name"))
	private PlainName fullName;

	protected Patient() {
	}

	private Patient(PatientId id, PlainName fullName) {
		setId( id );
		setFullName( fullName );
	}

	public static Patient of(PatientId id, PlainName fullName) {
		return new Patient( id, fullName );
	}

	public void setFullName(PlainName fullName) {
		this.fullName = requireNonNull( fullName, "El nombre del paciente es necesario. " );
	}

	public PlainName fullName() {
		return fullName;
	}

	@Override
	public PatientId id() {
		return id;
	}

	@Override
	public void setId(PatientId id) {
		this.id = id;
	}
}
