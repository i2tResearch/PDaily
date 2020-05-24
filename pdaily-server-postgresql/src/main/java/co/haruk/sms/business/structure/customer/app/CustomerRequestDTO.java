package co.haruk.sms.business.structure.customer.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.text.StringOps;
import co.haruk.sms.business.structure.address.domain.model.AddressRequest;
import co.haruk.sms.business.structure.customer.domain.model.Customer;
import co.haruk.sms.business.structure.customer.domain.model.CustomerId;
import co.haruk.sms.business.structure.customer.domain.model.CustomerType;
import co.haruk.sms.business.structure.customer.holding.domain.model.HoldingCompanyId;
import co.haruk.sms.business.structure.subsidiary.domain.model.SubsidiaryId;
import co.haruk.sms.common.infrastructure.session.HarukSession;
import co.haruk.sms.common.model.EmailAddress;
import co.haruk.sms.common.model.Reference;
import co.haruk.sms.common.model.TaxID;

/**
 * @author andres2508 on 9/12/19
 **/
public final class CustomerRequestDTO {
	public String id;
	public String taxID;
	public String holdingId;
	public String subsidiaryId;
	public String mainEmailAddress;
	public String name;
	public String reference;
	public String type;
	public AddressRequest mainAddress;
	public String businessUnit;

	protected CustomerRequestDTO() {
	}

	private CustomerRequestDTO(
			String id,
			String taxID,
			String holdingId,
			String mainEmailAddress,
			String name,
			String subsidiaryId,
			String reference,
			String type,
			String businessUnit) {
		this.id = id;
		this.taxID = taxID;
		this.holdingId = holdingId;
		this.mainEmailAddress = mainEmailAddress;
		this.name = name;
		this.subsidiaryId = subsidiaryId;
		this.reference = reference;
		this.type = type;
		this.businessUnit = businessUnit;
	}

	public static CustomerRequestDTO of(
			String id,
			String taxID,
			String holdingId,
			String mainEmailAddress,
			String name,
			String subsidiaryId,
			String reference,
			String type) {
		return new CustomerRequestDTO( id, taxID, holdingId, mainEmailAddress, name, subsidiaryId, reference, type, null );
	}

	public static CustomerRequestDTO of(
			String id,
			String taxID,
			String holdingId,
			String mainEmailAddress,
			String name,
			String subsidiaryId,
			String reference,
			String type,
			String businessUnit) {
		return new CustomerRequestDTO(
				id, taxID, holdingId, mainEmailAddress, name, subsidiaryId, reference, type, businessUnit
		);
	}

	public boolean hasBusinessView() {
		return StringOps.isNotNullOrEmpty( businessUnit ) && HarukSession.hasSalesRep();
	}

	Customer toCustomer() {
		return Customer.of(
				CustomerId.of( id ),
				TaxID.ofNullable( taxID ),
				HoldingCompanyId.of( holdingId ),
				EmailAddress.ofNullable( mainEmailAddress ),
				PlainName.of( name ),
				SubsidiaryId.ofNotNull( subsidiaryId ),
				Reference.of( reference ),
				CustomerType.of( type )
		);
	}
}
