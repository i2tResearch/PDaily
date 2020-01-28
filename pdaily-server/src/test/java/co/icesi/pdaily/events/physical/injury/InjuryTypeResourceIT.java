package co.icesi.pdaily.events.physical.injury;

import co.haruk.core.testing.TestNamesGenerator;
import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.icesi.testing.DataSets;
import co.icesi.testing.SMSTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalToIgnoringCase;

@SMSTest
@DisplayName("Injury Type tests")
public class InjuryTypeResourceIT implements IDataSetDependent {

    @Override
    public List<IDataSet> dataSets() {
        return List.of( DataSets.BODY_PARTS );
    }

    @Test
    @DisplayName("Finds all")
    void findAll() {
        given().contentType( MediaType.APPLICATION_JSON )
                .get( "/events/physical/body_part" )
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
                .get( "/events/physical/body_part/{0}", BodyPartTesting.BODY_PART_ID )
                .then()
                .statusCode( 200 )
                .body(
                        "id", equalToIgnoringCase( BodyPartTesting.BODY_PART_ID ),
                        "name", notNullValue()
                );
    }

    @Test
    @DisplayName("Saves a new body part")
    void saveBodyPart() {
        final var dto = BodyPartDTO.of( null, TestNamesGenerator.generateName() );
        given().contentType( MediaType.APPLICATION_JSON )
                .body( dto )
                .post( "/events/physical/body_part" )
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
        final var dto = BodyPartDTO.of( null, "EXISTENT" );
        given().contentType( MediaType.APPLICATION_JSON )
                .body( dto )
                .post( "/events/physical/body_part" )
                .then()
                .statusCode( 400 );
    }

    @Test
    @DisplayName("Fails if duplicated name ignoring case")
    void failsToSaveIfDuplicatedNameIgnoreCase() {
        final var dto = BodyPartDTO.of( null, " existent " );
        given().contentType( MediaType.APPLICATION_JSON )
                .body( dto )
                .post( "/events/physical/body_part" )
                .then()
                .statusCode( 400 );
    }

    @Test
    @DisplayName("Updates an body part")
    void updatesBodyPart() {
        final var dto = BodyPartDTO.of( BodyPartTesting.BODY_PART_TO_UPDATE, TestNamesGenerator.generateName() );
        given().contentType( MediaType.APPLICATION_JSON )
                .body( dto )
                .post( "/events/physical/body_part" )
                .then()
                .statusCode( 200 )
                .body(
                        "id", equalToIgnoringCase( BodyPartTesting.BODY_PART_TO_UPDATE ),
                        "name", equalToIgnoringCase( dto.name )
                );
    }

    @Test
    @DisplayName("Fails if duplicated name on existent")
    void failsIfDuplicatedNameOnExistent() {
        final var dto = BodyPartDTO.of( BodyPartTesting.BODY_PART_TO_UPDATE, " existent " );
        given().contentType( MediaType.APPLICATION_JSON )
                .body( dto )
                .post( "/events/physical/body_part" )
                .then()
                .statusCode( 400 );
    }

    @Test
    @DisplayName("Deletes an body part")
    void deleteById() {
        given().contentType( MediaType.APPLICATION_JSON )
                .delete( "/events/physical/body_part/{0}", BodyPartTesting.BODY_PART_TO_DELETE )
                .then()
                .statusCode( 204 );

        given().contentType( MediaType.APPLICATION_JSON )
                .get( "/events/physical/body_part/{0}", BodyPartTesting.BODY_PART_TO_DELETE )
                .then()
                .statusCode( 404 );
    }
}
