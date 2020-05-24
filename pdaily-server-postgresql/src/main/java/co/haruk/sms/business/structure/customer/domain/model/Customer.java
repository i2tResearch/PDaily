package co.haruk.sms.business.structure.customer.domain.model;

import static co.haruk.core.StreamUtils.find;
import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.EntityNotFoundException;
import co.haruk.sms.business.structure.businessunit.domain.model.BusinessUnitId;
import co.haruk.sms.business.structure.customer.holding.domain.model.HoldingCompanyId;
import co.haruk.sms.business.structure.subsidiary.domain.model.SubsidiaryId;
import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.SalesRepId;
import co.haruk.sms.common.infrastructure.jpa.ActiveInactiveStateConverter;
import co.haruk.sms.common.model.ActiveInactiveState;
import co.haruk.sms.common.model.EmailAddress;
import co.haruk.sms.common.model.Reference;
import co.haruk.sms.common.model.TaxID;
import co.haruk.sms.common.model.tenancy.PdailyTenantEntity;

/**
 * @author andres2508 on 9/12/19
 **/
@Entity
@Table(name = "bs_customers")
@NamedQuery(name = Customer.countForSubsidiary, query = "SELECT COUNT(c.id) FROM Customer c WHERE c.tenant = :company AND c.subsidiaryId = :subsidiary")
public class Customer extends PdailyTenantEntity<CustomerId> {
	private static final String PREFIX = "Customer.";

	public static final String findForSubsidiaryAsReadView = PREFIX + "findForSubsidiaryAsReadView";
	public static final String findForSubsidiaryAndSalesRepAsReadView = PREFIX + "findForSubsidiaryAndSalesRepAsReadView";

	public static final String findAllForSalesRepAsReadView = PREFIX + "findAllForSalesRepAsReadView";
	public static final String countForSubsidiary = PREFIX + "countForSubsidiary";
	public static final String findByIdAsReadView = PREFIX + "findByIdAsReadView";

	public static final String findSuppliersAsReadView = PREFIX + "findSuppliersAsReadView";
	public static final String findSuppliersForSalesRepAsReadView = PREFIX + "findSuppliersForSalesRepAsReadView";

	public static final String findEndBuyersAsReadView = PREFIX + "findEndBuyersAsReadView";
	public static final String findEndBuyersForSalesRepAsReadView = PREFIX + "findEndBuyersForSalesRepAsReadView";

	@EmbeddedId
	private CustomerId id;
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<CustomerBusinessView> businessViews;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "holding_id"))
	private HoldingCompanyId holdingId;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "subsidiary_id"))
	private SubsidiaryId subsidiaryId;
	@Embedded
	private Reference reference;
	@Embedded
	private TaxID taxID;
	@Embedded
	@AttributeOverride(name = "email", column = @Column(name = "main_email"))
	private EmailAddress mainEmailAddress;
	@Embedded
	private PlainName name;
	@Convert(converter = ActiveInactiveStateConverter.class)
	private ActiveInactiveState state = ActiveInactiveState.ACTIVE;
	@Convert(converter = CustomerTypeConverter.class)
	private CustomerType type;

	protected Customer() {
	}

	private Customer(
			CustomerId id,
			TaxID taxID,
			HoldingCompanyId holdingId,
			EmailAddress mainEmailAddress,
			PlainName name,
			SubsidiaryId subsidiaryId,
			Reference reference,
			CustomerType type) {
		setId( id );
		setTaxID( taxID );
		setHoldingId( holdingId );
		setMainEmailAddress( mainEmailAddress );
		setName( name );
		setSubsidiaryId( subsidiaryId );
		setReference( reference );
		setCustomerType( type );
		businessViews = new HashSet<>();
	}

	public static Customer of(
			CustomerId id,
			TaxID taxID,
			HoldingCompanyId holdingId,
			EmailAddress mainEmailAddress,
			PlainName name,
			SubsidiaryId subsidiaryId,
			Reference reference,
			CustomerType type) {
		return new Customer( id, taxID, holdingId, mainEmailAddress, name, subsidiaryId, reference, type );
	}

	@Override
	public CustomerId id() {
		return this.id;
	}

	@Override
	public void setId(CustomerId id) {
		this.id = id;
	}

	public TaxID taxID() {
		return taxID;
	}

	private void setTaxID(TaxID taxID) {
		this.taxID = taxID;
	}

	public HoldingCompanyId holdingId() {
		return holdingId;
	}

	private void setHoldingId(HoldingCompanyId holdingId) {
		this.holdingId = holdingId;
	}

	public EmailAddress mainEmailAddress() {
		return mainEmailAddress;
	}

	private void setMainEmailAddress(EmailAddress mainEmailAddress) {
		this.mainEmailAddress = mainEmailAddress;
	}

	public PlainName name() {
		return name;
	}

	private void setName(PlainName name) {
		this.name = requireNonNull( name, "El nombre del cliente es requerido" );
	}

	public boolean isActive() {
		return state == ActiveInactiveState.ACTIVE;
	}

	private void setState(ActiveInactiveState state) {
		this.state = requireNonNull( state, "El estado es requerido" );
	}

	public void updateFrom(Customer changed) {
		setName( changed.name );
		setHoldingId( changed.holdingId );
		setTaxID( changed.taxID );
		setMainEmailAddress( changed.mainEmailAddress );
		setReference( changed.reference );
		setCustomerType( changed.type );
	}

	public void disable() {
		setState( ActiveInactiveState.INACTIVE );
	}

	public void enable() {
		setState( ActiveInactiveState.ACTIVE );
	}

	public SubsidiaryId getSubsidiaryId() {
		return subsidiaryId;
	}

	private void setSubsidiaryId(SubsidiaryId subsidiaryId) {
		this.subsidiaryId = requireNonNull( subsidiaryId, "La filial del cliente es requerida" );
	}

	public Reference reference() {
		return reference;
	}

	private void setReference(Reference reference) {
		this.reference = reference;
	}

	public CustomerType type() {
		return this.type;
	}

	private void setCustomerType(CustomerType type) {
		this.type = requireNonNull( type, "Es necesario indicar el tipo de cliente." );
	}

	public CustomerBusinessView viewForBusiness(BusinessUnitId businessUnitId) {
		return find( businessViews, it -> it.businessUnitId().equals( businessUnitId ) )
				.orElseThrow( () -> new EntityNotFoundException( "No existe la vista de negocios para cliente" ) );
	}

	public boolean hasViewForBusiness(BusinessUnitId businessUnitId) {
		return find( businessViews, it -> it.businessUnitId().equals( businessUnitId ) ).isPresent();
	}

	public CustomerBusinessView createViewForBusiness(BusinessUnitId businessUnitId, SalesRepId salesRepId) {
		final var newView = CustomerBusinessView.of( this, businessUnitId, salesRepId );
		businessViews.add( newView );
		return newView;
	}
}
