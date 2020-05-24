package co.haruk.sms.sales.activities.domain.services;

import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import javax.transaction.UserTransaction;

import co.haruk.core.infrastructure.jta.JTAUtils;
import co.haruk.sms.business.structure.customer.app.CustomerAppService;
import co.haruk.sms.business.structure.customer.domain.model.CustomerId;
import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.SalesRepId;
import co.haruk.sms.common.infrastructure.session.HarukSession;
import co.haruk.sms.common.model.UTCDateTime;
import co.haruk.sms.sales.activities.domain.model.Activity;
import co.haruk.sms.sales.activities.domain.model.ActivityCreatedEvent;
import co.haruk.sms.sales.activities.domain.model.ActivityUpdatedEvent;
import co.haruk.sms.sales.activities.infrastructure.persistence.ActivityRepository;
import co.haruk.sms.sales.salesorder.app.SalesOrderAppService;
import co.haruk.sms.sales.salesorder.app.SalesOrderDTO;
import co.haruk.sms.sales.salesorder.domain.model.SalesOrderCreatedEvent;
import co.haruk.sms.sales.salesorder.domain.model.SalesOrderUpdateEvent;

@Dependent
public class ActivityService {
	private static final Logger log = Logger.getLogger( ActivityService.class.getName() );

	@Inject
	CustomerAppService customerService;
	@Inject
	ActivityRepository repository;
	@Inject
	SalesOrderAppService salesOrderService;
	@Inject
	UserTransaction transaction;

	public void listenActivityCreated(@ObservesAsync ActivityCreatedEvent event) {
		HarukSession.runInTenant( event.tenantId(), () -> {
			try {
				transaction.begin();
				final var activity = repository.findOrFail( event.activityId() );
				effectiveActivitiesBy( activity, activity.activityDate() );
				transaction.commit();
			} catch (Exception ex) {
				log.severe(
						String.format(
								"Error verificando la efectividad de las actividades al crear la actividad: %s",
								event.activityId().text()
						)
				);
				log.severe( ex.getMessage() );
				JTAUtils.rollback( transaction );
			}
		} );
	}

	public void listenActivityUpdated(@ObservesAsync ActivityUpdatedEvent event) {
		HarukSession.runInTenant( event.tenantId(), () -> {
			try {
				transaction.begin();
				final var activity = repository.findOrFail( event.activityId() );
				// Uncheck effective activity by update
				effectiveActivitiesBy( activity, event.updateDate() );
				// Check effective activity by update
				effectiveActivitiesBy( activity, activity.activityDate() );
				transaction.commit();
			} catch (Exception ex) {
				log.severe(
						String.format(
								"Error verificando la efectividad de las actividades al actualizar la actividad: %s",
								event.activityId().text()
						)
				);
				log.severe( ex.getMessage() );
				JTAUtils.rollback( transaction );
			}
		} );
	}

	public void listenSalesOrderCreated(@ObservesAsync SalesOrderCreatedEvent event) {
		HarukSession.runInTenant( event.tenantId(), () -> {
			try {
				transaction.begin();
				final SalesOrderDTO salesOrder = salesOrderService.findById( event.salesOrderId().text() );
				effectiveActivitiesBy( salesOrder, UTCDateTime.of( salesOrder.orderDate ) );
				transaction.commit();
			} catch (Exception ex) {
				log.severe(
						String.format(
								"Error verificando la efectividad de las actividades al crear la orden de venta: %s",
								event.salesOrderId().text()
						)
				);
				log.severe( ex.getMessage() );
				JTAUtils.rollback( transaction );
			}
		} );
	}

	public void listenSalesOrderUpdated(@ObservesAsync SalesOrderUpdateEvent event) {
		HarukSession.runInTenant( event.tenantId(), () -> {
			try {
				transaction.begin();
				final SalesOrderDTO salesOrder = salesOrderService.findById( event.salesOrderId().text() );
				// Uncheck effective activities by update
				effectiveActivitiesBy( salesOrder, event.updateDate() );
				// Check effective activities by update
				effectiveActivitiesBy( salesOrder, UTCDateTime.of( salesOrder.orderDate ) );
				transaction.commit();
			} catch (Exception ex) {
				log.severe(
						String.format(
								"Error verificando la efectividad de las actividades al actualizar la orden de venta: %s",
								event.salesOrderId().text()
						)
				);
				log.severe( ex.getMessage() );
				JTAUtils.rollback( transaction );
			}
		} );
	}

	private void effectiveActivitiesFor(CustomerId buyerId, SalesRepId salesRepId,
			UTCDateTime initialDate, UTCDateTime finalDate) {
		List<Activity> activities = repository.findActivitiesByDateRange(
				buyerId, salesRepId,
				initialDate, finalDate
		);

		for ( Activity actual : activities ) {
			final boolean exists = salesOrderService
					.existsOrderForCustomerInRange( actual.buyerId(), actual.salesRepId(), actual.activityDate(), finalDate );
			actual.setEffective( exists );
			repository.update( actual );
		}
	}

	private UTCDateTime calculateUpperDateBy(CustomerId buyerId, SalesRepId salesRepId, UTCDateTime actual) {
		final var businessUnit = customerService.businessUnitFor( buyerId, salesRepId );
		return actual.plusHours( businessUnit.effectiveThreshold().hours() );
	}

	private UTCDateTime calculateLowerDateBy(CustomerId buyerId, SalesRepId salesRepId, UTCDateTime actual) {
		final var businessUnit = customerService.businessUnitFor( buyerId, salesRepId );
		return actual.minusHours( businessUnit.effectiveThreshold().hours() );
	}

	public void effectiveActivitiesBy(Activity activity, UTCDateTime activityDate) {
		final var topDate = calculateUpperDateBy( activity.buyerId(), activity.salesRepId(), activityDate );
		effectiveActivitiesFor( activity.buyerId(), activity.salesRepId(), activityDate, topDate );
	}

	public void effectiveActivitiesBy(SalesOrderDTO salesOrder, UTCDateTime initialDate) {
		final var buyerId = CustomerId.ofNotNull( salesOrder.buyerId );
		final var salesRepId = SalesRepId.ofNotNull( salesOrder.salesRepId );
		final var lowerDate = calculateLowerDateBy( buyerId, salesRepId, initialDate );
		effectiveActivitiesFor( buyerId, salesRepId, lowerDate, initialDate );
	}

}
