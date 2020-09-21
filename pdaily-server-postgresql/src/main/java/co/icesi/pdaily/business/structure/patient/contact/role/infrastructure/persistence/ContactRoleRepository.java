package co.icesi.pdaily.business.structure.patient.contact.role.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.icesi.pdaily.business.structure.patient.contact.role.domain.model.ContactRole;

@ApplicationScoped
public class ContactRoleRepository extends JPARepository<ContactRole> {

	public Optional<ContactRole> findByName(PlainName name) {
		requireNonNull( name );
		return findSingleWithNamedQuery(
				ContactRole.findByName,
				QueryParameter.with( "name", name.text() ).parameters()
		);
	}
}
