package ca.utoronto.utm.mcs;

import org.neo4j.driver.Driver;

import javax.inject.Inject;
import javax.inject.Singleton;

// All your database transactions or queries should
// go in this class
@Singleton
public class Neo4jDAO {
    public Driver driver;

    @Inject
    public Neo4jDAO(Driver driver) {
        this.driver = driver;
    }

    public void insertActor(String id, String name) {
        String query;
        query = "CREATE (n:Actor {id: \"%s\", name: \"%s\"})";
        query = String.format(query, id, name);
        this.driver.session().run(query);
    }

    public void insertMovie(String id, String name) {
        String query;
        query = "CREATE (n:Movie {id: \"%s\", name: \"%s\"})";
        query = String.format(query, id, name);
        this.driver.session().run(query);
    }

    public void deleteActor(String id, String name) {
        String query;
        query = "MATCH (n:Actor {id: \"%s\", name: \"%s\"})";
        query = String.format(query, id, name);
        this.driver.session().run(query);
        query = "Delete n";
        this.driver.session().run(query);
    }


    public void deleteMovie(String id, String name) {
        String query;
        query = "MATCH (n:Movie {id: \"%s\", name: \"%s\"})";
        query = String.format(query, id, name);
        this.driver.session().run(query);
        query = "Delete n";
        this.driver.session().run(query);
    }
}
