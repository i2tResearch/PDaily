package co.icesi.pdaily.common.infrastructure.ejb;

import javax.sql.DataSource;

import co.haruk.core.infrastructure.ejb.EJBUtils;
import co.icesi.pdaily.startup.IDataSourceProvider;

/**
 * @author cristhiank on 20/11/19
 **/
public class JakartaEEDataSourceProvider implements IDataSourceProvider {

	@Override
	public DataSource getDataSource() {
		return EJBUtils.lookupOrFail( DataSource.class, "java:/jdbc/SMSDS" );
	}
}
