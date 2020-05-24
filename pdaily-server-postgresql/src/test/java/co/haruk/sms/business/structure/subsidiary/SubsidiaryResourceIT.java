package co.haruk.sms.business.structure.subsidiary;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.TestNamesGenerator;
import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.haruk.sms.business.structure.customer.CustomerTesting;
import co.haruk.sms.business.structure.customer.holding.HoldingTesting;
import co.haruk.sms.business.structure.subsidiary.app.SubsidiaryDTO;
import co.haruk.sms.business.structure.subsidiary.salesoffice.SalesOfficeTesting;
import co.haruk.sms.business.structure.subsidiary.salesrep.SalesRepTesting;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;

/**
 * @author cristhiank on 19/11/19
 **/
@SMSTest
@DisplayName("Subsidiary tests")
class SubsidiaryResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.CUSTOMER );
	}

	@Test
	@DisplayName("Finds all subsidiaries")
	void findAll() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/subsidiary" )
				.then()
				.statusCode( 200 )
				.body(
						"$.size", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Find subsidiary by id")
	void findById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/subsidiary/{0}", SubsidiaryTesting.SUBSIDIARY_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( SubsidiaryTesting.SUBSIDIARY_ID ),
						"name", notNullValue(),
						"reference", notNullValue()
				);
	}

	@Test
	@DisplayName("Deletes subsidiary")
	void deleteEntity() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/business/subsidiary/{0}", SubsidiaryTesting.SUBSIDIARY_TO_DELETE )
				.then()
				.statusCode( 204 );
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/subsidiary/{0}", SubsidiaryTesting.SUBSIDIARY_TO_DELETE )
				.then()
				.statusCode( 404 );
	}

	@Test
	@DisplayName("Fails if subsidiary has offices")
	void failsIfDeleteWithOffices() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/business/subsidiary/{0}", SalesOfficeTesting.SUBSIDIARY_FOR_DELETE_VALIDATION )
				.then()
				.statusCode( 400 )
				.body(
						"messages[0]", containsStringIgnoringCase( "tiene oficinas asignadas" )
				);
	}

	@Test
	@DisplayName("Fails if subsidiary has sales reps")
	void failsIfDeleteWithSalesReps() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/business/subsidiary/{0}", SalesRepTesting.SUBSIDIARY_FOR_DELETE_VALIDATION )
				.then()
				.statusCode( 400 )
				.body(
						"messages[0]", containsStringIgnoringCase( "tiene reps. de ventas asignados" )
				);
	}

	@Test
	@DisplayName("Fails if subsidiary has customers")
	void failsIfDeleteWithCustomers() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/business/subsidiary/{0}", CustomerTesting.SUBSIDIARY_FOR_DELETE_VALIDATION )
				.then()
				.statusCode( 400 )
				.body(
						"messages[0]", containsStringIgnoringCase( "tiene clientes asignados" )
				);
	}

	@Test
	@DisplayName("Fails if subsidiary has holdings")
	void failsIfDeleteWithHoldings() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/business/subsidiary/{0}", HoldingTesting.SUBSIDIARY_FOR_DELETE_VALIDATION )
				.then()
				.statusCode( 400 )
				.body(
						"messages[0]", containsStringIgnoringCase( "tiene grupos empresariales asignados" )
				);
	}

	@Test
	@DisplayName("Creates subsidiary")
	void saveEntity() {
		var dto = SubsidiaryDTO.of( null, TestNamesGenerator.generateCode(), TestNamesGenerator.generateName() );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/subsidiary" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"name", equalToIgnoringCase( dto.name ),
						"reference", equalToIgnoringCase( dto.reference )
				);
	}

	@Test
	@DisplayName("Creates subsidiary with null reference")
	void saveEntityWithNullReference() {
		var dto = SubsidiaryDTO.of( null, null, TestNamesGenerator.generateName() );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/subsidiary" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"name", equalToIgnoringCase( dto.name ),
						"reference", nullValue()
				);
	}

	@Test
	@DisplayName("Fails if duplicated name")
	void failsifDuplicatedName() {
		var dto = SubsidiaryDTO.of( null, TestNamesGenerator.generateCode(), "EXISTENT" );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/subsidiary" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name ignoring case")
	void failsifDuplicatedNameIgnoreCase() {
		var dto = SubsidiaryDTO.of( null, TestNamesGenerator.generateCode(), " existent " );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/subsidiary" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated code")
	void failsifDuplicatedCode() {
		var dto = SubsidiaryDTO.of( null, "EXISTENT", TestNamesGenerator.generateName() );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/subsidiary" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated code ignoring case")
	void failsifDuplicatedCodeIgnoreCase() {
		var dto = SubsidiaryDTO.of( null, " existent ", TestNamesGenerator.generateName() );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/subsidiary" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Updates subsidiary")
	void updatesEntity() {
		var dto = SubsidiaryDTO.of(
				SubsidiaryTesting.SUBSIDIARY_TO_UPDATE,
				TestNamesGenerator.generateCode(),
				TestNamesGenerator.generateName()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/subsidiary" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( SubsidiaryTesting.SUBSIDIARY_TO_UPDATE ),
						"name", equalToIgnoringCase( dto.name ),
						"reference", equalToIgnoringCase( dto.reference )
				);
	}
}