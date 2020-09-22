package co.icesi.pdaily.common.infrastructure.jpa;

import co.haruk.core.infrastructure.persistence.jpa.JPARepository;

/**
 * @author andres2508 on 5/3/20
 **/
public class SMSJPARepository<T> extends JPARepository<T> {

	public SMSJPARepository() {
		addQueryListener( new SMSJPAQueryListener() );
	}
}
