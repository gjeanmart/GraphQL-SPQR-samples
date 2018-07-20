package org.gjeanmart.graphql.graphqlsqprtest.graphql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.gjeanmart.graphql.graphqlsqprtest.domain.Article;
import org.gjeanmart.graphql.graphqlsqprtest.domain.Comment;
import org.gjeanmart.graphql.graphqlsqprtest.domain.Resource;
import org.gjeanmart.graphql.graphqlsqprtest.domain.Resource1;
import org.gjeanmart.graphql.graphqlsqprtest.domain.Resource2;
import org.gjeanmart.graphql.graphqlsqprtest.domain.User;
import org.springframework.stereotype.Component;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class GraphQLQueryService {

    @GraphQLQuery(name = "article")
    public Article getArticle(@GraphQLArgument(name = "id") String id)  {
        log.info("article(id: {}", id);
        return new Article("article1", "blabla", "user1");
    }
    
    @GraphQLQuery(name = "user")
    public User getUser(@GraphQLContext Article article)  {
        log.info("user(id: {})", article.getUserid());
        return new User("1", "greg", "greg@mail.com");
    }
    
    @GraphQLQuery(name = "user")
    public User getUser(@GraphQLContext Comment comment)  {
        log.info("user(id: {})", comment.getUserid());
        return new User("1", "greg", "greg@mail.com");
    }
    
    @GraphQLQuery(name = "comment")
    public List<Comment> getComments(@GraphQLContext Article article, @GraphQLArgument(name = "limit") Integer limit)  {
        log.info("comment(id: {})", article.getUserid());
        limit = Optional.ofNullable(limit).orElse(10);
        List<Comment> comments = new ArrayList<>();
        for(int i = 0; i < limit; i++) {
            comments.add(new Comment(i, "comment"+i, "user"+i));
        }
        
        return comments;
    }
    
    @GraphQLQuery(name = "resource")
    public Resource getResource(@GraphQLArgument(name = "id") String id)  {
        log.info("resource(id: {})", id);
        
        if(id.equals("1")) {
            return new Resource1(id, "resource1", "hello", "world");
        } else {
            return new Resource2(id, "resource1", "hello2", "worl2d");
            
        }
        
    }
    
    @GraphQLQuery(name = "resources")
    public List<Resource> getResources()  {
        return Arrays.asList(new Resource1("1", "resource1", "hello", "world"), new Resource2("2", "resource1", "hello2", "worl2d"));        
    }
    
    
    
    
}
