package com.ww3.booking.bookingbot;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;
import static com.ww3.booking.bookingbot.constant.BookingConstant.*;
import static com.ww3.booking.bookingbot.constant.DbConstant.CUSTOMER_COLLECTION;
import static com.ww3.booking.bookingbot.constant.DbConstant.TELEGRAM_DATABASE;
import static com.ww3.booking.bookingbot.enumeration.BookingStatusEnum.ROOM_SELECTION;

public class MongoDB {

    private static MongoClient mongoClient;

    public static void connectToDatabase() {
        String url = "mongodb://localhost:27017/";

        mongoClient = MongoClients.create(url);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(TELEGRAM_DATABASE);

        try {
            Bson command = new BsonDocument("ping", new BsonInt64(1));
            Document commandResult = mongoDatabase.runCommand(command);
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    public static void insertNewUserId(String userId) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(TELEGRAM_DATABASE);
        MongoCollection<Document> customersCollection = mongoDatabase.getCollection(CUSTOMER_COLLECTION);

        try {
            if (!MongoDB.userExists(userId)) {
                InsertOneResult result = customersCollection.insertOne(new Document().append("_id", userId)
                        .append(BOOKING_STATE, ROOM_SELECTION.toString()).append(ROOM_TYPE, "").append(
                                BED_PREFERENCES, "").append(ADDITIONAL, ""));
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    public static boolean userExists(String userId) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(TELEGRAM_DATABASE);
        MongoCollection<Document> customersCollection = mongoDatabase.getCollection(CUSTOMER_COLLECTION);

        Document userDoc = customersCollection.find(eq("_id", userId)).first();
        return userDoc != null;
    }

    public static void updateField(String fieldName, String newValue, String userChatId) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(TELEGRAM_DATABASE);
        MongoCollection<Document> customersCollection = mongoDatabase.getCollection(CUSTOMER_COLLECTION);

        UpdateResult updateResult = customersCollection.updateOne(eq("_id", userChatId),
                new Document("$set", new Document(fieldName, newValue)));

        if (updateResult.wasAcknowledged()) {
            updateResult.getModifiedCount();
        }
    }


    public static String getFieldValue(String fieldName, String userChatId) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(TELEGRAM_DATABASE);
        MongoCollection<Document> customersCollection = mongoDatabase.getCollection(CUSTOMER_COLLECTION);

        Document result = customersCollection.find(eq("_id", userChatId))
                .projection(fields(include(fieldName), excludeId())).first();

        assert result != null;
        return result.getString(fieldName);
    }

    public static Document getCustomerAttributes(String userChatId) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(TELEGRAM_DATABASE);
        MongoCollection<Document> customersCollection = mongoDatabase.getCollection(CUSTOMER_COLLECTION);

        return customersCollection.find(eq("_id", userChatId)).first();
    }
}
