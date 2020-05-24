package co.haruk.sms.business.structure.subsidiary.salesrep.infrastructure.persistence;

import javax.persistence.Parameter;

import co.haruk.core.infrastructure.persistence.jpa.JPAQueryParameterBinder;
import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.SalesRepId;
import co.haruk.sms.common.infrastructure.session.HarukSession;

/**
 * @author cristhiank on 5/3/20
 **/
public final class SalesRepParameterBinder implements JPAQueryParameterBinder {
	public static SalesRepParameterBinder INSTANCE = new SalesRepParameterBinder();

	private SalesRepParameterBinder() {
	}

	@Override
	public boolean canBind(Parameter<?> param) {
		return param.getName().equals( "salesRepId" );
	}

	@Override
	public Object parameterValue(Parameter<?> parameter) {
		return HarukSession.hasSalesRep() ? SalesRepId.ofNotNull( HarukSession.currentSalesRep().id ) : null;
	}
}
