package co.haruk.sms.events.levodopa.event.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.*;

import co.haruk.sms.business.structure.patient.domain.model.PatientId;
import co.haruk.sms.clinical.base.levodopa.domain.model.LevodopaMedicineId;
import co.haruk.sms.common.model.UTCDateTime;
import co.haruk.sms.common.model.tenancy.PdailyTenantEntity;

@Entity
@Table(name = "event_levodopa_events")
@NamedQuery(name = LevodopaEvent.findByPatient, query = "SELECT e FROM LevodopaEvent e WHERE e.tenant = :company AND e.patientId = :patientId")
public class LevodopaEvent extends PdailyTenantEntity<LevodopaEventId> {
	private static final String PREFIX = "LevodopaEvent.";
	public static final String findByPatient = PREFIX + "findByPatient";

	@EmbeddedId
	private LevodopaEventId id;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "patient_id"))
	private PatientId patientId;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "levodopa_id"))
	private LevodopaMedicineId levodopaId;
	@Embedded
	private UTCDateTime date;

	protected LevodopaEvent() {
	}

	private LevodopaEvent(LevodopaEventId id, PatientId patientId, LevodopaMedicineId levodopaId, UTCDateTime date) {
		setId( id );
		setPatientId( patientId );
		setLevodopaId( levodopaId );
		setDate( date );
	}

	public static LevodopaEvent of(LevodopaEventId id, PatientId patientId, LevodopaMedicineId levodopaId, UTCDateTime date) {
		return new LevodopaEvent( id, patientId, levodopaId, date );
	}

	public PatientId patientId() {
		return patientId;
	}

	private void setPatientId(PatientId patientId) {
		this.patientId = requireNonNull( patientId, "El paciente que genero el evento es obligatorio." );
	}

	public UTCDateTime date() {
		return date;
	}

	private void setDate(UTCDateTime date) {
		this.date = requireNonNull( date, "La fecha en la que se tomo la medicina es obligatoria." );
	}

	public LevodopaMedicineId levodopaId() {
		return levodopaId;
	}

	private void setLevodopaId(LevodopaMedicineId levodopaId) {
		this.levodopaId = requireNonNull( levodopaId, "La levodopa que se ha tomado el paciente es obligatoria." );
	}

	public void updateFrom(LevodopaEvent event) {
		setDate( event.date );
		setLevodopaId( event.levodopaId );
	}

	@Override
	public LevodopaEventId id() {
		return id;
	}

	@Override
	public void setId(LevodopaEventId id) {
		this.id = id;
	}
}
