package co.haruk.sms.sales.activities.purpose.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.sales.activities.purpose.domain.model.Purpose;
import co.haruk.sms.sales.activities.purpose.domain.model.PurposeId;

public class PurposeDTO {
	public String id;
	public String name;

	protected PurposeDTO() {
	}

	private PurposeDTO(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public static PurposeDTO of(String id, String name) {
		return new PurposeDTO( id, name );
	}

	public static PurposeDTO of(Purpose purpose) {
		return new PurposeDTO( purpose.id().text(), purpose.name().text() );
	}

	public Purpose toPurpose() {
		return Purpose.of(
				PurposeId.of( id ),
				PlainName.of( name )
		);
	}
}
