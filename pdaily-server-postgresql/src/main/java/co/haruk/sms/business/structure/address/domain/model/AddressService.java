package co.haruk.sms.business.structure.address.domain.model;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import co.haruk.core.StreamUtils;
import co.haruk.core.domain.model.entity.Identity;
import co.haruk.sms.business.structure.address.domain.model.view.AddressReadView;
import co.haruk.sms.business.structure.address.domain.model.view.AddressReadViewBuilder;
import co.haruk.sms.business.structure.address.infrastructure.persistence.AddressRepository;

/**
 * @author cristhiank on 6/12/19
 **/
@ApplicationScoped
public class AddressService {
	@Inject
	AddressRepository repository;
	@Inject
	AddressReadViewBuilder readViewBuilder;
	@Inject
	AddressValidator validator;

	public AddressReadView saveAddress(AddressRequest addressData) {
		final Address changes = addressData.toAddress();
		Address saved = saveAddress( changes );
		return readViewBuilder.buildFor( saved );
	}

	private Address saveAddress(Address changed) {
		Address saved;
		if ( changed.isPersistent() ) {
			final var original = repository.findOrFail( changed.id() );
			original.updateFrom( changed );
			validator.validate( original );
			saved = repository.update( original );
		} else {
			changed.setId( AddressId.generateNew() );
			validator.validate( changed );
			saved = repository.create( changed );
		}
		return saved;
	}

	public AddressReadView createForReferenced(AddressRequest addressData) {
		final Address changes = addressData.toAddress();
		changes.setId( AddressId.generateNew() );
		validator.validate( changes );
		Address saved = repository.create( changes );
		return readViewBuilder.buildFor( saved );
	}

	public void deleteAllForReferenced(Identity referencedId) {
		repository.deleteAllForReferenced( ReferencedId.ofNotNull( referencedId ) );
	}

	public AddressReadView findMainForReferencedOrFail(Identity referencedId) {
		final var found = repository.findMainAddressOrFailFor( ReferencedId.ofNotNull( referencedId ) );
		return readViewBuilder.buildFor( found );
	}

	public Optional<AddressReadView> findMainAddressFor(Identity referencedId) {
		return repository.findMainAddressFor( ReferencedId.ofNotNull( referencedId ) )
				.map( it -> readViewBuilder.buildFor( it ) );
	}

	public void deleteById(AddressId addressId) {
		repository.delete( addressId );
	}

	public AddressReadView findById(AddressId addressId) {
		final var found = repository.findOrFail( addressId );
		return readViewBuilder.buildFor( found );
	}

	public void setAsMainAddress(AddressId addressId) {
		final var found = repository.findOrFail( addressId );
		found.setAsMainAddress( repository );
	}

	public List<AddressReadView> findAllForReferenced(Identity referencedId) {
		final var all = repository.findAllForReferenced( ReferencedId.ofNotNull( referencedId ) );
		return StreamUtils.map( all, it -> readViewBuilder.buildFor( it ) );
	}

	public void saveMainForReferenced(AddressRequest mainAddress) {
		final var saved = saveAddress( mainAddress.toAddress() );
		if ( !saved.isMain() ) {
			saved.setAsMainAddress( repository );
		}
	}

	public void deleteMainIfExistsFor(Identity referencedId) {
		final var found = repository.findMainAddressFor( ReferencedId.ofNotNull( referencedId ) );
		found.ifPresent( address -> deleteById( address.id() ) );
	}
}
