package orders;

import dto.DtoCourier;
import dto.DtoOrder;
import dto.DtoOrders;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import request.CourierRequests;
import request.OrderRequests;

import static org.hamcrest.CoreMatchers.equalTo;

public class GetOrderTest extends BaseOrderTest {

    @Test
    @DisplayName("Получить заказ по его номеру")
    public void getOrderTest() {
        String login = String.format("Alex%d", (int) (Math.random() * (9999 - 1111) + 1111));
        String password = "12345";
        DtoCourier courier = new DtoCourier(login, password, "Alexey");
        CourierRequests.createCourier(courier);
        courier = new DtoCourier(login, password);
        Response responseCourier = CourierRequests.loginCourier(courier);
        courier = responseCourier.as(DtoCourier.class);

        DtoOrder order = new DtoOrder();
        Response response = OrderRequests.createOrder(order);
        order = response.as(DtoOrder.class);
        response = OrderRequests.getOrderByNumber(order);
        response.then().assertThat()
                .statusCode(200);
        DtoOrders dtoOrders = response.as(DtoOrders.class);

        response = OrderRequests.acceptOrder(dtoOrders.getOrder(), courier);
        response.then().assertThat()
                .statusCode(200)
                .and()
                .body("ok", equalTo(true));

        response = CourierRequests.deleteCourier(courier);
        response.then().assertThat()
                .statusCode(200)
                .and()
                .body("ok", equalTo(true));

        loginAndDeleteCourier(courier);
    }


}
