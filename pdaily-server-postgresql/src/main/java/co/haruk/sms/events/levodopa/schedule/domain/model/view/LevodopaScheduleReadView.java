package co.haruk.sms.events.levodopa.schedule.domain.model.view;

import java.util.UUID;

import co.haruk.sms.common.model.MedicineConcentration;

public final class LevodopaScheduleReadView {
	public String id;
	public String patientId;
	public String medicineId;
	public String medicineLabel;
	public String medicineConcentration;
	public String medicineTypeId;
	public String medicineTypeLabel;
	public String schedule;
	public int dose;

	protected LevodopaScheduleReadView() {
	}

	public LevodopaScheduleReadView(
			UUID id,
			UUID patientId,
			UUID medicineId,
			String medicineLabel,
			int medicineConcentration,
			UUID medicineTypeId,
			String medicineTypeLabel,
			String schedule,
			int dose) {
		this.id = id.toString();
		this.patientId = patientId.toString();
		this.medicineId = medicineId.toString();
		this.medicineLabel = medicineLabel;
		this.medicineConcentration = MedicineConcentration.of( medicineConcentration ).valueInFormat();
		this.medicineTypeId = medicineTypeId.toString();
		this.medicineTypeLabel = medicineTypeLabel;
		this.schedule = schedule;
		this.dose = dose;
	}
}
