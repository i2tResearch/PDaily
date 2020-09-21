package co.icesi.pdaily.business.structure.subsidiary.doctor.infrastructure.persistence;

import javax.persistence.Parameter;

import co.haruk.core.infrastructure.persistence.jpa.JPAQueryParameterBinder;
import co.icesi.pdaily.business.structure.subsidiary.doctor.domain.model.DoctorId;
import co.icesi.pdaily.common.infrastructure.session.HarukSession;

/**
 * @author andres2508 on 5/3/20
 **/
public final class DoctorParameterBinder implements JPAQueryParameterBinder {
	public static DoctorParameterBinder INSTANCE = new DoctorParameterBinder();

	private DoctorParameterBinder() {
	}

	@Override
	public boolean canBind(Parameter<?> param) {
		return param.getName().equals( "doctorId" );
	}

	@Override
	public Object parameterValue(Parameter<?> parameter) {
		return HarukSession.hasDoctor() ? DoctorId.ofNotNull( HarukSession.currentDoctor().id ) : null;
	}
}
