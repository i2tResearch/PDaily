package co.icesi.pdaily.business.structure.subsidiary.salesrep.app;

import static co.haruk.core.domain.model.guards.Guards.check;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.StreamUtils;
import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.business.structure.subsidiary.domain.model.SubsidiaryId;
import co.icesi.pdaily.business.structure.subsidiary.salesrep.domain.model.SalesRep;
import co.icesi.pdaily.business.structure.subsidiary.salesrep.domain.model.SalesRepId;
import co.icesi.pdaily.business.structure.subsidiary.salesrep.domain.model.view.SalesRepReadView;
import co.icesi.pdaily.business.structure.subsidiary.salesrep.infrastructure.persistence.SalesRepRepository;
import co.icesi.pdaily.common.infrastructure.session.HarukSession;
import co.icesi.pdaily.subscription.account.user.app.UserAppService;
import co.icesi.pdaily.subscription.account.user.domain.model.UserId;

/**
 * @author cristhiank on 25/11/19
 **/
@Dependent
class UserSalesRepAdapter {
	@Inject
	UserAppService userAppService;
	@Inject
	SalesRepRepository repository;

	SalesRep createRepForUser(UserId userId, SubsidiaryId subsidiaryId) {
		final var exists = existsSalesRepForUser( userId );
		check( !exists, "Ya existe un vendedor para el usuario" );
		final var user = userAppService.findById( userId.text() );
		final var fullname = PlainName.of( user.fullName() );
		return SalesRep.of( SalesRepId.of( userId ), subsidiaryId, fullname );
	}

	boolean existsSalesRepForUser(UserId userId) {
		return repository.find( SalesRepId.of( userId ) ).isPresent();
	}

	List<SalesRepReadView> findAvailableUsersForSubsidiary(SubsidiaryId subsidiaryId) {
		final var users = userAppService.findForAccount( HarukSession.currentTenant().text() );
		// Gets sales reps ids
		final var reps = repository.findForSubsidiary( subsidiaryId ).stream().map( it -> it.id().text() )
				.collect( Collectors.toList() );
		// Remove users who already is a sales rep
		users.removeIf( it -> reps.contains( it.id ) );
		return StreamUtils.map(
				users, it -> SalesRepReadView.of(
						it.id,
						it.fullName(),
						null,
						subsidiaryId.text()
				)
		);
	}
}
