package co.haruk.sms.business.structure.customer.infrastructure.persistence;

import javax.persistence.EntityManager;

import co.haruk.sms.business.structure.customer.domain.model.Customer;
import co.haruk.sms.common.infrastructure.session.HarukSession;

/**
 * @author andres2508 on 9/5/20
 **/
class CustomerQueryManager {
	final static String queryForAllAsReadView = "SELECT new co.haruk.sms.business.structure.customer.domain.model.view.CustomerReadView(c.id.id,c.taxID.text,c.mainEmailAddress.email,c.name.name,h.id.id,h.name.name,s.id.id,s.name.name,c.reference.text,c.state, c.type)"
			+ " FROM Customer c INNER JOIN Subsidiary s ON c.subsidiaryId = s.id LEFT JOIN c.businessViews vw" +
			" LEFT JOIN HoldingCompany h ON c.holdingId = h.id" +
			" WHERE c.tenant = :company";

	private CustomerQueryManager() {
	}

	static void registerQueries(EntityManager em) {
		registerFindForSubsidiary( em );
		registerFindSuppliers( em );
		registerFindBuyers( em );
		registerFindForId( em );
		registerFindForSalesRep( em );
	}

	private static String addFilterBySalesRep(String originalQuery) {
		return originalQuery + " AND vw.salesRepId = :salesRepId";
	}

	private static void registerFindForSalesRep(EntityManager em) {
		em.getEntityManagerFactory().addNamedQuery(
				Customer.findAllForSalesRepAsReadView,
				em.createQuery( addFilterBySalesRep( (queryForAllAsReadView) ) )
		);
	}

	private static void registerFindForId(EntityManager em) {
		final var queryForType = queryForAllAsReadView + " AND c.id = :id";
		em.getEntityManagerFactory().addNamedQuery( Customer.findByIdAsReadView, em.createQuery( queryForType ) );
	}

	private static void registerFindBuyers(EntityManager em) {
		final var queryForType = queryForAllAsReadView + " AND c.type = 'B'";
		em.getEntityManagerFactory()
				.addNamedQuery( Customer.findEndBuyersAsReadView, em.createQuery( queryForType ) );
		em.getEntityManagerFactory().addNamedQuery(
				Customer.findEndBuyersForSalesRepAsReadView, em.createQuery( addFilterBySalesRep( queryForType ) )
		);
	}

	private static void registerFindSuppliers(EntityManager em) {
		final var queryForType = queryForAllAsReadView + " AND c.type = 'S'";
		em.getEntityManagerFactory()
				.addNamedQuery( Customer.findSuppliersAsReadView, em.createQuery( queryForType ) );
		em.getEntityManagerFactory().addNamedQuery(
				Customer.findSuppliersForSalesRepAsReadView, em.createQuery( addFilterBySalesRep( queryForType ) )
		);
	}

	private static void registerFindForSubsidiary(EntityManager em) {
		final var queryForSubsidiary = queryForAllAsReadView + " AND c.subsidiaryId = :subsidiary";
		em.getEntityManagerFactory()
				.addNamedQuery( Customer.findForSubsidiaryAsReadView, em.createQuery( queryForSubsidiary ) );
		em.getEntityManagerFactory().addNamedQuery(
				Customer.findForSubsidiaryAndSalesRepAsReadView, em.createQuery( addFilterBySalesRep( queryForSubsidiary ) )
		);
	}

	public static String chooseIfHasSalesRep(String queryWithSalesRep, String queryWithoutSalesRep) {
		return HarukSession.hasSalesRep() ? queryWithSalesRep : queryWithoutSalesRep;
	}

	public static String queryForSubsidiary() {
		return chooseIfHasSalesRep(
				Customer.findForSubsidiaryAndSalesRepAsReadView,
				Customer.findForSubsidiaryAsReadView
		);
	}

	public static String queryForSuppliers() {
		return chooseIfHasSalesRep( Customer.findSuppliersForSalesRepAsReadView, Customer.findSuppliersAsReadView );
	}

	public static String queryForBuyers() {
		return chooseIfHasSalesRep( Customer.findEndBuyersForSalesRepAsReadView, Customer.findEndBuyersAsReadView );
	}
}
