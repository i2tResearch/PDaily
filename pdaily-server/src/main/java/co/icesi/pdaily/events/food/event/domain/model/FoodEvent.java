package co.icesi.pdaily.events.food.event.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.*;

import co.icesi.pdaily.business.structure.patient.domain.model.PatientId;
import co.icesi.pdaily.common.model.UTCDateTime;
import co.icesi.pdaily.common.model.tenancy.HarukTenantEntity;

@Entity
@Table(name = "event_food_events")
@NamedQuery(name = FoodEvent.findByPatient, query = "SELECT f FROM FoodEvent f WHERE f.tenant = :company AND f.patientId = :patientId")
public class FoodEvent extends HarukTenantEntity<FoodEventId> {
	private static final String PREFIX = "FoodEvent.";
	public static final String findByPatient = PREFIX + "findByPatientAsReadView";

	@EmbeddedId
	private FoodEventId id;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "patient_id"))
	private PatientId patientId;
	@Embedded
	private UTCDateTime date;

	protected FoodEvent() {
	}

	private FoodEvent(FoodEventId id, PatientId patientId, UTCDateTime date) {
		setId( id );
		setPatientId( patientId );
		setDate( date );
	}

	public static FoodEvent of(FoodEventId id, PatientId patientId, UTCDateTime date) {
		return new FoodEvent( id, patientId, date );
	}

	public void setDate(UTCDateTime date) {
		this.date = requireNonNull( date, "Es necesaria la fecha en la que se genero el evento." );
	}

	private void setPatientId(PatientId patientId) {
		this.patientId = requireNonNull( patientId, "Es necesario el paciente que genero el evento." );
	}

	public PatientId patientId() {
		return patientId;
	}

	public UTCDateTime date() {
		return date;
	}

	@Override
	public FoodEventId id() {
		return id;
	}

	@Override
	public void setId(FoodEventId id) {
		this.id = id;
	}
}
