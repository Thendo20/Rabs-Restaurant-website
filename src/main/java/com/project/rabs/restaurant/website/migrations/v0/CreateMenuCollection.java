package com.project.rabs.restaurant.website.migrations.v0;

import io.mongock.api.annotations.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.schema.JsonSchemaProperty;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.data.mongodb.core.validation.Validator;

@ChangeUnit(id = "create-menu-collection", order = "001", author = "Thendo")
public class CreateMenuCollection {
    @BeforeExecution
    public void beforeExecution(MongoTemplate mongoTemplate) {
        if (!mongoTemplate.collectionExists("menu")) {
            mongoTemplate.createCollection("menu", CollectionOptions.empty()
                    .validator(Validator.schema(MongoJsonSchema.builder()
                            .required("itemName", "price")
                            .properties(
                                    JsonSchemaProperty.string("itemName"),
                                    JsonSchemaProperty.float64("price"),
                                    JsonSchemaProperty.string("description"),
                                    JsonSchemaProperty.int64("stockCount")).build())));
        }
        mongoTemplate.indexOps("menu")
                .ensureIndex(new Index().on("itemName", Sort.Direction.ASC).unique());

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
