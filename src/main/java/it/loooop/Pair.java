package it.loooop;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.loooop.exception.ValidationException;
import it.loooop.model.Device;
import it.loooop.request.PairRequest;
import it.loooop.response.GenericResponse;
import it.loooop.response.PairResponse;
import it.loooop.service.DeviceService;
import it.loooop.utils.Mapper;
import it.loooop.utils.Utils;
import it.loooop.utils.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pair implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    Logger logger = LoggerFactory.getLogger(Pair.class);
    ObjectMapper mapper = new ObjectMapper();
    DeviceService deviceService = new DeviceService();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {

        try {
            PairRequest request = mapper.readValue(event.getBody(), PairRequest.class);

            logger.info(request.toString());

            Validation.validatePairRequest(request);

            Device device = Mapper.pairRequestToDevice(request);

            //Search for existing device
            Device deviceFound = deviceService.getDevice(device.getDeviceId());

            //device found
            if (deviceFound != null && !device.getDeviceInfo().equals(deviceFound.getDeviceInfo())) {
                //if deviceInfo changes -> user changed physical device -> disable the old one
                deviceFound.setDeviceId(deviceFound.getDeviceId().replace("active", "inactive") + "#" + Utils.currentStringDate());
                deviceService.persistDevice(deviceFound);
            }

            deviceService.updateDevice(device);

            return Utils.buildResponse("OK", mapper.writeValueAsString(new PairResponse("OK", "Device paired", device.getDeviceId())));

        } catch (ValidationException e1) {
            logger.atError().setCause(e1).log("Exception = ValidationException Code = {} Message = {}", e1.getCode(), e1.getMessage());
            return Utils.buildResponse("KO", new GenericResponse("KO", e1.getMessage()).toString());
        } catch (JsonProcessingException e2) {
            logger.atError().setCause(e2).log("Exception = JsonProcessingException Message = {}", e2.getMessage());
            return Utils.buildResponse("KO", new GenericResponse("KO", e2.getMessage()).toString());
        } catch (Exception e) {
            logger.atError().setCause(e).log("Exception = Exception Message = {}", e.getMessage());
            return Utils.buildResponse("KO", new GenericResponse("KO", e.getMessage()).toString());
        }

    }
}
