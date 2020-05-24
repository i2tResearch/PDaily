package co.haruk.sms.business.structure.subsidiary.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.business.structure.subsidiary.domain.model.Subsidiary;
import co.haruk.sms.business.structure.subsidiary.domain.model.SubsidiaryId;
import co.haruk.sms.common.model.Reference;

/**
 * @author cristhiank on 19/11/19
 **/
public final class SubsidiaryDTO {
	public String id;
	public String reference;
	public String name;

	protected SubsidiaryDTO() {
	}

	private SubsidiaryDTO(String id, String reference, String name) {
		this.id = id;
		this.reference = reference;
		this.name = name;
	}

	public static SubsidiaryDTO of(String id, String reference, String name) {
		return new SubsidiaryDTO( id, reference, name );
	}

	public static SubsidiaryDTO of(Subsidiary subsidiary) {
		final String reference = subsidiary.reference() != null ? subsidiary.reference().text() : null;
		return new SubsidiaryDTO(
				subsidiary.id().text(),
				reference,
				subsidiary.name().text()
		);
	}

	public Subsidiary toSubsidiary() {
		return Subsidiary.of(
				SubsidiaryId.of( id ),
				Reference.of( reference ),
				PlainName.of( name )
		);
	}
}
