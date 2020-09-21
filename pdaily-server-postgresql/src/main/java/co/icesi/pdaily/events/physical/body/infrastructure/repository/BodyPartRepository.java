package co.icesi.pdaily.events.physical.body.infrastructure.repository;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.icesi.pdaily.events.physical.body.domain.model.BodyPart;

@ApplicationScoped
public class BodyPartRepository extends JPARepository<BodyPart> {
	public Optional<BodyPart> findByName(PlainName name) {
		requireNonNull( name );
		return findSingleWithNamedQuery(
				BodyPart.findByName,
				QueryParameter.with( "name", name.text() ).parameters()
		);
	}
}
