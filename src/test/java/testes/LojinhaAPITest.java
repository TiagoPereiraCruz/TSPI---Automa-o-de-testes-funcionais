package testes;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class LojinhaAPITest {
    private String token;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://165.227.93.41";
        RestAssured.basePath = "lojinha";

        token = RestAssured
                    .given()
                        .contentType(ContentType.JSON)
                        .body("{\n" +
                            " \"usuariologin\": \"tiagocr\",\n" +
                            " \"usuariosenha\": \"t1p9d8c8\"\n" +
                            "}")
                    .when()
                        .post("login")
                    .then()
                        .extract()
                            .path("data.token");
    }

    @Test
    public void testBuscarDadosDeUmProdutoEspecifico() {
        RestAssured
                .given()
                    .header("token", token)
                .when()
                    .get("produto/5020")
                .then()
                    .assertThat()
                        .statusCode(200)
                        .body("data.produtonome", Matchers.equalTo("Playstation 3"))
                        .body("data.componentes[0].componentenome", Matchers.equalTo("Controle"))
                        .body("message", Matchers.equalTo("Detalhando dados do produto"));
    }

    @Test
    public void testBuscarDadosDeUmComponenteEspecifico() {
        RestAssured
                .given()
                    .header("token", token)
                .when()
                    .get("produto/5020/componente/1042")
                .then()
                    .assertThat()
                    .body("data.componentenome", Matchers.equalTo("Controle"))
                    .body("data.componentequantidade", Matchers.equalTo("2"))
                    .body("message", Matchers.equalTo("Detalhando dados do componente de produto"));
    }
}
