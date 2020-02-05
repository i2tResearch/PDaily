package co.icesi.pdaily.business.structure.subsidiary.salesrep.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.icesi.pdaily.business.structure.subsidiary.domain.model.SubsidiaryId;
import co.icesi.pdaily.business.structure.subsidiary.salesrep.domain.model.SalesRep;
import co.icesi.pdaily.business.structure.subsidiary.salesrep.domain.model.SalesRepId;
import co.icesi.pdaily.business.structure.subsidiary.salesrep.domain.model.SalesRepValidator;
import co.icesi.pdaily.business.structure.subsidiary.salesrep.domain.model.view.SalesRepReadView;
import co.icesi.pdaily.business.structure.subsidiary.salesrep.infrastructure.persistence.SalesRepRepository;
import co.icesi.pdaily.common.model.Reference;
import co.icesi.pdaily.subscription.account.user.domain.model.UserId;

/**
 * @author cristhiank on 25/11/19
 **/
@ApplicationScoped
public class SalesRepAppService {
	@Inject
	UserSalesRepAdapter userSalesRepAdapter;
	@Inject
	SalesRepRepository repository;
	@Inject
	SalesRepValidator validator;

	@Transactional
	public void saveForUser(String userId, SalesRepRequestDTO request) {
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
}
