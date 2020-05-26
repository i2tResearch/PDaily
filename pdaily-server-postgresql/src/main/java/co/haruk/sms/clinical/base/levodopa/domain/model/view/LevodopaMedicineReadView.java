package co.haruk.sms.clinical.base.levodopa.domain.model.view;

import java.util.UUID;

import co.haruk.sms.common.model.MedicineConcentration;

public class LevodopaMedicineReadView {
	public String id;
	public String name;
	public String typeId;
	public String typeLabel;
	public String dose;

	protected LevodopaMedicineReadView() {
	}

	public LevodopaMedicineReadView(
			UUID id,
			String name,
			UUID typeId,
			String typeLabel,
			int dose) {
		this.id = id.toString();
		this.name = name;
		this.typeId = typeId.toString();
		this.typeLabel = typeLabel;
		this.dose = MedicineConcentration.of( dose ).valueInFormat();
	}
}
