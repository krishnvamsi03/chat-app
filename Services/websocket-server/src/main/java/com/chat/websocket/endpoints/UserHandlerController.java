package com.chat.websocket.endpoints;

import com.chat.websocket.converter.ConvertToJson;
import com.chat.websocket.model.GroupCreateRequest;
import com.chat.websocket.model.GroupMember;
import com.chat.websocket.model.ResponseBody;
import com.chat.websocket.store.mongo.MongoApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.InsertOneResult;
import lombok.Getter;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class UserHandlerController {

    @Autowired
    private MongoApi mongoApi;

    private static String ACTIVE_USER_SESSIONS = "ActiveSessions";

    @GetMapping("/available/users")
    public ResponseEntity<?> getAvailableUsers() {
        MongoCollection<Document> mongoCollection =
                mongoApi.getMongoCollection(ACTIVE_USER_SESSIONS);
        FindIterable<Document> documents = mongoCollection.find();
        MongoCursor<Document> cursor = documents.cursor();
        HashMap<String, String> users = new HashMap<>();
        try {
            while (cursor.hasNext()) {
                String userId = cursor.next().getString("userId");
                Document document =
                        mongoApi.getDocumentOnEq("user", "_id",
                                new ObjectId(userId));
                users.put(document.getObjectId("_id").toString(),
                        document.get("username").toString());
            }
        } finally {
            cursor.close();
        }
        return new ResponseEntity<>(users,
                HttpStatus.ACCEPTED);
    }

    @PostMapping("create/group/")
    public ResponseEntity<?> createGroup(@RequestBody GroupCreateRequest groupCreateRequest) throws JsonProcessingException {
        InsertOneResult result =
                mongoApi.createDocument(mongoApi.getMongoCollection("Groups"),
                        groupCreateRequest);
        mongoApi.createDocument(mongoApi.getMongoCollection("GroupMembers"),
                new GroupMember(groupCreateRequest.getAdminUserId(),
                        result.getInsertedId().asObjectId().toString()));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("add/member/{groupId}/{userId}")
    public ResponseEntity<?> addMember(@PathVariable String groupId,
                                       @PathVariable String userId) throws JsonProcessingException {
        InsertOneResult result =
                mongoApi.createDocument(mongoApi.getMongoCollection(
                                "GroupMembers"),
                        new GroupMember(groupId, userId));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
