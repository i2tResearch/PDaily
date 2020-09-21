package co.icesi.pdaily.business.structure.subsidiary.doctor.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import co.haruk.core.infrastructure.jta.JTAUtils;
import co.icesi.pdaily.business.structure.businessunit.app.BusinessUnitAppService;
import co.icesi.pdaily.business.structure.subsidiary.doctor.domain.model.Doctor;
import co.icesi.pdaily.business.structure.subsidiary.doctor.domain.model.DoctorId;
import co.icesi.pdaily.business.structure.subsidiary.doctor.domain.model.DoctorValidator;
import co.icesi.pdaily.business.structure.subsidiary.doctor.domain.model.view.DoctorReadView;
import co.icesi.pdaily.business.structure.subsidiary.doctor.domain.model.view.DoctorReadViewBuilder;
import co.icesi.pdaily.business.structure.subsidiary.doctor.infrastructure.persistence.DoctorRepository;
import co.icesi.pdaily.business.structure.subsidiary.domain.model.SubsidiaryId;
import co.icesi.pdaily.common.model.Reference;
import co.icesi.pdaily.security.user.domain.model.UserId;

/**
 * @author andres2508 on 25/11/19
 **/
@ApplicationScoped
public class DoctorAppService {
	@Inject
	UserDoctorAdapter userDoctorAdapter;
	@Inject
	DoctorRepository repository;
	@Inject
	DoctorValidator validator;
	@Inject
	BusinessUnitAppService businessAppService;
	@Inject
	UserTransaction transaction;
	@Inject
	DoctorReadViewBuilder builder;

	@Transactional
	public void saveForUser(String userId, DoctorRequestDTO request) {
		if ( userDoctorAdapter.existsDoctorForUser( UserId.ofNotNull( userId ) ) ) {
			var existent = repository.findOrFail( DoctorId.ofNotNull( userId ) );
			existent.setReference( Reference.of( request.reference ) );
			validator.validate( existent );
			repository.update( existent );
		} else {
			Doctor rep = userDoctorAdapter.createRepForUser(
					UserId.ofNotNull( userId ),
					SubsidiaryId.ofNotNull( request.subsidiaryId )
			);
			rep.setReference( Reference.of( request.reference ) );
			validator.validate( rep );
			repository.create( rep );
		}
	}

	public List<DoctorReadView> findAll() {
		return repository.findAllAsRepView();
	}

	public DoctorReadView findById(String id) {
		return repository.findOrFailAsRepView( DoctorId.ofNotNull( id ) );
	}

	@Transactional
	public void delete(String id) {
		repository.delete( DoctorId.ofNotNull( id ) );
	}

	public List<DoctorReadView> findForSubsidiary(String subsidiaryId) {
		return repository.findForSubsidiaryAsRepView( SubsidiaryId.ofNotNull( subsidiaryId ) );
	}

	public List<DoctorReadView> findAvailableUsersForSubsidiary(String subsidiaryId) {
		return userDoctorAdapter.findAvailableUsersForSubsidiary( SubsidiaryId.ofNotNull( subsidiaryId ) );
	}

	public boolean existsWithId(String id) {
		return repository.find( DoctorId.ofNotNull( id ) ).isPresent();
	}

	public DoctorReadView addBusinessUnit(String doctorId) {
		try {
			transaction.begin();
			final var changed = repository.findOrFail( DoctorId.ofNotNull( doctorId ) );
			repository.update( changed );
			transaction.commit();
			return builder.buildDoctor( changed.id() );
		} catch (Exception ex) {
			JTAUtils.rollback( transaction );
			throw new IllegalStateException( ex );
		}
	}
}
