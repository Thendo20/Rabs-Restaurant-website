package com.project.rabs.restaurant.website.migrations.v0;

import io.mongock.api.annotations.*;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.schema.JsonSchemaProperty;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.data.mongodb.core.validation.Validator;

@ChangeUnit(id = "create-orders-collection", order = "002", author = "George")
public class CreateOrderCollection {
    @BeforeExecution
    public void beforeExecution(MongoTemplate mongoTemplate) {
        if (!mongoTemplate.collectionExists("order")) {
            mongoTemplate.createCollection("order", CollectionOptions.empty()
                    .validator(Validator.schema(MongoJsonSchema.builder()
                            .properties(
                                    JsonSchemaProperty.string("order_id"),
                                    JsonSchemaProperty.string("customer_id"),
                                    JsonSchemaProperty.object("items"),
                                    JsonSchemaProperty.decimal128("total_price"),
                                    JsonSchemaProperty.string("order_status"),
                                    JsonSchemaProperty.string("payment_status"),
                                    JsonSchemaProperty.string("payment_type"),
                                    JsonSchemaProperty.date("timestamp")).build())));
        }
    }

    @Execution
    public void execute(MongoTemplate mongoTemplate) {
        // Main execution is empty as all work is done in beforeExecution
        // This method is required by Mongock
    }

    @RollbackBeforeExecution
    public void rollbackBeforeExecution(MongoTemplate mongoTemplate) {
        // No rollback needed for collection creation.
        // If the operation fails, the collection won't exist.
    }

    @RollbackExecution
    public void rollbackExecution(MongoTemplate mongoTemplate) {
        // No rollback needed for empty execution method
    }
}
