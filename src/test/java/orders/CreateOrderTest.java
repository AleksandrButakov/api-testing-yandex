package orders;

import dto.DtoOrder;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import request.OrderRequests;

import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseOrderTest {
    private List<String> color;

    public CreateOrderTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Цвет: {0}")
    public static Object[][] colorScooter() {
        return new Object[][]{
                {List.of("BLUE")},
                {List.of("GRAY")},
                {List.of("GRAY", "BLACK")},
                {List.of("")},
        };
    }

    @Test
    @DisplayName("Создание заказа с различными цветами")
    public void createOrderColorBlueTest() {
        DtoOrder order = new DtoOrder();
        order.setColor(color);
        Response response = OrderRequests.createOrder(order);
        response.then().assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("track", notNullValue());
    }

}
