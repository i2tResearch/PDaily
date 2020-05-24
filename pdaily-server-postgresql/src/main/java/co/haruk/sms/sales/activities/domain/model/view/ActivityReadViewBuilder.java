package co.haruk.sms.sales.activities.domain.model.view;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.UUID;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.sms.business.structure.customer.domain.model.Customer;
import co.haruk.sms.business.structure.customer.infrastructure.persistence.CustomerRepository;
import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.view.SalesRepReadView;
import co.haruk.sms.business.structure.subsidiary.salesrep.infrastructure.persistence.SalesRepRepository;
import co.haruk.sms.sales.activities.domain.model.Activity;
import co.haruk.sms.sales.activities.domain.model.ActivityId;
import co.haruk.sms.sales.activities.infrastructure.persistence.ActivityRepository;
import co.haruk.sms.sales.activities.purpose.domain.model.Purpose;
import co.haruk.sms.sales.activities.purpose.infrastructure.persistence.PurposeRepository;

@Dependent
public class ActivityReadViewBuilder {
	@Inject
	ActivityRepository repository;
	@Inject
	CustomerRepository customerRepository;
	@Inject
	PurposeRepository purposeRepository;
	@Inject
	SalesRepRepository salesRepository;

	public ActivityReadView buildActivity(ActivityId id) {
		requireNonNull( id );
		Activity found = repository.findOrFail( id );
		Purpose purpose = purposeRepository.findOrFail( found.purposeId() );
		Customer buyer = customerRepository.findOrFail( found.buyerId() );
		Customer supplier = found.supplierId() != null ? customerRepository.findOrFail( found.supplierId() ) : null;
		SalesRepReadView salesRep = salesRepository.findOrFailAsRepView( found.salesRepId() );

		UUID supplierId = supplier != null ? supplier.id().uuidValue() : null;
		String supplierName = supplier != null ? supplier.name().text() : null;
		String salesRepName = salesRep.fullName;
		String comment = found.comment() != null ? found.comment().text() : null;

		ActivityReadView readView = new ActivityReadView(
				id.uuidValue(), found.salesRepId().uuidValue(), salesRepName,
				buyer.id().uuidValue(), buyer.name().text(), supplierId, supplierName, purpose.id().uuidValue(),
				purpose.name().text(), found.creationDate().dateAsLong(), found.activityDate().dateAsLong(),
				found.isEffective(), comment,
				found.geolocation()
		);
		readView.campaigns = repository.findCampaignDetailsAsReadView( id );
		readView.tasks = repository.findTaskDetailsAsReadView( id );

		return readView;
	}
}
