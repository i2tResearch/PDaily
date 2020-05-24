package co.haruk.sms.business.structure.address.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.persistence.EntityNotFoundException;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.business.structure.address.domain.model.Address;
import co.haruk.sms.business.structure.address.domain.model.ReferencedId;

/**
 * @author cristhiank on 6/12/19
 **/
@ApplicationScoped
public class AddressRepository extends JPARepository<Address> {

	public Optional<Address> findMainAddressFor(ReferencedId referencedId) {
		requireNonNull( referencedId );
		return findSingleWithNamedQuery(
				Address.findMainByReferenced,
				QueryParameter.with( "referenced", referencedId ).parameters()
		);
	}

	public Address findMainAddressOrFailFor(ReferencedId referencedId) {
		requireNonNull( referencedId );
		return findSingleWithNamedQuery(
				Address.findMainByReferenced,
				QueryParameter.with( "referenced", referencedId ).parameters()
		).orElseThrow( EntityNotFoundException::new );
	}

	public void markAllAsSecondaryForReferenced(ReferencedId referencedId) {
		requireNonNull( referencedId );
		executeUpdateNamedQuery(
				Address.markAllAsSecondary,
				QueryParameter.with( "referenced", referencedId ).parameters()
		);
	}

	public void deleteAllForReferenced(ReferencedId referencedId) {
		requireNonNull( referencedId );
		executeUpdateNamedQuery(
				Address.deleteAllForReferenced,
				QueryParameter.with( "referenced", referencedId ).parameters()
		);
	}

	public List<Address> findAllForReferenced(ReferencedId referencedId) {
		requireNonNull( referencedId );
		return findWithNamedQuery(
				Address.findAllByReferenced,
				QueryParameter.with( "referenced", referencedId ).parameters()
		);
	}
}
