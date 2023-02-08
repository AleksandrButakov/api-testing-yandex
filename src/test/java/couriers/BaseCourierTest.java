package couriers;

import dto.DtoCourier;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import request.CourierRequests;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

public class BaseCourierTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    protected void loginAndDeleteCourier(DtoCourier courier) {
        Response response = CourierRequests.loginCourier(courier);
        response.then().assertThat()
                .statusCode(SC_OK);
        courier = response
                .as(DtoCourier.class);

        response = CourierRequests.deleteCourier(courier);
        response.then().assertThat()
                .statusCode(SC_OK)
                .and()
                .body("ok", equalTo(true));
    }

}