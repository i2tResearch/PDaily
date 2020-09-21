package co.icesi.pdaily.business.structure.subsidiary.doctor.domain.model.view;

import java.util.UUID;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.business.structure.subsidiary.doctor.domain.model.Doctor;
import co.icesi.pdaily.business.structure.subsidiary.doctor.domain.model.DoctorId;
import co.icesi.pdaily.business.structure.subsidiary.domain.model.SubsidiaryId;
import co.icesi.pdaily.common.model.Reference;

/**
 * @author andres2508 on 25/11/19
 **/
public final class DoctorReadView {
	public final String id;
	public final String fullName;
	public final String reference;
	public final String subsidiaryId;

	public DoctorReadView(UUID id, String fullName, String reference, UUID subsidiaryId) {
		this( id.toString(), fullName, reference, subsidiaryId.toString() );
	}

	public DoctorReadView(String id, String fullName, String reference,
			String subsidiaryId) {
		this.id = id;
		this.fullName = fullName;
		this.reference = reference;
		this.subsidiaryId = subsidiaryId;
	}

	public static DoctorReadView of(String id, String fullName, String reference, String subsidiaryId) {
		return new DoctorReadView( id, fullName, reference, subsidiaryId );
	}

	public static DoctorReadView of(Doctor rep) {
		final String finalRef = rep.reference() != null ? rep.reference().text() : null;
		return of( rep.id().text(), rep.fullName().text(), finalRef, rep.subsidiaryId().text() );
	}

	public Doctor toDoctor() {
		return Doctor.of(
				DoctorId.ofNotNull( id ),
				SubsidiaryId.of( subsidiaryId ),
				PlainName.of( fullName ),
				Reference.of( reference )
		);
	}
}
