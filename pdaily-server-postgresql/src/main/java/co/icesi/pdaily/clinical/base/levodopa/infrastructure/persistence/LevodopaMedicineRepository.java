package co.icesi.pdaily.clinical.base.levodopa.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.EntityNotFoundException;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.icesi.pdaily.clinical.base.levodopa.domain.model.LevodopaMedicine;
import co.icesi.pdaily.clinical.base.levodopa.domain.model.LevodopaMedicineId;
import co.icesi.pdaily.clinical.base.levodopa.domain.model.view.LevodopaMedicineReadView;

@ApplicationScoped
public class LevodopaMedicineRepository extends JPARepository<LevodopaMedicine> {
	public Optional<LevodopaMedicine> findByName(PlainName label) {
		requireNonNull( label );
		return findSingleWithNamedQuery(
				LevodopaMedicine.findByName,
				QueryParameter.with( "name", label.text() ).parameters()
		);
	}

	public List<LevodopaMedicineReadView> findAllAsReadView() {
		return findOtherWithNamedQuery(
				LevodopaMedicineReadView.class,
				LevodopaMedicine.findAllAsReadView
		);
	}

	public LevodopaMedicineReadView findByIdAsReadView(LevodopaMedicineId id) {
		requireNonNull( id );
		return findOtherSingleWithNamedQuery(
				LevodopaMedicineReadView.class,
				LevodopaMedicine.findByIdAsReadView,
				QueryParameter.with( "id", id ).parameters()
		).orElseThrow( EntityNotFoundException::new );
	}
}
