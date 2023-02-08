package request;

import dto.DtoCourier;
import io.restassured.response.Response;

import static data.DataOrders.COURIER_URL;
import static io.restassured.RestAssured.given;

public class CourierRequests {
    public static Response createCourier(DtoCourier dtoCourier) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(dtoCourier)
                .when()
                .post(COURIER_URL);
        return response;
    }

    public static Response loginCourier(DtoCourier dtoCourier) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(dtoCourier)
                .when()
                .post(COURIER_URL + "/login");
        return response;
    }

    public static Response deleteCourier(DtoCourier dtoCourier) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .pathParam("id", dtoCourier.getId())
                .when()
                .delete(COURIER_URL + "/{id}");
        return response;
    }

}