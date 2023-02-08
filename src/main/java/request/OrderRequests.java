package request;

import dto.DtoCourier;
import dto.DtoOrder;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderRequests {
    public static Response createOrder(DtoOrder dtoOrder) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(dtoOrder)
                .when()
                .post("/api/v1/orders");
        return response;
    }

    public static Response getOrderByNumber(DtoOrder dtoOrder) {
        Response response = given()
                .header("Content-type", "application/json")
                .params("t", dtoOrder.getTrack())
                .when()
                .get("/api/v1/orders/track");
        return response;
    }

    public static Response acceptOrder(DtoOrder dtoOrder, DtoCourier dtoCourier) {
        Response response = given()
                .header("Content-type", "application/json")
                .params("courierId", dtoCourier.getId())
                .when()
                .put(String.format("/api/v1/orders/accept/%d", dtoOrder.getId()));
        return response;
    }

    public static Response getOrdersList(DtoCourier dtoCourier) {
        Response response = given()
                .header("Content-type", "application/json")
                .params("courierId", dtoCourier.getId())
                .when()
                .get("/api/v1/orders");
        return response;
    }

    public static Response cancelOrder(DtoOrder dtoOrder) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(dtoOrder)
                .when()
                .put("/api/v1/orders/cancel");
        return response;
    }

    public static Response completeOrder(DtoOrder dtoOrder) {
        Response response = given()
                .header("Content-type", "application/json")
                .pathParams("id", dtoOrder.getId())
                .when()
                .put("/api/v1/orders/finish/{id}");
        return response;
    }

}