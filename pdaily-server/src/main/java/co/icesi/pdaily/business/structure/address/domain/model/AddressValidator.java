package co.icesi.pdaily.business.structure.address.domain.model;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.guards.Guards;
import co.icesi.pdaily.business.structure.address.infrastructure.persistence.AddressRepository;

/**
 * @author cristhiank on 7/12/19
 **/
@Dependent
public class AddressValidator {
	@Inject
	AddressRepository repository;

	public void validate(Address address) {
		if ( address.isMain() ) {
			failIfDuplicatedMainForReferenced( address );
		}
	}

	private void failIfDuplicatedMainForReferenced(Address address) {
		final var found = repository.findMainAddressFor( address.referencedId() );
		if ( found.isPresent() ) {
			final var existent = found.get();
			final var mustFail = !address.isPersistent() || !existent.equals( address );
			Guards.check( !mustFail, "Ya existe una dirección principal para la entidad referenciada" );
		}
	}
}
