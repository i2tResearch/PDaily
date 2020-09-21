package co.icesi.pdaily.business.structure.subsidiary.doctor.domain.model.view;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.icesi.pdaily.business.structure.subsidiary.doctor.domain.model.DoctorId;
import co.icesi.pdaily.business.structure.subsidiary.doctor.infrastructure.persistence.DoctorRepository;

@Dependent
public class DoctorReadViewBuilder {
	@Inject
	DoctorRepository repository;

	protected DoctorReadViewBuilder() {
	}

	public DoctorReadView buildDoctor(DoctorId doctorId) {
		requireNonNull( doctorId );
		final var build = repository.findOrFailAsRepView( doctorId );
		return build;
	}
}
