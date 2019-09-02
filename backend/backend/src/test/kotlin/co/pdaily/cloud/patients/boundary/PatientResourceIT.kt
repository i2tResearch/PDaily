package co.pdaily.cloud.patients.boundary

import co.pdaily.cloud.patients.PatientTesting
import co.pdaily.cloud.patients.entity.PatientDTO
import co.pdaily.cloud.patients.entity.PatientGender
import co.pdaily.cloud.testing.DataSets
import co.pdaily.cloud.testing.PdailyTest
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.DisplayName
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test

@QuarkusTest
@PdailyTest(sqlDataSets = [DataSets.PATIENTS])
@DisplayName("Patient tests")
internal class PatientResourceIT {

    @Test
    @DisplayName("Save a patient correctly")
    fun createDoctor(){
        val patient = PatientDTO(null,"112233", "Testing", 20,PatientGender.FEMENINO)
        given().body(patient)
                .contentType(ContentType.JSON)
                .`when`().post("/patients")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(
                        "id", Matchers.`is`(Matchers.notNullValue()),
                        "name", Matchers.equalToIgnoringCase(patient.name),
                        "document", Matchers.equalTo(patient.document),
                        "age", Matchers.equalTo(patient.age)
                )
    }

    @Test
    @DisplayName("Find all patients in DB")
    fun findAll(){
        given().`when`().get("/patients")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(
                        "$.size", Matchers.greaterThan(0)
                )
    }

    @Test
    @DisplayName("Find patient by id")
    fun findById(){
        given().`when`()
                .get("/patients/{0}", PatientTesting.EXISTENT_ID)
                .then()
                .assertThat()
                .statusCode(200)
                .and().contentType(ContentType.JSON)
                .body("id", Matchers.equalToIgnoringCase(PatientTesting.EXISTENT_ID))
    }

    @Test
    @DisplayName("Delete patient by id")
    fun deleteById(){
        given().`when`().delete("/patients/{0}", PatientTesting.TO_DELETE_ID)
                .then()
                .log().all()
                .assertThat()
                .statusCode(204)

        given().`when`()
                .get("/patients/{0}", PatientTesting.TO_DELETE_ID)
                .then()
                .assertThat()
                .statusCode(500)
    }
}
