package co.icesi.pdaily.subscription.license.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.icesi.pdaily.subscription.license.domain.model.License;
import co.icesi.pdaily.subscription.license.domain.model.LicenseId;
import co.icesi.pdaily.subscription.license.domain.model.LicenseValidator;
import co.icesi.pdaily.subscription.license.infrastructure.persistence.LicenseRepository;

/**
 * @author andres2508 on 15/11/19
 **/
@ApplicationScoped
public class LicenseAppService {

	@Inject
	LicenseRepository repository;
	@Inject
	LicenseValidator validator;

	public List<LicenseDTO> findAll() {
		var found = repository.findAll();
		return StreamUtils.map( found, LicenseDTO::of );
	}

	public LicenseDTO findById(String id) {
		var found = repository.findOrFail( LicenseId.ofNotNull( id ) );
		return LicenseDTO.of( found );
	}

	@Transactional
	public void delete(String id) {
		repository.delete( LicenseId.ofNotNull( id ) );
	}

	@Transactional
	public LicenseDTO saveLicense(LicenseDTO dto) {
		License changed = dto.toLicense();
		License saved;
		if ( changed.isPersistent() ) {
			var original = repository.findOrFail( changed.id() );
			original.setName( changed.name() );
			validator.validate( original );
			saved = repository.update( original );
		} else {
			changed.setId( LicenseId.generateNew() );
			validator.validate( changed );
			saved = repository.create( changed );
		}
		return LicenseDTO.of( saved );
	}
}
