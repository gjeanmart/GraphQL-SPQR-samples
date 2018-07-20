package org.gjeanmart.graphql.graphqlsqprtest.domain;

import io.leangen.graphql.annotations.types.GraphQLInterface;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Resource2 extends Resource {

    private String name;
    private String field1;
    private String field2;
    
    public Resource2(String id, String name, String field1, String field2) {
        super("TYPE2", id);
        this.name = name;
        this.field1 = field1;
        this.field2 = field2;
    }
   
    
}
