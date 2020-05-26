package co.haruk.sms.events.routine.schedule.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.*;

import co.haruk.sms.business.structure.patient.domain.model.PatientId;
import co.haruk.sms.clinical.base.routines.domain.model.RoutineTypeId;
import co.haruk.sms.common.model.Schedule;
import co.haruk.sms.common.model.tenancy.PdailyTenantEntity;

@Entity
@Table(name = "programming_routine_schedules")
@NamedQuery(name = RoutineSchedule.findByPatientAsReadView, query = "SELECT new co.haruk.sms.events.routine.schedule.domain.model.view.RoutineScheduleReadView( rs.id.id, rs.patientId.id, rt.id.id, rt.label.text, rs.schedule.schedule ) "
		+
		"FROM RoutineSchedule rs " +
		"INNER JOIN RoutineType rt ON rs.type = rt.id " +
		"WHERE rs.tenant = :company " +
		"AND rs.patientId = :patientId")
@NamedQuery(name = RoutineSchedule.findByIdAsReadView, query = "SELECT new co.haruk.sms.events.routine.schedule.domain.model.view.RoutineScheduleReadView( rs.id.id, rs.patientId.id, rt.id.id, rt.label.text, rs.schedule.schedule ) "
		+
		"FROM RoutineSchedule rs " +
		"INNER JOIN RoutineType rt ON rs.type = rt.id " +
		"WHERE rs.tenant = :company " +
		"AND rs.id = :id")
public class RoutineSchedule extends PdailyTenantEntity<RoutineScheduleId> {
	private static final String PREFIX = "RoutineSchedule.";
	public static final String findByPatientAsReadView = PREFIX + "findByPatientAsReadView";
	public static final String findByIdAsReadView = PREFIX + "findByIdAsReadView";

	@EmbeddedId
	private RoutineScheduleId id;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "patient_id"))
	private PatientId patientId;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "type_id"))
	private RoutineTypeId type;
	@Embedded
	private Schedule schedule;

	protected RoutineSchedule() {
	}

	private RoutineSchedule(RoutineScheduleId id, PatientId patientId, RoutineTypeId typeId, Schedule schedule) {
		setId( id );
		setPatientId( patientId );
		setTypeId( typeId );
		setSchedule( schedule );
	}

	public static RoutineSchedule of(RoutineScheduleId id, PatientId patientId, RoutineTypeId typeId, Schedule schedule) {
		return new RoutineSchedule( id, patientId, typeId, schedule );
	}

	public PatientId patientId() {
		return patientId;
	}

	public RoutineTypeId type() {
		return type;
	}

	public Schedule schedule() {
		return schedule;
	}

	private void setSchedule(Schedule schedule) {
		this.schedule = requireNonNull( schedule, "La hora de programacion de la rutina no puede ser nula." );
	}

	private void setTypeId(RoutineTypeId typeId) {
		this.type = requireNonNull( typeId, "Es necesario la rutina que se realizara." );
	}

	private void setPatientId(PatientId patientId) {
		this.patientId = requireNonNull( patientId, "Es necesario el paciente que tiene programado esta rutina." );
	}

	public void updateFrom(RoutineSchedule event) {
		setTypeId( event.type() );
		setSchedule( event.schedule() );
	}

	@Override
	public RoutineScheduleId id() {
		return id;
	}

	@Override
	public void setId(RoutineScheduleId id) {
		this.id = id;
	}
}
