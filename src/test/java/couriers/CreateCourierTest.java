package couriers;

import dto.DtoCourier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import request.CourierRequests;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest extends BaseCourierTest {

    @Test
    @DisplayName("Создание курьера")
    public void createNewCourierTest() {
        String login = String.format("Alex%d", (int) (Math.random() * (9999 - 1111) + 1111));
        String password = "12345";
        DtoCourier courier = new DtoCourier(login, password, "Alexey");
        Response response = CourierRequests.createCourier(courier);
        response.then().assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));

        loginAndDeleteCourier(courier);
    }

    @Test
    @DisplayName("Создание курьера без логина, пароля, имени")
    public void createNewCourierWithoutRequiredFieldsTest() {
        String login = String.format("Alex%d", (int) (Math.random() * (9999 - 1111) + 1111));
        String password = "12345";
        DtoCourier courier = new DtoCourier("", password, "Alexey");
        Response response = CourierRequests.createCourier(courier);
        response.then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

        courier = new DtoCourier(login, "", "Alexey");
        response = CourierRequests.createCourier(courier);
        response.then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

        courier = new DtoCourier(login, password, "");
        response = CourierRequests.createCourier(courier);
        response.then().assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));

        loginAndDeleteCourier(courier);
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    public void cantCreateTwoOwnersCouriersTest() {
        String login = String.format("Alex%d", (int) (Math.random() * (9999 - 1111) + 1111));
        String password = "12345";
        DtoCourier courier = new DtoCourier(login, password, "Alexey");
        Response response = CourierRequests.createCourier(courier);
        response.then().assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));

        response = CourierRequests.createCourier(courier);
        response.then().assertThat()
                .statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        loginAndDeleteCourier(courier);
    }

}