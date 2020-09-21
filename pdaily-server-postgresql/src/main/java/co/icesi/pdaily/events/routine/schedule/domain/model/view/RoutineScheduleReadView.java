package co.icesi.pdaily.events.routine.schedule.domain.model.view;

import java.util.UUID;

public final class RoutineScheduleReadView {
	public String id;
	public String patientId;
	public String typeId;
	public String typeLabel;
	public String schedule;

	protected RoutineScheduleReadView() {
	}

	public RoutineScheduleReadView(
			UUID id,
			UUID patientId,
			UUID typeId,
			String typeLabel,
			String schedule) {
		this.id = id.toString();
		this.patientId = patientId.toString();
		this.typeId = typeId.toString();
		this.typeLabel = typeLabel;
		this.schedule = schedule;
	}
}
