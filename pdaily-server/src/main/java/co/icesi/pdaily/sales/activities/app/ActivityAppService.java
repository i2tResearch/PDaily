package co.icesi.pdaily.sales.activities.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import co.haruk.core.infrastructure.jta.JTAUtils;
import co.icesi.pdaily.business.structure.subsidiary.salesrep.domain.model.SalesRepId;
import co.icesi.pdaily.sales.activities.domain.model.Activity;
import co.icesi.pdaily.sales.activities.domain.model.ActivityId;
import co.icesi.pdaily.sales.activities.domain.model.view.ActivityReadView;
import co.icesi.pdaily.sales.activities.domain.model.view.ActivityReadViewBuilder;
import co.icesi.pdaily.sales.activities.infrastructure.persistence.ActivityRepository;

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
			if ( changed.isPersistent() ) {
				Activity original = repository.findOrFail( changed.id() );
				original.updateFrom( changed );
				saved = repository.update( original );
			} else {
				changed.setId( ActivityId.generateNew() );
				saved = repository.create( changed );
			}
			transaction.commit();
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

}
