package tests;

import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class DemoWebshopTests {
    @Test
    void addCartAsAnonymTest() {
        String body = "product_attribute_72_5_18=53" +
                "&product_attribute_72_6_19=54" +
                "&product_attribute_72_3_20=57" +
                "&addtocart_72.EnteredQuantity=1";

        given()
                .filter(withCustomTemplates())
                .log().uri()
                .log().body()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body(body)
                .when()
                .post("https://demowebshop.tricentis.com/addproducttocart/details/72/1")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
                .body("updatetopcartsectionhtml", is("(1)"));
    }

    @Test
    void addCartAsNotAuthorizedTest() {
        String authCookieKey = "NOPCOMMERCE.AUTH";
        String authCookieValue = "C3B23684816523C3E10934D04BB7269BD98869D9B42CE39E423791717071721A102B8200F60C64202EFF08A8BB31019B281869DCC0A013C1BDDB8A2C040B083CB7EE18743398AB5F22BC4F57596FA47732C6ED3EF4505FAC4C44E1324BC72D5D6C434630381EAAD9EF1A5CC8316CAA105B3A34D3709FBA22C343FDCB8F4546262AC16884DD69B73F0ECE3F16DFCBAE2A;";
        String body = "product_attribute_72_5_18=53" +
                "&product_attribute_72_6_19=54" +
                "&product_attribute_72_3_20=57" +
                "&addtocart_72.EnteredQuantity=1";

        given()
                .filter(withCustomTemplates())
                .log().uri()
                .log().body()
                .cookie(authCookieKey, authCookieValue, "Nop.customer", "d5407a42-2690-493f-8b09-e89bc69f890b;")
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body(body)
                .when()
                .post("https://demowebshop.tricentis.com/addproducttocart/details/72/1")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
                .body("updatetopcartsectionhtml", is("(1)"));
    }
}
