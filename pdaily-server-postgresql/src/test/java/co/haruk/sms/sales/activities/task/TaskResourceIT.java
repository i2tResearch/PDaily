package co.haruk.sms.sales.activities.task;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
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
import co.haruk.sms.sales.activities.task.app.TaskDTO;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;

@SMSTest
@DisplayName("Activities task tests")
public class TaskResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.ACTIVITIES );
	}

	@Test
	@DisplayName("Finds all")
	void findAll() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/sales/activities/task" )
				.then()
				.statusCode( 200 )
				.body(
						"size()", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Finds by id correctly")
	void findById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/sales/activities/task/{0}", TaskTesting.TASK_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( TaskTesting.TASK_ID ),
						"name", notNullValue()
				);
	}

	@Test
	@DisplayName("Saves a new task")
	void saveTask() {
		final var dto = TaskDTO.of( null, TestNamesGenerator.generateName() );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/sales/activities/task" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"name", equalToIgnoringCase( dto.name )
				);
	}

	@Test
	@DisplayName("Fails if duplicated name")
	void failsToSaveIfDuplicatedName() {
		final var dto = TaskDTO.of( null, "EXISTENT" );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/sales/activities/task" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name ignoring case")
	void failsToSaveIfDuplicatedNameIgnoreCase() {
		final var dto = TaskDTO.of( null, " existent " );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/sales/activities/task" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Updates an task")
	void updatesTask() {
		final var dto = TaskDTO.of( TaskTesting.TASK_TO_UPDATE, TestNamesGenerator.generateName() );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/sales/activities/task" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( TaskTesting.TASK_TO_UPDATE ),
						"name", equalToIgnoringCase( dto.name )
				);
	}

	@Test
	@DisplayName("Fails if duplicated name on existent")
	void failsIfDuplicatedNameOnExistent() {
		final var dto = TaskDTO.of( TaskTesting.TASK_TO_UPDATE, " existent " );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/sales/activities/task" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Deletes an task")
	void deleteById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/sales/activities/task/{0}", TaskTesting.TASK_TO_DELETE )
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/sales/activities/task/{0}", TaskTesting.TASK_TO_DELETE )
				.then()
				.statusCode( 404 );
	}

	@Test
	@DisplayName("Fails if task has activities assigned")
	void failsIfDeleteWithActivities() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/sales/activities/task/{0}", TaskTesting.TASK_FOR_DETAIL )
				.then()
				.statusCode( 400 )
				.body(
						"messages[0]", containsStringIgnoringCase( "tiene actividades asignadas" )
				);
	}
}
