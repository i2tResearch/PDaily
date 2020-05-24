package co.haruk.sms.sales.activities.infrastructure.persistence;

import javax.persistence.EntityManager;

import co.haruk.sms.common.infrastructure.session.HarukSession;
import co.haruk.sms.sales.activities.domain.model.Activity;

/**
 * @author andres2508 on 10/5/20
 **/
class ActivityQueryManager {
	static final String idQuery = "SELECT a.id FROM Activity a WHERE a.tenant = :tenant";

	static void registerQueries(EntityManager em) {
		addFindActivitiesIdQuery( em );
	}

	private static void addFindActivitiesIdQuery(EntityManager em) {
		final var suffix = " ORDER BY a.activityDate DESC";
		final var salesRepFilter = " AND a.salesRepId = :salesRepId";
		final var normalQuery = idQuery + suffix;
		final var salesRepFilterQuery = idQuery + salesRepFilter + suffix;
		em.getEntityManagerFactory().addNamedQuery( Activity.findAllActivitiesIds, em.createQuery( normalQuery ) );
		em.getEntityManagerFactory()
				.addNamedQuery( Activity.findAllActivitiesIdsForSalesRep, em.createQuery( salesRepFilterQuery ) );
	}

	static String queryForActivitiesIDs() {
		return HarukSession.hasSalesRep() ? Activity.findAllActivitiesIdsForSalesRep : Activity.findAllActivitiesIds;
	}
}
