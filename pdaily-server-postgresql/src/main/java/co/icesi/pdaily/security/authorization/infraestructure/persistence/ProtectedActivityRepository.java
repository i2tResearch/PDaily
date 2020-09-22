package co.icesi.pdaily.security.authorization.infraestructure.persistence;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.icesi.pdaily.security.authorization.domain.model.ProtectedActivity;

/**
 * @author andres2508 on 1/5/20
 **/
@ApplicationScoped
public class ProtectedActivityRepository extends JPARepository<ProtectedActivity> {

}
