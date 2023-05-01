package it.loooop;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.loooop.exception.ValidationException;
import it.loooop.model.Notification;
import it.loooop.request.ListRequest;
import it.loooop.response.GenericResponse;
import it.loooop.response.ListResponse;
import it.loooop.service.NotificationService;
import it.loooop.utils.Utils;
import it.loooop.utils.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.utils.Pair;

public class List implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    Logger logger = LoggerFactory.getLogger(List.class);
    ObjectMapper mapper = new ObjectMapper();
    NotificationService notificationService = new NotificationService();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {

        try {
            ListRequest request = mapper.readValue(event.getBody(), ListRequest.class);

            logger.info(request.toString());

            Validation.validateListRequest(request);

            logger.info("request.getSearchKey() -> {}", request.getSearchKey());

            Pair<java.util.List<Notification>, Integer> searchResult =
                    notificationService.listNotifications(request.getSearchKey(), request.getFrom(), request.getTo());

            return Utils.buildResponse("OK", mapper.writeValueAsString(
                    new ListResponse("OK", "Search performed", searchResult.left(), searchResult.right())));

        } catch (ValidationException e1) {
            logger.atError().setCause(e1).log("Exception = ValidationException Code = {} Message = {}", e1.getCode(), e1.getMessage());
            return Utils.buildResponse("KO", new GenericResponse("KO", e1.getMessage()).toString());
        } catch (JsonProcessingException e2) {
            logger.atError().setCause(e2).log("Exception = JsonProcessingException Message = {}", e2.getMessage());
            return Utils.buildResponse("KO", new GenericResponse("KO", e2.getMessage()).toString());
        } catch (Exception e) {
            logger.atError().setCause(e).log("Exception = Exception Message = {} Cause = {} StackTrace = {}", e.getMessage(), e.getCause(), e.getStackTrace());
            return Utils.buildResponse("KO", new GenericResponse("KO", e.getMessage()).toString());
        }

    }
}
