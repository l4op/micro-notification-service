package it.loooop.utils;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    public static String currentStringDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    public static APIGatewayProxyResponseEvent buildResponse(String outcome, String body) {

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

        response.setStatusCode("OK".equals(outcome) ? 200 : 500);
        response.setBody(body);

        return response;
    }
}
