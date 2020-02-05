package co.icesi.pdaily.business.structure.contact.role;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.TestNamesGenerator;
import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.icesi.pdaily.business.structure.customer.contact.role.app.ContactRoleDTO;
import co.icesi.testing.DataSets;
import co.icesi.testing.SMSTest;

@SMSTest
@DisplayName("Contact role tests")
public class ContactRoleResourceIT implements IDataSetDependent {
	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.CONTACT_ROLE );
	}

	@Test
	@DisplayName("Finds all")
	void findAll() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/customer/contact/role" )
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
				.get( "/business/customer/contact/role/{0}", ContactRoleTesting.ROLE_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( ContactRoleTesting.ROLE_ID ),
						"name", notNullValue()
				);
	}

	@Test
	@DisplayName("Creates contact role")
	void saveEntity() {
		var dto = ContactRoleDTO.of(
				null,
				TestNamesGenerator.generateName()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/customer/contact/role" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"name", equalToIgnoringCase( dto.name )
				);
	}

	@Test
	@DisplayName("Updates contact role")
	void updatesEntity() {
		var dto = ContactRoleDTO.of(
				ContactRoleTesting.ROLE_TO_UPDATE,
				TestNamesGenerator.generateName()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/customer/contact/role" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( dto.id ),
						"name", equalToIgnoringCase( dto.name )
				);
	}

	@Test
	@DisplayName("Fails if duplicated name")
	void failsIfDuplicatedName() {
		var dto = ContactRoleDTO.of(
				null,
				"EXISTENT"
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/customer/contact/role" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name ignoring case")
	void failsIfDuplicatedNameIgnoringCase() {
		var dto = ContactRoleDTO.of(
				null,
				" existent "
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/customer/contact/role" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name on existent")
	void failsIfDuplicatedNameOnExistent() {
		var dto = ContactRoleDTO.of(
				ContactRoleTesting.ROLE_TO_UPDATE,
				"EXISTENT"
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/customer/contact/role" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Deletes contact role")
	void deleteEntity() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/business/customer/contact/role/{0}", ContactRoleTesting.ROLE_TO_DELETE )
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/customer/contact/role/{0}", ContactRoleTesting.ROLE_TO_DELETE )
				.then()
				.statusCode( 404 );
	}
}
