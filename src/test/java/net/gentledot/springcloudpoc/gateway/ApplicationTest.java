package net.gentledot.springcloudpoc.gateway;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {"httpbin=http://localhost:${wiremock.server.port}"})
@AutoConfigureWireMock(port = 0)
class ApplicationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void contextLoads() throws Exception {

        //Stubs
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/get"))
            .willReturn(WireMock.aResponse()
                .withBody("{\"headers\":{\"Hello\":\"World\"}}")
                .withHeader("Content-Type", "application/json")));
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/delay/3"))
            .willReturn(WireMock.aResponse()
                .withBody("no fallback")
                .withFixedDelay(3000)));

        webTestClient
            .get().uri("/get")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.headers.Hello").isEqualTo("World");

        webTestClient
            .get().uri("/delay/3")
            .header("Host", "www.circuitbreaker.com")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .consumeWith(
                response -> assertThat(response.getResponseBody()).isEqualTo(
                    "fallback".getBytes()));
    }
}