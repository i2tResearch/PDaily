package co.icesi.pdaily.common.infrastructure.jpa;

import javax.persistence.Query;

import co.haruk.core.infrastructure.persistence.jpa.JPAQueryListener;
import co.icesi.pdaily.business.structure.subsidiary.doctor.infrastructure.persistence.DoctorParameterBinder;

/**
 * @author andres2508 on 5/3/20
 **/
public class SMSJPAQueryListener implements JPAQueryListener {

	@Override
	public void beforeQuery(Query query) {
		DoctorParameterBinder.INSTANCE.bind( query );
	}
}
