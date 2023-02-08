package orders;

import dto.DtoOrder;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import request.OrderRequests;

import static org.hamcrest.CoreMatchers.equalTo;

public class CancelOrderTest extends BaseOrderTest {

    @Test
    @DisplayName("Отменить заказ")
    public void cancelOrderTest() {
        DtoOrder order = new DtoOrder();
        Response response = OrderRequests.createOrder(order);
        order = response.as(DtoOrder.class);
        response = OrderRequests.cancelOrder(order);
        response.then().assertThat()
                .statusCode(200)
                .and()
                .body("ok", equalTo("true"));
    }


}
