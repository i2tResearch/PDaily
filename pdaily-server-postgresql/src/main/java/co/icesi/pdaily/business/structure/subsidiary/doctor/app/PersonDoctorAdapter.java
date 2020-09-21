package co.icesi.pdaily.business.structure.subsidiary.doctor.app;

import static co.haruk.core.domain.model.guards.Guards.check;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.StreamUtils;
import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.business.structure.subsidiary.doctor.domain.model.Doctor;
import co.icesi.pdaily.business.structure.subsidiary.doctor.domain.model.DoctorId;
import co.icesi.pdaily.business.structure.subsidiary.doctor.domain.model.view.DoctorReadView;
import co.icesi.pdaily.business.structure.subsidiary.doctor.infrastructure.persistence.DoctorRepository;
import co.icesi.pdaily.business.structure.subsidiary.domain.model.SubsidiaryId;
import co.icesi.pdaily.common.infrastructure.session.HarukSession;
import co.icesi.pdaily.security.user.app.UserAppService;
import co.icesi.pdaily.security.user.domain.model.UserId;

/**
 * @author andres2508 on 25/11/19
 **/
@Dependent
class UserDoctorAdapter {
	@Inject
	UserAppService userAppService;
	@Inject
	DoctorRepository repository;

	Doctor createRepForUser(UserId userId, SubsidiaryId subsidiaryId) {
		final var exists = existsDoctorForUser( userId );
		check( !exists, "Ya existe un doctor para el usuario" );
		final var user = userAppService.findByIdOrFail( userId.text() );
		final var fullname = PlainName.of( user.fullName() );
		return Doctor.of( DoctorId.of( userId ), subsidiaryId, fullname );
	}

	boolean existsDoctorForUser(UserId userId) {
		return repository.find( DoctorId.of( userId ) ).isPresent();
	}

	List<DoctorReadView> findAvailableUsersForSubsidiary(SubsidiaryId subsidiaryId) {
		final var users = userAppService.findForAccount( HarukSession.currentTenant().text() );
		// Gets sales doctors ids
		final var doctors = repository.findForSubsidiary( subsidiaryId ).stream().map( it -> it.id().text() )
				.collect( Collectors.toList() );
		// Remove users who already is a sales rep
		users.removeIf( it -> doctors.contains( it.id ) );
		return StreamUtils.map(
				users, it -> DoctorReadView.of(
						it.id,
						it.fullName(),
						null,
						subsidiaryId.text()
				)
		);
	}
}
