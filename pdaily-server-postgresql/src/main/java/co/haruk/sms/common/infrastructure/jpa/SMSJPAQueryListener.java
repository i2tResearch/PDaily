package co.haruk.sms.common.infrastructure.jpa;

import javax.persistence.Query;

import co.haruk.core.infrastructure.persistence.jpa.JPAQueryListener;
import co.haruk.sms.business.structure.subsidiary.salesrep.infrastructure.persistence.SalesRepParameterBinder;

/**
 * @author cristhiank on 5/3/20
 **/
public class SMSJPAQueryListener implements JPAQueryListener {

	@Override
	public void beforeQuery(Query query) {
		SalesRepParameterBinder.INSTANCE.bind( query );
	}
}
