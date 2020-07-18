package aws.demo.dynamodb;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DemoDynamoDB {

    public static void main(String[] args) {
        // Create the DynamoDbClient object
        Region region = Region.AP_SOUTH_1;
        DynamoDbClient ddb = DynamoDbClient.builder()
                .endpointOverride(URI.create("http://localhost:8000"))
                .region(region)
                .build();
        // create table with single primary key [demo:email(hashkey)]
        //createTable(ddb, "demo", "email");

        //create table with composite primary key
        //createTableComKey(ddb,"greetings");

        // putItem into greetings
        //putItemsinGreetings(ddb);
        // getItem from greetings
        getDynamoDBItem(ddb,"greetings","Language","English","Greeting","Welcome");
    }

    private static void putItemsinGreetings(DynamoDbClient ddb) {
        Map<String, AttributeValue> itemmap = new HashMap<String,AttributeValue>();

        itemmap.put("Language", AttributeValue.builder().s("English").build());
        itemmap.put("Greeting",AttributeValue.builder().s("Welcome").build());


        PutItemRequest request = PutItemRequest.builder()
                .tableName("greetings")
                .item(itemmap)
                .build();

        PutItemResponse response = ddb.putItem(request);

        Map<String, AttributeValue> responseMap = response.attributes();

        System.out.println("Put Item is successful");
    }

    public static String createTable(DynamoDbClient ddb, String tableName, String key) {

        // Create the CreateTableRequest object
        CreateTableRequest request = CreateTableRequest.builder()
                .attributeDefinitions(AttributeDefinition.builder()
                        .attributeName(key)
                        .attributeType(ScalarAttributeType.S)
                        .build())
                .keySchema(KeySchemaElement.builder()
                        .attributeName(key)
                        .keyType(KeyType.HASH)
                        .build())
                .provisionedThroughput(ProvisionedThroughput.builder()
                        .readCapacityUnits(Long.valueOf(10))
                        .writeCapacityUnits(Long.valueOf(10))
                        .build())
                .tableName(tableName)
                .build();

        String newTable = "";
        try {
            CreateTableResponse response = ddb.createTable(request);
            newTable = response.tableDescription().tableName();
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return newTable;
    }

    public static String createTableComKey(DynamoDbClient ddb, String tableName) {
        CreateTableRequest request = CreateTableRequest.builder()
                .attributeDefinitions(
                        AttributeDefinition.builder()
                                .attributeName("Language")
                                .attributeType(ScalarAttributeType.S)
                                .build(),
                        AttributeDefinition.builder()
                                .attributeName("Greeting")
                                .attributeType(ScalarAttributeType.S)
                                .build())
                .keySchema(
                        KeySchemaElement.builder()
                                .attributeName("Language")
                                .keyType(KeyType.HASH)
                                .build(),
                        KeySchemaElement.builder()
                                .attributeName("Greeting")
                                .keyType(KeyType.RANGE)
                                .build())
                .provisionedThroughput(
                        ProvisionedThroughput.builder()
                                .readCapacityUnits(10L)
                                .writeCapacityUnits(10L).build())
                .tableName(tableName)
                .build();

        String tableId = "";

        try {
            CreateTableResponse result = ddb.createTable(request);
            tableId = result.tableDescription().tableId();
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return tableId;
    }

    public static void getDynamoDBItem(DynamoDbClient ddb,String tableName,String key,String keyVal,String sortkey,String sortkeyVal ) {

        HashMap<String, AttributeValue> keyToGet = new HashMap<String, AttributeValue>();
        //
        keyToGet.put(key, AttributeValue.builder()
                .s(keyVal).build());

        keyToGet.put(sortkey, AttributeValue.builder()
                .s(sortkeyVal).build());


        // Create a GetItemRequest object
        GetItemRequest request = GetItemRequest.builder()
                .tableName(tableName)
                .key(keyToGet)
                .build();

        try {
            Map<String, AttributeValue> returnedItem = ddb.getItem(request).item();

            if (returnedItem != null) {
                Set<String> keys = returnedItem.keySet();
                System.out.println("Table Attributes: \n");

                for (String key1 : keys) {
                    System.out.format("%s: %s\n", key1, returnedItem.get(key1).toString());
                }
            } else {
                System.out.format("No item found with the key %s!\n", key);
            }
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

}