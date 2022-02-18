package ca.utoronto.utm.mcs;

import org.json.JSONArray;
import org.json.JSONObject;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

// All your database transactions or queries should
// go in this class
@Singleton
public class Neo4jDAO {
    public Driver driver;

    @Inject
    public Neo4jDAO(Driver driver) {
        this.driver = driver;
    }

    public int insertActor(String actorId, String name) {
        try {
            Session curSession = this.driver.session();
            String query = "MATCH(a:actor) WHERE a.id=\"%s\" RETURN a".formatted(actorId);
            Result res = curSession.run(query);
            if(res.hasNext()){
                // actorId already exists
                return 400;
            }
            else {
                // insert the actor
                curSession.run("Create (a:actor {Name: \"%s\", id: \"%s\"})".formatted(name, actorId));
                return 200;
            }
        }
        catch (Exception e){
            return 500;
        }
    }

    public int insertMovie(String movieId, String name) {
        try {
            Session curSession = this.driver.session();
            String query = "MATCH(a:movie) WHERE a.id=\"%s\" RETURN a".formatted(movieId);
            Result res = curSession.run(query);
            if(res.hasNext()){
                // movieId already exists
                return 400;
            }
            else {
                // insert the Movie
                curSession.run("Create (a:movie {Name: \"%s\", id: \"%s\"})".formatted(name, movieId));
                return 200;
            }
        }
        catch (Exception e){
            return 500;
        }
    }

    public int insertRelationship(String actorId, String movieId) {
        try {
            Session curSession = this.driver.session();

            String query = "MATCH(a:movie) WHERE a.id=\"%s\" RETURN a".formatted(movieId);
            Result res1 = curSession.run(query);
            boolean hasMovie = res1.hasNext();

            query = "MATCH(a:actor) WHERE a.id=\"%s\" RETURN a".formatted(actorId);
            Result res2 = curSession.run(query);
            boolean hasActor = res2.hasNext();

            if (hasActor && hasMovie){
                // check if the
                query = "MATCH(a:actor)-[r:ACTED_IN]->(m:movie) WHERE a.id=\"%s\" AND m.id=\"%s\" RETURN r".formatted(actorId, movieId);
                Result res3 = curSession.run(query);
                if (res3.hasNext()){
                    // edge case: relationship already exists
                    return 400;
                }

                // insert the relationship
                query = "MATCH(a:actor),(m:movie) WHERE a.id=\"%s\" AND m.id=\"%s\" CREATE (a)-[r:ACTED_IN]->(m)".formatted(actorId, movieId);
                curSession.run(query);
                return 200;
            }
            else {
                // actorId or movieId invalid
                return 404;
            }

        }
        catch (Exception e){
            return 500;
        }
    }

    public DBResponse getActor(String actorId){
        try {
            Session curSession = this.driver.session();
            String query = "MATCH(a:actor) WHERE a.id=\"%s\" RETURN a.Name".formatted(actorId);
            Result res = curSession.run(query);
            if(!res.hasNext()){
                // actorId does not exist
                return new DBResponse("NOT FOUND", 404);
            }
            JSONObject responseObj = new JSONObject();
            responseObj.put("actorId", actorId);
            responseObj.put("name", res.next().get("a.Name").asString());

            query = "MATCH (a:actor {id: \"%s\"})-[:ACTED_IN]->(m:movie) RETURN m.id".formatted(actorId);
            Result res2 = curSession.run(query);

            List<String> movieIds = new ArrayList<String>();
            while (res2.hasNext()){
                String curMovieId = res2.next().get("m.id").asString();
                movieIds.add(curMovieId);
            }
            responseObj.put("movies", new JSONArray(movieIds));
            return new DBResponse(responseObj.toString(), 200);
        }
        catch (Exception e){
            return new DBResponse("INTERNAL SERVER ERROR", 500);
        }
    }

    public DBResponse getMovie(String movieId){
        try {
            Session curSession = this.driver.session();
            String query = "MATCH(m:movie) WHERE m.id=\"%s\" RETURN m.Name".formatted(movieId);
            Result res = curSession.run(query);
            if(!res.hasNext()){
                // movieId does not exist
                return new DBResponse("NOT FOUND", 404);
            }
            JSONObject responseObj = new JSONObject();
            responseObj.put("movieId", movieId);
            responseObj.put("name", res.next().get("m.Name").asString());

            query = "MATCH (a:actor)-[:ACTED_IN]->(m:movie {id: \"%s\"}) RETURN a.id".formatted(movieId);
            Result res2 = curSession.run(query);

            List<String> actorIds = new ArrayList<String>();
            while (res2.hasNext()){
                String curActorId = res2.next().get("a.id").asString();
                actorIds.add(curActorId);
            }
            responseObj.put("actors", new JSONArray(actorIds));
            return new DBResponse(responseObj.toString(), 200);
        }
        catch (Exception e){
            return new DBResponse("INTERNAL SERVER ERROR", 500);
        }
    }

    public DBResponse hasRelationship(String actorId, String movieId){
        try {
            Session curSession = this.driver.session();

            String query = "MATCH(a:actor) WHERE a.id=\"%s\" RETURN a.Name".formatted(actorId);
            Result res = curSession.run(query);
            if(!res.hasNext()){
                // actorId does not exist
                return new DBResponse("NOT FOUND", 404);
            }

            query = "MATCH(m:movie) WHERE m.id=\"%s\" RETURN m.Name".formatted(movieId);
            res = curSession.run(query);
            if(!res.hasNext()){
                // movieId does not exist
                return new DBResponse("NOT FOUND", 404);
            }

            query = "MATCH (a:actor {id: \"%s\"})-[r:ACTED_IN]->(m:movie {id: \"%s\"}) RETURN r".formatted(actorId, movieId);
            res = curSession.run(query);
            boolean hasRel = res.hasNext();

            JSONObject responseObj = new JSONObject();
            responseObj.put("movieId", movieId);
            responseObj.put("actorId", actorId);
            responseObj.put("hasRelationship", hasRel);
            return new DBResponse(responseObj.toString(), 200);

        }
        catch (Exception e){
            return new DBResponse("INTERNAL SERVER ERROR", 500);
        }
    }
}

