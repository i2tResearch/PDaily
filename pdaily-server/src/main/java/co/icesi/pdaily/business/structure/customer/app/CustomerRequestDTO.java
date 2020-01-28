package co.icesi.pdaily.business.structure.customer.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.business.structure.address.domain.model.AddressRequest;
import co.icesi.pdaily.business.structure.customer.domain.model.Customer;
import co.icesi.pdaily.business.structure.customer.domain.model.CustomerId;
import co.icesi.pdaily.business.structure.customer.domain.model.CustomerType;
import co.icesi.pdaily.business.structure.customer.holding.domain.model.HoldingCompanyId;
import co.icesi.pdaily.business.structure.subsidiary.domain.model.SubsidiaryId;
import co.icesi.pdaily.common.model.EmailAddress;
import co.icesi.pdaily.common.model.Reference;
import co.icesi.pdaily.common.model.TaxID;

/**
 * @author cristhiank on 9/12/19
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
			String type) {
		this.id = id;
		this.taxID = taxID;
		this.holdingId = holdingId;
		this.mainEmailAddress = mainEmailAddress;
		this.name = name;
		this.subsidiaryId = subsidiaryId;
		this.reference = reference;
		this.type = type;
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
		return new CustomerRequestDTO( id, taxID, holdingId, mainEmailAddress, name, subsidiaryId, reference, type );
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
