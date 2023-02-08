package orders;

import dto.DtoCourier;
import dto.DtoOrder;
import dto.DtoOrders;
import io.restassured.response.Response;
import org.junit.Test;
import request.CourierRequests;
import request.OrderRequests;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderForNumberTest extends BaseOrderTest {

    @Test
    public void receivingOrdersForNumberTest() {
        //создание курьера
        String login = String.format("Alex%d", (int) (Math.random() * (9999 - 1111) + 1111));
        String password = "12345";
        String firstName = "Alex";
        CourierRequests.createCourier(new DtoCourier(login, password, firstName))
                .then().assertThat()
                .statusCode(201);

        //залогиниться
        Response response = CourierRequests.loginCourier(new DtoCourier(login, password));
        response.then().assertThat()
                .statusCode(200);
        DtoCourier dtoCourier = response.as(DtoCourier.class);

        //создать заказ
        response = OrderRequests.createOrder(new DtoOrder());
        response.then().assertThat()
                .statusCode(201);
        DtoOrder order1 = response.as(DtoOrder.class);

        response = OrderRequests.createOrder(new DtoOrder());
        response.then().assertThat()
                .statusCode(201);
        DtoOrder order2 = response.as(DtoOrder.class);

        //получение заказа
        response = OrderRequests.getOrderByNumber(order1);
        DtoOrders orders1 = response.as(DtoOrders.class);
        response.then().assertThat()
                .statusCode(200);
        response = OrderRequests.getOrderByNumber(order2);
        DtoOrders orders2 = response.as(DtoOrders.class);
        response.then().assertThat()
                .statusCode(200)
                .and()
                .body(notNullValue());
    }

}
