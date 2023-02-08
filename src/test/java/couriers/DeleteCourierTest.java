package couriers;

import dto.DtoCourier;
import io.restassured.response.Response;
import org.junit.Test;
import request.CourierRequests;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.hamcrest.Matchers.equalTo;

public class DeleteCourierTest extends BaseCourierTest{

    @Test
    public void cantDeleteCouriersWithoutNonExistentIdTest() {
        DtoCourier dtoCourier = new DtoCourier(000);
        Response response = CourierRequests.deleteCourier(dtoCourier);
        response.then().assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Курьера с таким id нет."));
    }

}