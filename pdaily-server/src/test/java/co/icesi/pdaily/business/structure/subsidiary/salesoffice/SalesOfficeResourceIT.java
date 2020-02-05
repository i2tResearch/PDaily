package co.icesi.pdaily.business.structure.subsidiary.salesoffice;

import static io.restassured.RestAssured.given;
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
import co.icesi.pdaily.business.structure.subsidiary.SubsidiaryTesting;
import co.icesi.pdaily.business.structure.subsidiary.salesoffice.app.SalesOfficeDTO;
import co.icesi.testing.DataSets;
import co.icesi.testing.SMSTest;

/**
 * @author cristhiank on 21/11/19
 **/
@SMSTest
@DisplayName("SalesOffice tests")
class SalesOfficeResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.SALES_OFFICE );
	}

	@Test
	@DisplayName("Finds all")
	void findAll() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/sales-office" )
				.then()
				.statusCode( 200 )
				.body(
						"$.size", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Finds all for subsidiary")
	void findAllForSubsidiary() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/sales-office/for-subsidiary/{0}", SubsidiaryTesting.SUBSIDIARY_ID )
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
				.get( "/business/sales-office/{0}", SalesOfficeTesting.SALES_OFF_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( SalesOfficeTesting.SALES_OFF_ID ),
						"reference", notNullValue(),
						"name", notNullValue(),
						"subsidiaryId", equalToIgnoringCase( SubsidiaryTesting.SUBSIDIARY_ID )
				);
	}

	@Test
	@DisplayName("Creates sales office")
	void saveEntity() {
		var dto = SalesOfficeDTO.of(
				null,
				TestNamesGenerator.generateCode(),
				TestNamesGenerator.generateName(),
				SubsidiaryTesting.SUBSIDIARY_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/sales-office" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"reference", equalToIgnoringCase( dto.reference ),
						"name", equalToIgnoringCase( dto.name ),
						"subsidiaryId", equalToIgnoringCase( dto.subsidiaryId )
				);
	}

	@Test
	@DisplayName("Creates sales office with null reference")
	void saveEntityWithNullReference() {
		var dto = SalesOfficeDTO.of(
				null,
				null,
				TestNamesGenerator.generateName(),
				SubsidiaryTesting.SUBSIDIARY_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/sales-office" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"reference", nullValue(),
						"name", equalToIgnoringCase( dto.name ),
						"subsidiaryId", equalToIgnoringCase( dto.subsidiaryId )
				);
	}

	@Test
	@DisplayName("Updates sales office")
	void updatesEntity() {
		var dto = SalesOfficeDTO.of(
				SalesOfficeTesting.SALES_OFF_TO_UPDATE,
				TestNamesGenerator.generateCode(),
				TestNamesGenerator.generateName(),
				SubsidiaryTesting.SUBSIDIARY_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/sales-office" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( dto.id ),
						"reference", equalToIgnoringCase( dto.reference ),
						"name", equalToIgnoringCase( dto.name ),
						"subsidiaryId", equalToIgnoringCase( dto.subsidiaryId )
				);
	}

	@Test
	@DisplayName("Fails if duplicated reference")
	void failsIfDuplicatedReference() {
		var dto = SalesOfficeDTO.of(
				null,
				"EXISTENT",
				TestNamesGenerator.generateName(),
				SubsidiaryTesting.SUBSIDIARY_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/sales-office" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated reference ignoring case")
	void failsIfDuplicatedReferenceIgnoringCase() {
		var dto = SalesOfficeDTO.of(
				null,
				" existent ",
				TestNamesGenerator.generateName(),
				SubsidiaryTesting.SUBSIDIARY_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/sales-office" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated reference on existent")
	void failsIfDuplicatedReferenceOnExistent() {
		var dto = SalesOfficeDTO.of(
				SalesOfficeTesting.SALES_OFF_TO_UPDATE,
				" existent ",
				TestNamesGenerator.generateName(),
				SubsidiaryTesting.SUBSIDIARY_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/sales-office" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name")
	void failsIfDuplicatedName() {
		var dto = SalesOfficeDTO.of(
				null,
				TestNamesGenerator.generateCode(),
				"EXISTENT",
				SubsidiaryTesting.SUBSIDIARY_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/sales-office" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name ignoring case")
	void failsIfDuplicatedNameIgnoringCase() {
		var dto = SalesOfficeDTO.of(
				null,
				TestNamesGenerator.generateCode(),
				" existent ",
				SubsidiaryTesting.SUBSIDIARY_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/sales-office" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name on existent")
	void failsIfDuplicatedNameOnExistent() {
		var dto = SalesOfficeDTO.of(
				SalesOfficeTesting.SALES_OFF_TO_UPDATE,
				TestNamesGenerator.generateCode(),
				"EXISTENT",
				SubsidiaryTesting.SUBSIDIARY_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/sales-office" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Deletes sales office")
	void deleteEntity() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/business/sales-office/{0}", SalesOfficeTesting.SALES_OFF_TO_DELETE )
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/sales-office/{0}", SalesOfficeTesting.SALES_OFF_TO_DELETE )
				.then()
				.statusCode( 404 );
	}
}
