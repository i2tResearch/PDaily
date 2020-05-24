package co.haruk.sms.business.structure.subsidiary.salesrep.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import co.haruk.core.infrastructure.jta.JTAUtils;
import co.haruk.sms.business.structure.businessunit.app.BusinessUnitAppService;
import co.haruk.sms.business.structure.subsidiary.domain.model.SubsidiaryId;
import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.SalesRep;
import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.SalesRepId;
import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.SalesRepValidator;
import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.view.SalesRepReadView;
import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.view.SalesRepReadViewBuilder;
import co.haruk.sms.business.structure.subsidiary.salesrep.infrastructure.persistence.SalesRepRepository;
import co.haruk.sms.common.model.Reference;
import co.haruk.sms.security.user.domain.model.UserId;

/**
 * @author andres2508 on 25/11/19
 **/
@ApplicationScoped
public class SalesRepAppService {
	@Inject
	UserSalesRepAdapter userSalesRepAdapter;
	@Inject
	SalesRepRepository repository;
	@Inject
	SalesRepValidator validator;
	@Inject
	BusinessUnitAppService businessAppService;
	@Inject
	UserTransaction transaction;
	@Inject
	SalesRepReadViewBuilder builder;

	@Transactional
	public void saveForUser(String userId, co.haruk.sms.business.structure.subsidiary.salesrep.app.SalesRepRequestDTO request) {
		if ( userSalesRepAdapter.existsSalesRepForUser( UserId.ofNotNull( userId ) ) ) {
			var existent = repository.findOrFail( SalesRepId.ofNotNull( userId ) );
			existent.setReference( Reference.of( request.reference ) );
			validator.validate( existent );
			repository.update( existent );
		} else {
			SalesRep rep = userSalesRepAdapter.createRepForUser(
					UserId.ofNotNull( userId ),
					SubsidiaryId.ofNotNull( request.subsidiaryId )
			);
			rep.setReference( Reference.of( request.reference ) );
			validator.validate( rep );
			repository.create( rep );
		}
	}

	public List<SalesRepReadView> findAll() {
		return repository.findAllAsRepView();
	}

	public SalesRepReadView findById(String id) {
		return repository.findOrFailAsRepView( SalesRepId.ofNotNull( id ) );
	}

	@Transactional
	public void delete(String id) {
		repository.delete( SalesRepId.ofNotNull( id ) );
	}

	public List<SalesRepReadView> findForSubsidiary(String subsidiaryId) {
		return repository.findForSubsidiaryAsRepView( SubsidiaryId.ofNotNull( subsidiaryId ) );
	}

	public List<SalesRepReadView> findAvailableUsersForSubsidiary(String subsidiaryId) {
		return userSalesRepAdapter.findAvailableUsersForSubsidiary( SubsidiaryId.ofNotNull( subsidiaryId ) );
	}

	public boolean existsWithId(String id) {
		return repository.find( SalesRepId.ofNotNull( id ) ).isPresent();
	}

	public SalesRepReadView addBusinessUnit(String businessId, String salesRepId) {
		try {
			transaction.begin();
			final var businessFound = businessAppService.findOrFail( businessId ).toBusinessUnit();
			final var changed = repository.findOrFail( SalesRepId.ofNotNull( salesRepId ) );
			changed.addBusinessUnit( businessFound );
			repository.update( changed );
			transaction.commit();
			return builder.buildSalesRep( changed.id() );
		} catch (Exception ex) {
			JTAUtils.rollback( transaction );
			throw new IllegalStateException( ex );
		}
	}
}
