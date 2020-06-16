package co.haruk.sms.analytics.resume;

import static io.restassured.RestAssured.given;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.haruk.sms.business.structure.patient.PatientTesting;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;

@SMSTest
@DisplayName("Activity dashboard tests")
public class PatientDashboardResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.RESUME_DASHBOARD );
	}

	@Test
	@DisplayName("Patient resume data comprobe")
	void resume() {
		given().get(
				"reports/patient/{0}/resume/{1}/{2}", PatientTesting.PATIENT_ID,
				1590987600000L, 1591592399000L
		)
				.then()
				.statusCode( 200 )
				.log().body();
	}
}
