package co.icesi.pdaily.subscription.license.infrastructure.persistence;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.icesi.pdaily.subscription.license.domain.model.License;

/**
 * @author andres2508 on 15/11/19
 **/
@ApplicationScoped
public class LicenseRepository extends JPARepository<License> {

	public Optional<License> findByName(PlainName name) {
		return findSingleWithNamedQuery(
				License.findByName,
				QueryParameter.with( "name", name.text() ).parameters()
		);
	}
}
