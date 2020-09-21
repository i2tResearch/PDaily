package co.icesi.pdaily.business.structure.businessunit.zone;

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
import co.icesi.pdaily.business.structure.businessunit.BusinessUnitTesting;
import co.icesi.pdaily.business.structure.businessunit.zone.app.ZoneDTO;
import co.icesi.pdaily.testing.DataSets;
import co.icesi.pdaily.testing.PDailyTest;

/**
 * @author andres2508 on 24/11/19
 **/
@PDailyTest
@DisplayName("Zone tests")
class ZoneResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.ZONE );
	}

	@Test
	@DisplayName("Finds all")
	void findAll() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/structure/zone" )
				.then()
				.statusCode( 200 )
				.body(
						"$.size", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Finds for business unit")
	void findByBusinessUnit() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/structure/zone/for-business-unit/{0}", BusinessUnitTesting.BUSINESS_UNIT_ID )
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
				.get( "/business/structure/zone/{0}", ZoneTesting.ZONE_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( ZoneTesting.ZONE_ID ),
						"reference", notNullValue(),
						"name", notNullValue(),
						"businessUnitId", equalToIgnoringCase( BusinessUnitTesting.BUSINESS_UNIT_ID )
				);
	}

	@Test
	@DisplayName("Creates new zone")
	void saveEntity() {
		var dto = ZoneDTO.of(
				null,
				TestNamesGenerator.generateCode(),
				TestNamesGenerator.generateName(),
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/zone" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"reference", equalToIgnoringCase( dto.reference ),
						"name", equalToIgnoringCase( dto.name ),
						"businessUnitId", equalToIgnoringCase( dto.businessUnitId )
				);
	}

	@Test
	@DisplayName("Creates new zone with null reference")
	void saveEntityWithNullReference() {
		var dto = ZoneDTO.of(
				null,
				null,
				TestNamesGenerator.generateName(),
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/zone" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"reference", nullValue(),
						"name", equalToIgnoringCase( dto.name ),
						"businessUnitId", equalToIgnoringCase( dto.businessUnitId )
				);
	}

	@Test
	@DisplayName("Fails if duplicated name")
	void failsIfDuplicatedName() {
		var dto = ZoneDTO.of(
				null,
				TestNamesGenerator.generateCode(),
				"EXISTENT",
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/zone" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name ignoring case")
	void failsIfDuplicatedNameIgnoreCase() {
		var dto = ZoneDTO.of(
				null,
				TestNamesGenerator.generateCode(),
				" existent ",
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/zone" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name on existent")
	void failsIfDuplicatedNameOnExistent() {
		var dto = ZoneDTO.of(
				ZoneTesting.ZONE_TO_UPDATE,
				TestNamesGenerator.generateCode(),
				"existent",
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/zone" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated reference")
	void failsIfDuplicatedReference() {
		var dto = ZoneDTO.of(
				null,
				"EXISTENT",
				TestNamesGenerator.generateName(),
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/zone" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated reference ignoring case")
	void failsIfDuplicatedReferenceIgnoreCase() {
		var dto = ZoneDTO.of(
				null,
				" existent ",
				TestNamesGenerator.generateName(),
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/zone" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated reference on existent")
	void failsIfDuplicatedReferenceOnExistent() {
		var dto = ZoneDTO.of(
				ZoneTesting.ZONE_TO_UPDATE,
				"existent",
				TestNamesGenerator.generateName(),
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/zone" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Updates zone")
	void updatesEntity() {
		var dto = ZoneDTO.of(
				ZoneTesting.ZONE_TO_UPDATE,
				TestNamesGenerator.generateCode(),
				TestNamesGenerator.generateName(),
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/zone" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( dto.id ),
						"reference", equalToIgnoringCase( dto.reference ),
						"name", equalToIgnoringCase( dto.name ),
						"businessUnitId", equalToIgnoringCase( dto.businessUnitId )
				);
	}

	@Test
	@DisplayName("Deletes zone")
	void delete() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/business/structure/zone/{0}", ZoneTesting.ZONE_TO_DELETE )
				.then()
				.statusCode( 204 );
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/structure/zone/{0}", ZoneTesting.ZONE_TO_DELETE )
				.then()
				.statusCode( 404 );
	}
}
