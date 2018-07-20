package org.gjeanmart.graphql.graphqlsqprtest.domain;

import io.leangen.graphql.annotations.types.GraphQLInterface;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Resource1 extends Resource {

    private String name;
    private String fieldA;
    private String fieldB;
    
    public Resource1(String id, String name, String fieldA, String fieldB) {
        super("TYPE1", id);
        this.name = name;
        this.fieldA = fieldA;
        this.fieldB = fieldB;
    }
   
    
}
