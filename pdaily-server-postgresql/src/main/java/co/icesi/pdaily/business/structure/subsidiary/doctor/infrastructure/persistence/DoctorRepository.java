package co.icesi.pdaily.business.structure.subsidiary.doctor.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.persistence.EntityNotFoundException;
import co.haruk.core.domain.model.persistence.IDataSourceProvider;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.domain.model.query.QueryEngine;
import co.haruk.core.domain.model.query.StdMappers;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.icesi.pdaily.business.structure.subsidiary.doctor.domain.model.Doctor;
import co.icesi.pdaily.business.structure.subsidiary.doctor.domain.model.DoctorId;
import co.icesi.pdaily.business.structure.subsidiary.doctor.domain.model.view.DoctorReadView;
import co.icesi.pdaily.business.structure.subsidiary.domain.model.SubsidiaryId;
import co.icesi.pdaily.common.model.Reference;

/**
 * @author andres2508 on 25/11/19
 **/
@ApplicationScoped
public class DoctorRepository extends JPARepository<Doctor> {

	public List<DoctorReadView> findAllAsRepView() {
		return findOtherWithNamedQuery( DoctorReadView.class, Doctor.findAllAsRepView );
	}

	public DoctorReadView findOrFailAsRepView(DoctorId repId) {
		requireNonNull( repId );
		return findOtherSingleWithNamedQuery(
				DoctorReadView.class,
				Doctor.findByIdAsRepView,
				QueryParameter.with( "id", repId ).parameters()
		).orElseThrow( EntityNotFoundException::new );
	}

	public Optional<Doctor> findByReference(Reference reference) {
		requireNonNull( reference );
		return findSingleWithNamedQuery(
				Doctor.findByReference,
				QueryParameter.with( "reference", reference.text() ).parameters()
		);
	}

	public List<DoctorReadView> findForSubsidiaryAsRepView(SubsidiaryId subsidiaryId) {
		requireNonNull( subsidiaryId );
		return findOtherWithNamedQuery(
				DoctorReadView.class,
				Doctor.findBySubsidiaryAsRepView,
				QueryParameter.with( "subsidiary", subsidiaryId ).parameters()
		);
	}

	public List<Doctor> findForSubsidiary(SubsidiaryId subsidiaryId) {
		requireNonNull( subsidiaryId );
		return findWithNamedQuery(
				Doctor.findBySubsidiary,
				QueryParameter.with( "subsidiary", subsidiaryId ).parameters()
		);
	}

	public boolean existsAnyForSubsidiary(SubsidiaryId subsidiaryId) {
		requireNonNull( subsidiaryId );
		final var count = executeAggregateQuery(
				Doctor.countBySubsidiary,
				QueryParameter.with( "subsidiary", subsidiaryId ).parameters()
		).intValue();
		return count > 0;
	}

	private List<String> executeQuery(String query, Map<String, Object> params) {
		return QueryEngine.executeReadQuery(
				IDataSourceProvider.current().getDataSource(),
				query, StdMappers.STRING(), params
		);
	}
}
