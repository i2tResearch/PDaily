package co.haruk.sms.common.infrastructure.jpa;

import co.haruk.core.infrastructure.persistence.jpa.JPARepository;

/**
 * @author cristhiank on 5/3/20
 **/
public class SMSJPARepository<T> extends JPARepository<T> {

	public SMSJPARepository() {
		addQueryListener( new SMSJPAQueryListener() );
	}
}
