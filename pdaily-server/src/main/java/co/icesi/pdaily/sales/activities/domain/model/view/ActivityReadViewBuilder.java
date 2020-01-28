package co.icesi.pdaily.sales.activities.domain.model.view;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.icesi.pdaily.business.structure.customer.domain.model.Customer;
import co.icesi.pdaily.business.structure.customer.infrastructure.persistence.CustomerRepository;
import co.icesi.pdaily.business.structure.subsidiary.salesrep.domain.model.view.SalesRepReadView;
import co.icesi.pdaily.business.structure.subsidiary.salesrep.infrastructure.persistence.SalesRepRepository;
import co.icesi.pdaily.sales.activities.domain.model.Activity;
import co.icesi.pdaily.sales.activities.domain.model.ActivityId;
import co.icesi.pdaily.sales.activities.infrastructure.persistence.ActivityRepository;
import co.icesi.pdaily.sales.activities.purpose.domain.model.Purpose;
import co.icesi.pdaily.sales.activities.purpose.infrastructure.persistence.PurposeRepository;

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
		List<CampaignDetailReadView> campaings = repository.findCampaignDetailsAsReadView( id );
		List<TaskDetailReadView> tasks = repository.findTaskDetailsAsReadView( id );
		Purpose purpose = purposeRepository.findOrFail( found.purposeId() );
		Customer buyer = customerRepository.findOrFail( found.buyerId() );
		Customer supplier = found.supplierId() != null ? customerRepository.findOrFail( found.supplierId() ) : null;
		SalesRepReadView salesRep = salesRepository.findOrFailAsRepView( found.salesRepId() );

		UUID supplierId = supplier != null ? supplier.id().uuidValue() : null;
		String supplierName = supplier != null ? supplier.name().text() : null;
		String salesRepName = salesRep.fullName != null ? salesRep.fullName : null;
		String comment = found.comment() != null ? found.comment().text() : null;

		ActivityReadView readView = new ActivityReadView(
				id.uuidValue(), found.salesRepId().uuidValue(), salesRepName,
				buyer.id().uuidValue(), buyer.name().text(), supplierId, supplierName, purpose.id().uuidValue(),
				purpose.name().text(), found.creationDate().dateAsLong(), found.activityDate().dateAsLong(), comment,
				found.geolocation()
		);
		readView.campaigns = campaings;
		readView.tasks = tasks;

		return readView;
	}
}
