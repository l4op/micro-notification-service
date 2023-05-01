package it.loooop.service;

import it.loooop.model.Device;
import it.loooop.client.DynamoDB;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class DeviceService {

    DynamoDB dynamoDb = new DynamoDB();
    String devicesTable = System.getenv("DYNAMODB_DEVICES_TABLE");

    DynamoDbTable<Device> mappedTable = dynamoDb.client()
            .table(devicesTable, TableSchema.fromBean(Device.class));

    public String persistDevice(Device device){
        mappedTable.putItem(device);
        return device.getDeviceId();
    }

    public Device getDevice(String deviceId){
        return mappedTable.getItem(Key.builder().partitionValue(deviceId).build());
    }

    public String updateDevice(Device device) {
        mappedTable.updateItem(device);
        return device.getDeviceId();
    }

}
