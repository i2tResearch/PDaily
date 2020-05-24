package co.haruk.sms.events.physical.app;

import java.util.List;

import co.haruk.sms.business.structure.patient.domain.model.PatientId;
import co.haruk.sms.common.model.UTCDateTime;
import co.haruk.sms.events.physical.domain.model.Intensity;
import co.haruk.sms.events.physical.domain.model.PhysicalEvent;
import co.haruk.sms.events.physical.domain.model.PhysicalEventId;
import co.haruk.sms.events.physical.injury.type.domain.model.InjuryTypeId;

public final class PhysicalEventDTO {
	public String id;
	public String patientId;
	public int intensity;
	public String injuryTypeId;
	public Long initialDate;
	public Long finalDate;
	public List<BodyPartDetailDTO> bodyDetails;

	protected PhysicalEventDTO() {
	}

	private PhysicalEventDTO(String id, String patientId, int intensity, String injuryTypeId, Long initialDate,
			Long finalDate, List<BodyPartDetailDTO> bodyDetails) {
		this.id = id;
		this.patientId = patientId;
		this.intensity = intensity;
		this.injuryTypeId = injuryTypeId;
		this.initialDate = initialDate;
		this.finalDate = finalDate;
		this.bodyDetails = bodyDetails;
	}

	public static PhysicalEventDTO of(String id, String patientId, int intensity, String injuryTypeId, Long initialDate,
			Long finalDate, List<BodyPartDetailDTO> bodyDetails) {
		return new PhysicalEventDTO( id, patientId, intensity, injuryTypeId, initialDate, finalDate, bodyDetails );
	}

	public static PhysicalEventDTO of(String id, String patientId, int intensity, String injuryTypeId, Long initialDate,
			Long finalDate) {
		return new PhysicalEventDTO( id, patientId, intensity, injuryTypeId, initialDate, finalDate, null );
	}

	public PhysicalEvent toPhysicalEvent() {
		PhysicalEvent result = PhysicalEvent.of(
				PhysicalEventId.of( id ),
				PatientId.ofNotNull( patientId ),
				Intensity.of( intensity ),
				InjuryTypeId.ofNotNull( injuryTypeId ),
				UTCDateTime.of( initialDate ),
				UTCDateTime.of( finalDate )
		);
		addDetailsToEvent( result );
		return result;
	}

	private void addDetailsToEvent(PhysicalEvent event) {
		if ( bodyDetails != null ) {
			for ( BodyPartDetailDTO detail : bodyDetails ) {
				event.addBodyPartDetail( detail.toBodyPartDetail( event ) );
			}
		}
	}
}
