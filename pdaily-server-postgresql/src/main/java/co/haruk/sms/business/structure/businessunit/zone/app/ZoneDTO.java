package co.haruk.sms.business.structure.businessunit.zone.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.business.structure.businessunit.domain.model.BusinessUnitId;
import co.haruk.sms.business.structure.businessunit.zone.domain.model.Zone;
import co.haruk.sms.business.structure.businessunit.zone.domain.model.ZoneId;
import co.haruk.sms.common.model.Reference;

/**
 * @author cristhiank on 24/11/19
 **/
public final class ZoneDTO {
	public String id;
	public String reference;
	public String name;
	public String businessUnitId;

	protected ZoneDTO() {
	}

	private ZoneDTO(String id, String reference, String name, String businessUnitId) {
		this.id = id;
		this.reference = reference;
		this.name = name;
		this.businessUnitId = businessUnitId;
	}

	public static ZoneDTO of(String id, String reference, String name, String businessUnitId) {
		return new ZoneDTO( id, reference, name, businessUnitId );
	}

	public static ZoneDTO of(Zone zone) {
		final String reference = zone.reference() != null ? zone.reference().text() : null;
		return new ZoneDTO(
				zone.id().text(),
				reference,
				zone.name().text(),
				zone.businessUnitId().text()
		);
	}

	Zone toZone() {
		return Zone.of(
				ZoneId.of( id ),
				Reference.of( reference ),
				PlainName.of( name ),
				BusinessUnitId.ofNotNull( businessUnitId )
		);
	}
}
