package ca.utoronto.utm.mcs;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Record;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Scanner;

// All your database transactions or queries should
// go in this class
@Singleton
public class Neo4jDAO {
    public Driver driver;

    @Inject
    public Neo4jDAO(Driver driver) {
        this.driver = driver;
    }

    public int existsActor(String id){
        String query;
        query = "MATCH (n:actor {id: \"%s\"}) WITH COUNT(n) > 0  as node_exists RETURN node_exists";
        query = String.format(query, id);
        Result result = this.driver.session().run(query);
        Record r = result.next();
        if (r.toString().contains("TRUE")){
            return 1;
        }else{
            return 0;
        }

    }

    public int existsMovie(String id){
        String query;
        query = "MATCH (n:movie {id: \"%s\"}) WITH COUNT(n) > 0  as node_exists RETURN node_exists";
        query = String.format(query, id);
        Result result = this.driver.session().run(query);
        Record r = result.next();
        if (r.toString().contains("TRUE")){
            return 1;
        }else{
            return 0;
        }

    }
    public int existsRelation(String actorId, String movieId){
        String query;
        query = "MATCH (n:actedIn {actorId: \"%s\", movieId: \"%s\"}) WITH COUNT(n) > 0  as node_exists RETURN node_exists";
        query = String.format(query, actorId, movieId);
        Result result = this.driver.session().run(query);
        Record r = result.next();
        if (r.toString().contains("TRUE")){
            return 1;
        }else{
            return 0;
        }

    }

    public void insertActor(String id, String name) {
        String query;
        query = "CREATE (n:actor {id: \"%s\", name: \"%s\"})";
        query = String.format(query, id, name);
        this.driver.session().run(query);
    }

    public void insertMovie(String id, String name) {
        String query;
        query = "CREATE (n:movie {id: \"%s\", name: \"%s\"})";
        query = String.format(query, id, name);
        this.driver.session().run(query);
    }

    public void insertActedIn(String actorId, String movieId) {
        String query;
        query = "CREATE (n:actedIn {actorId: \"%s\", movieId: \"%s\"})";
        query = String.format(query, actorId, movieId);
        this.driver.session().run(query);
    }

    public void deleteActor(String id, String name) {
        String query;
        query = "MATCH (n:actor {id: \"%s\", name: \"%s\"}) DELETE n";
        query = String.format(query, id, name);
        this.driver.session().run(query);
    }

    public void deleteMovie(String id, String name) {
        String query;
        query = "MATCH (n:movie {id: \"%s\", name: \"%s\"}) DELETE n";
        query = String.format(query, id, name);
        this.driver.session().run(query);
    }

    public void deleteActedIn(String actorId, String movieId) {
        String query;
        query = "MATCH (n:actedIn {actorId: \"%s\", movieId: \"%s\"}) DELETE n";
        query = String.format(query, actorId, movieId);
        this.driver.session().run(query);
    }
}
