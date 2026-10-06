package com.crud_trabajo.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.bson.Document;

@SpringBootTest
class Trabajo4ApplicationTests {

	@Autowired(required = false)
	private MongoTemplate mongoTemplate;

	@Test
	void contextLoads() {
		if (mongoTemplate != null) {
			Document ping = mongoTemplate.getDb().runCommand(new Document("ping", 1));
			System.out.println("MONGO PING RESULT: " + ping);
		}
	}

}
