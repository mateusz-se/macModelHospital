package bsk.boot.test.integration;

import bsk.boot.Application;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationTest {
    private static ConfigurableApplicationContext context;

    @BeforeClass
    public static void start() throws Exception {
        Callable<ConfigurableApplicationContext> callable = () -> SpringApplication.run(Application.class);
        Future<ConfigurableApplicationContext> future = Executors.newSingleThreadExecutor().submit(callable);
        context = future.get(60, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void stop() {
        if (context != null) {
            context.close();
        }
    }


    @Test
    public void redirectToLoginWhenNotLoggedIn() {
        ResponseEntity<String> entity = getRestTemplate().getForEntity(
                "http://localhost:8080/another/example", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK) ;
        assertThat(entity.getBody()).contains("<h3>Login with Username and Password</h3><form name='f' action='/login' method='POST'>") ;
    }

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
            }
        });
        return restTemplate;

    }
}
