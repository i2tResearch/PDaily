package co.haruk.sms.events.levodopa.schedule.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.*;

import co.haruk.sms.business.structure.patient.domain.model.PatientId;
import co.haruk.sms.clinical.base.levodopa.domain.model.LevodopaMedicineId;
import co.haruk.sms.common.model.Schedule;
import co.haruk.sms.common.model.tenancy.PdailyTenantEntity;

@Entity
@Table(name = "programming_levodopa_schedules")
@NamedQuery(name = LevodopaSchedule.findByPatientAsReadView, query = "SELECT new co.haruk.sms.events.levodopa.schedule.domain.model.view.LevodopaScheduleReadView(ls.id.id, ls.patientId.id, ls.medicineId.id, lm.name.name, lm.dose.value, lt.id.id, lt.label.name, ls.schedule.schedule, ls.pillsDose.dose) "
		+
		"FROM LevodopaSchedule ls " +
		"INNER JOIN LevodopaMedicine lm ON lm.id = ls.medicineId " +
		"INNER JOIN LevodopaType lt ON lt.id = lm.typeId " +
		"WHERE ls.patientId = :patientId " +
		"AND ls.tenant = :company")
@NamedQuery(name = LevodopaSchedule.findByIdAsReadView, query = "SELECT new co.haruk.sms.events.levodopa.schedule.domain.model.view.LevodopaScheduleReadView(ls.id.id, ls.patientId.id, ls.medicineId.id, lm.name.name, lm.dose.value, lt.id.id, lt.label.name, ls.schedule.schedule, ls.pillsDose.dose) "
		+
		"FROM LevodopaSchedule ls " +
		"INNER JOIN LevodopaMedicine lm ON lm.id = ls.medicineId " +
		"INNER JOIN LevodopaType lt ON lt.id = lm.typeId " +
		"WHERE ls.id = :id " +
		"AND ls.tenant = :company")
public class LevodopaSchedule extends PdailyTenantEntity<LevodopaScheduleId> {
	private static final String PREFIX = "LevodopaSchedule.";
	public static final String findByPatientAsReadView = PREFIX + "findByPatientAsReadView";
	public static final String findByIdAsReadView = PREFIX + "findByIdAsReadView";

	@EmbeddedId
	private LevodopaScheduleId id;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "patient_id"))
	private PatientId patientId;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "medicine_id"))
	private LevodopaMedicineId medicineId;
	@Embedded
	private Schedule schedule;
	@Embedded
	@AttributeOverride(name = "dose", column = @Column(name = "pills_dose"))
	private PillsDose pillsDose;

	protected LevodopaSchedule() {
	}

	private LevodopaSchedule(LevodopaScheduleId id, PatientId patientId, LevodopaMedicineId medicineId,
			Schedule schedule, PillsDose dose) {
		setId( id );
		setPatientId( patientId );
		setMedicineId( medicineId );
		setSchedule( schedule );
		setPillsDose( dose );
	}

	public PatientId patientId() {
		return patientId;
	}

	public LevodopaMedicineId medicineId() {
		return medicineId;
	}

	public Schedule schedule() {
		return schedule;
	}

	public PillsDose dose() {
		return pillsDose;
	}

	public static LevodopaSchedule of(LevodopaScheduleId id, PatientId patientId, LevodopaMedicineId medicineId,
			Schedule schedule, PillsDose dose) {
		return new LevodopaSchedule( id, patientId, medicineId, schedule, dose );
	}

	private void setPatientId(PatientId patientId) {
		this.patientId = requireNonNull( patientId, "El paciente que se le asigna la toma de medicamento es obligatorio." );
	}

	private void setPillsDose(PillsDose dose) {
		this.pillsDose = requireNonNull( dose, "La dosis de levodopa es obligatoria." );
	}

	private void setSchedule(Schedule schedule) {
		this.schedule = requireNonNull( schedule, "La hora de programacion de la toma de levodopa es obligatoria." );
	}

	private void setMedicineId(LevodopaMedicineId medicineId) {
		this.medicineId = requireNonNull( medicineId, "El tipo de levodopa es obligatorio." );
	}

	public void updateFrom(LevodopaSchedule schedule) {
		setMedicineId( schedule.medicineId );
		setPillsDose( schedule.pillsDose );
		setSchedule( schedule.schedule );
	}

	@Override
	public LevodopaScheduleId id() {
		return id;
	}

	@Override
	public void setId(LevodopaScheduleId id) {
		this.id = id;
	}
}
