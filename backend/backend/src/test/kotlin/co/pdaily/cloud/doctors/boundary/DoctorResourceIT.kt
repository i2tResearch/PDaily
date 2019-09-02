package co.pdaily.cloud.doctors.boundary

import co.pdaily.cloud.doctors.DoctorTesting
import co.pdaily.cloud.doctors.entity.DoctorDTO
import co.pdaily.cloud.doctors.entity.DoctorRole
import co.pdaily.cloud.patients.entity.PatientDTO
import co.pdaily.cloud.testing.DataSets
import co.pdaily.cloud.testing.PdailyTest
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.DisplayName
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test


@QuarkusTest
@PdailyTest(sqlDataSets = [DataSets.DOCTORS])
@DisplayName("Doctor tests")
internal class DoctorResourceIT {
    @Test
    @DisplayName("Save a doctor correctly")
    fun createDoctor(){
        val doctor = DoctorDTO(null, "Testing Name", "testing@mail.com", DoctorRole.GENERAL)
        given().body(doctor)
                .contentType(ContentType.JSON)
                .`when`().post("/doctors")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(
                        "id", Matchers.`is`(Matchers.notNullValue()),
                        "name", Matchers.equalToIgnoringCase(doctor.name),
                        "mail", Matchers.equalTo(doctor.mail)
                )
    }

    @Test
    @DisplayName("Find all doctors in DB")
    fun findAll(){
        given().`when`().get("/doctors")
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
    @DisplayName("Find doctor by id")
    fun findById(){
        given().`when`()
                .get("/doctors/{0}", DoctorTesting.EXISTENT_ID)
                .then()
                .assertThat()
                .statusCode(200)
                .and().contentType(ContentType.JSON)
                .body("id", Matchers.equalToIgnoringCase(DoctorTesting.EXISTENT_ID))
    }

    @Test
    @DisplayName("Delete doctor by id")
    fun deleteById(){
        given().`when`().delete("/doctors/{0}", DoctorTesting.TO_DELETE_ID)
                .then()
                .log().all()
                .assertThat()
                .statusCode(204)

        given().`when`()
                .get("/doctors/{0}", DoctorTesting.TO_DELETE_ID)
                .then()
                .assertThat()
                .statusCode(500)
    }

    @Test
    @DisplayName("Add patient to doctor")
    fun addPatient(){
        val body = PatientDTO()
        given().body()
                .`when`().post("/doctors/{0}/patient", DoctorTesting.TO_DELETE_ID)
                .then()
                .log().all()
                .assertThat()
                .statusCode(204)

        given().`when`()
                .get("/doctors/{0}", DoctorTesting.TO_DELETE_ID)
                .then()
                .assertThat()
                .statusCode(500)
    }
}
