package co.haruk.sms.business.structure.patient.infrastructure.persistence;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.business.structure.patient.domain.model.Patient;

@ApplicationScoped
public class PatientRepository extends JPARepository<Patient> {
}
