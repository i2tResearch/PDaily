package co.icesi.pdaily.business.structure.businessunit.domain.model.view;

import java.util.List;
import java.util.UUID;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.business.structure.businessunit.domain.model.BusinessUnit;
import co.icesi.pdaily.business.structure.businessunit.domain.model.BusinessUnitId;
import co.icesi.pdaily.business.structure.businessunit.domain.model.EffectiveActivityThreshold;
import co.icesi.pdaily.business.structure.subsidiary.doctor.domain.model.view.DoctorReadView;
import co.icesi.pdaily.common.model.Reference;

public final class BusinessUnitReadView {
	public String id;
	public String name;
	public String reference;
	public int effectiveThreshold;
	public List<DoctorReadView> sellers;

	protected BusinessUnitReadView() {
	}

	public BusinessUnitReadView(
			UUID id,
			String name,
			String reference,
			int effectiveActivityThreshold) {
		this.id = id.toString();
		this.name = name;
		this.reference = reference;
		this.effectiveThreshold = effectiveActivityThreshold;
	}

	public BusinessUnit toBusinessUnit() {
		return BusinessUnit.of(
				BusinessUnitId.ofNotNull( id ),
				PlainName.of( name ),
				Reference.of( reference ),
				EffectiveActivityThreshold.of( effectiveThreshold )
		);
	}
}
