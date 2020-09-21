package co.icesi.pdaily.common.infrastructure.quarkus;

import javax.enterprise.inject.spi.CDI;
import javax.sql.DataSource;

import co.haruk.core.domain.model.persistence.IDataSourceProvider;

import io.agroal.api.AgroalDataSource;

/**
 * @author andres2508 on 20/11/19
 **/
public class QuarkusDataSourceProvider implements IDataSourceProvider {

	@Override
	public DataSource getDataSource() {
		return CDI.current().select( AgroalDataSource.class ).get();
	}
}
