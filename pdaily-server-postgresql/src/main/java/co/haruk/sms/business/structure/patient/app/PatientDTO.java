package co.haruk.sms.business.structure.patient.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.business.structure.patient.domain.model.Patient;
import co.haruk.sms.business.structure.patient.domain.model.PatientId;

public final class PatientDTO {
	public String id;
	public String fullName;

	protected PatientDTO() {
	}

	private PatientDTO(String id, String fullName) {
		this.id = id;
		this.fullName = fullName;
	}

	public static PatientDTO of(String id, String fullName) {
		return new PatientDTO( id, fullName );
	}

	public static PatientDTO of(Patient patient) {
		return new PatientDTO(
				patient.id().toString(),
				patient.fullName().text()
		);
	}

	public Patient toPatient() {
		return Patient.of(
				PatientId.of( id ),
				PlainName.of( fullName )
		);
	}
}
