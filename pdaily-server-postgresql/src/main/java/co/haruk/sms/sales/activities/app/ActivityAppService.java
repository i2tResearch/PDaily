package co.haruk.sms.sales.activities.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import co.haruk.core.StreamUtils;
import co.haruk.core.domain.model.events.EventBus;
import co.haruk.core.infrastructure.jta.JTAUtils;
import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.SalesRepId;
import co.haruk.sms.common.model.UTCDateTime;
import co.haruk.sms.sales.activities.domain.model.Activity;
import co.haruk.sms.sales.activities.domain.model.ActivityCreatedEvent;
import co.haruk.sms.sales.activities.domain.model.ActivityId;
import co.haruk.sms.sales.activities.domain.model.ActivityUpdatedEvent;
import co.haruk.sms.sales.activities.domain.model.view.ActivityReadView;
import co.haruk.sms.sales.activities.domain.model.view.ActivityReadViewBuilder;
import co.haruk.sms.sales.activities.infrastructure.persistence.ActivityRepository;

@ApplicationScoped
public class ActivityAppService {
	@Inject
	ActivityRepository repository;
	@Inject
	ActivityReadViewBuilder builder;
	@Inject
	UserTransaction transaction;

	public List<ActivityReadView> findAllBySalesRep(String salesRepId) {
		return repository.findActivitiesBySalesRepAsReadView( SalesRepId.ofNotNull( salesRepId ) );
	}

	public ActivityReadView findActivityByIdAsReadView(String id) {
		return builder.buildActivity( ActivityId.ofNotNull( id ) );
	}

	public ActivityReadView saveActivity(ActivityRequestDTO dto) {
		var changed = dto.toActivity();
		try {
			Activity saved;
			transaction.begin();
			final var isUpdate = changed.isPersistent();
			UTCDateTime updateDate = null;
			if ( changed.isPersistent() ) {
				Activity original = repository.findOrFail( changed.id() );
				updateDate = original.activityDate();
				original.updateFrom( changed );
				saved = repository.update( original );
			} else {
				changed.setId( ActivityId.generateNew() );
				saved = repository.create( changed );
			}
			transaction.commit();
			final var event = isUpdate ? ActivityUpdatedEvent.of( saved.id(), saved.tenantId(), updateDate )
					: ActivityCreatedEvent.of( saved.id(), saved.tenantId() );
			EventBus.fireAsyncEvent( event );
			return builder.buildActivity( saved.id() );
		} catch (Exception ex) {
			JTAUtils.rollback( transaction );
			throw new IllegalStateException( ex );
		}
	}

	@Transactional
	public void deleteActivity(String id) {
		repository.delete( ActivityId.of( id ) );
	}

	public List<ActivityReadView> findAllForCurrentUser() {
		final var all = repository.findAllIDs();
		return StreamUtils.map( all, it -> builder.buildActivity( it ) );
	}
}
