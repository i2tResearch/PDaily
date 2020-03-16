package co.icesi.pdaily.events.food.schedule.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.*;

import co.icesi.pdaily.business.structure.patient.domain.model.PatientId;
import co.icesi.pdaily.common.model.tenancy.HarukTenantEntity;

@Entity
@Table(name = "programming_food_schedules")
@NamedQuery(name = FoodSchedule.findByPatient, query = "SELECT f FROM FoodSchedule f WHERE f.tenant = :company AND f.patientId = :patient")
public class FoodSchedule extends HarukTenantEntity<FoodScheduleId> {
	private static final String PREFIX = "PhysicalEvent.";
	public static final String findByPatient = PREFIX + "findByPatient";

	@EmbeddedId
	private FoodScheduleId id;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "patient_id"))
	private PatientId patientId;
	@Embedded
	private Schedule schedule;

	protected FoodSchedule() {
	}

	private FoodSchedule(FoodScheduleId id, PatientId patientId, Schedule schedule) {
		setId( id );
		setPatientId( patientId );
		setSchedule( schedule );
	}

	private void setSchedule(Schedule schedule) {
		this.schedule = requireNonNull( schedule, "La hora de programacion de la comida no puede ser nula." );
	}

	private void setPatientId(PatientId patientId) {
		this.patientId = requireNonNull( patientId, "El paciente es requerido en la programacion de la comida." );
	}

	@Override
	public FoodScheduleId id() {
		return id;
	}

	@Override
	public void setId(FoodScheduleId id) {
		this.id = id;
	}

	public void updateFrom(FoodSchedule updated) {
		setSchedule( updated.schedule );
	}
}
