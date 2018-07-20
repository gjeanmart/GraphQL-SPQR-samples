package org.gjeanmart.graphql.graphqlsqprtest.domain;

import io.leangen.graphql.annotations.types.GraphQLInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@GraphQLInterface(name = "Resource", implementationAutoDiscovery = true)
public  abstract class Resource  {
    
    private String type;
    private String id;
    
    
}
