package co.haruk.sms.business.structure.customer.holding;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.TestNamesGenerator;
import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.haruk.sms.business.structure.customer.holding.app.HoldingCompanyDTO;
import co.haruk.sms.business.structure.subsidiary.SubsidiaryTesting;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;

/**
 * @author andres2508 on 1/12/19
 **/
@SMSTest
@DisplayName("HoldingCompany tests")
class HoldingCompanyResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.HOLDING_COMPANY );
	}

	@Test
	@DisplayName("Finds for subsidiary")
	void findAll() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/customer/holding/for-subsidiary/{0}", SubsidiaryTesting.SUBSIDIARY_ID )
				.then()
				.statusCode( 200 )
				.body(
						"$.size", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Finds by id")
	void findById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/customer/holding/{0}", HoldingTesting.HOLDING_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( HoldingTesting.HOLDING_ID ),
						"subsidiaryId", equalToIgnoringCase( SubsidiaryTesting.SUBSIDIARY_ID ),
						"name", notNullValue()
				);
	}

	@Test
	@DisplayName("Creates holding company")
	void saveHolding() {
		final var dto = HoldingCompanyDTO.of( null, SubsidiaryTesting.SUBSIDIARY_ID, TestNamesGenerator.generateName() );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/customer/holding" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"subsidiaryId", equalToIgnoringCase( SubsidiaryTesting.SUBSIDIARY_ID ),
						"name", equalToIgnoringCase( dto.name )
				);
	}

	@Test
	@DisplayName("Updates holding company")
	void updateHolding() {
		final var dto = HoldingCompanyDTO
				.of( HoldingTesting.HOLDING_TO_UPDATE, SubsidiaryTesting.SUBSIDIARY_ID, TestNamesGenerator.generateName() );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/customer/holding" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( dto.id ),
						"subsidiaryId", equalToIgnoringCase( SubsidiaryTesting.SUBSIDIARY_ID ),
						"name", equalToIgnoringCase( dto.name )
				);
	}

	@Test
	@DisplayName("Fails if duplicated name on existent")
	void failsIfDuplicatedNameOnExisting() {
		final var dto = HoldingCompanyDTO.of( HoldingTesting.HOLDING_TO_UPDATE, SubsidiaryTesting.SUBSIDIARY_ID, " existent " );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/customer/holding" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name on new")
	void failsIfDuplicatedNameOnNew() {
		final var dto = HoldingCompanyDTO.of( null, SubsidiaryTesting.SUBSIDIARY_ID, "EXISTENT" );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/customer/holding" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name on new ignoring case")
	void failsIfDuplicatedNameOnNewIgnoringCase() {
		final var dto = HoldingCompanyDTO.of( null, SubsidiaryTesting.SUBSIDIARY_ID, " existent " );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/customer/holding" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Deletes holding company")
	void deleteById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/business/customer/holding/{0}", HoldingTesting.HOLDING_TO_DELETE )
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/customer/holding/{0}", HoldingTesting.HOLDING_TO_DELETE )
				.then()
				.statusCode( 404 );
	}
}