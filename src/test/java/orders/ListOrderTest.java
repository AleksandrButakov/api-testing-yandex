package orders;

import dto.DtoCourier;
import dto.DtoOrder;
import dto.DtoOrders;
import dto.DtoOrdersList;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import request.CourierRequests;
import request.OrderRequests;

public class ListOrderTest extends BaseOrderTest {

    @Test
    public void receivingOrdersListTest() {
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
                .statusCode(200);

        //принять заказ
        OrderRequests.acceptOrder(orders1.getOrder(), dtoCourier)
                .then().assertThat()
                .statusCode(200);
        OrderRequests.acceptOrder(orders2.getOrder(), dtoCourier)
                .then().assertThat()
                .statusCode(200);

        //получить список заказов
        response = OrderRequests.getOrdersList(dtoCourier);
        response.then().assertThat()
                .statusCode(200);
        DtoOrdersList dtoOrdersList = response.as(DtoOrdersList.class);
        Assert.assertNotEquals(0, dtoOrdersList.getOrders().size());
    }

}
