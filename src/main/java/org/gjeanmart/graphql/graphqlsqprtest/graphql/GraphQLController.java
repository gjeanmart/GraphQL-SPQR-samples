package org.gjeanmart.graphql.graphqlsqprtest.graphql;

import java.lang.reflect.AnnotatedType;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.geantyref.GenericTypeReflector;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.annotations.types.GraphQLInterface;
import io.leangen.graphql.generator.mapping.strategy.InterfaceMappingStrategy;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.metadata.strategy.query.PublicResolverBuilder;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;
import io.leangen.graphql.util.ClassUtils;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class GraphQLController {

    private static final String PATH = "/graphql";

    private final GraphQL graphQlFromAnnotated;
    
    @Autowired
    public GraphQLController(GraphQLQueryService graphQLQueryService) {
        //Schema generated from query classes
        GraphQLSchemaGenerator generator = new GraphQLSchemaGenerator()
                .withResolverBuilders(
                        new AnnotatedResolverBuilder(), //Resolve by annotations
                        new PublicResolverBuilder()) //Resolve public methods inside root package
                .withValueMapperFactory(new JacksonValueMapperFactory())
                .withInterfaceMappingStrategy(new InterfaceMappingStrategy() {
            @Override
            public boolean supports(AnnotatedType interfase) {
                return interfase.isAnnotationPresent(GraphQLInterface.class);
            }

            @Override
            public Collection<AnnotatedType> getInterfaces(AnnotatedType type) {
                Class clazz = ClassUtils.getRawType(type.getType());
                Set<AnnotatedType> interfaces = new HashSet<>();
                do {
                    AnnotatedType currentType = GenericTypeReflector.getExactSuperType(type, clazz);
                    if (supports(currentType)) {
                        interfaces.add(currentType);
                    }
                    Arrays.stream(clazz.getInterfaces())
                            .map(inter -> GenericTypeReflector.getExactSuperType(type, inter))
                            .filter(this::supports)
                            .forEach(interfaces::add);
                } while ((clazz = clazz.getSuperclass()) != Object.class && clazz != null);
                return interfaces;
            }
        });
        
        generator.withOperationsFromSingleton(graphQLQueryService);
        
        
        
        GraphQLSchema schemaFromAnnotated = generator.generate();
        
        graphQlFromAnnotated = GraphQL.newGraphQL(schemaFromAnnotated).build();
        
        log.info("Generated GraphQL schema using SPQR");
    }

    @PostMapping(value = PATH, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object indexFromAnnotated(Principal principal, @RequestBody Map<String, Object> request) {
        ExecutionInput input = ExecutionInput.newExecutionInput()
                .query((String) request.get("query"))
                .operationName((String) request.get("operationName"))
                .variables((Map<String, Object>) request.getOrDefault("variables", Collections.emptyMap()))
                .build();    
        
        ExecutionResult executionResult = graphQlFromAnnotated.execute(input);

        return executionResult;
    }
    

}