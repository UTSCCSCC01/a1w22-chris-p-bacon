package ca.utoronto.utm.mcs;

import org.neo4j.driver.Driver;

import javax.inject.Inject;
import javax.inject.Singleton;

// All your database transactions or queries should
// go in this class
@Singleton
public class Neo4jDAO {
    // TODO Complete This Class

//    public Driver driver;
//
//    @Inject
//    public Neo4jDAO(Driver driver) {
//        this.driver = driver;
//    }

    private final Session session;
    private final Driver driver;

    private final String uriDb = "bolt://localhost:8080";
    private final String username = "neo4j";
    private final String password = "123456";


    public Neo4jDAO() {
        this.driver = GraphDatabase.driver(this.uriDb, AuthTokens.basic(this.username, this.password));
        this.session = this.driver.session();
    }

    public void insertActor(String id, String name) {
        String query;
        query = "CREATE (n:Actor {id: \"%s\", name: \"%s\"})";
        query = String.format(query, id, name);
        this.session.run(query);
        return;
    }

    public void deleteActor(String id, String name) {
        String query;
        query = "MATCH (n:Actor {id: \"%s\", name: \"%s\"})";
        query = String.format(query, id, name);
        this.session.run(query);
        query = "Delete n";
        this.session.run(query);
        return;
    }
    public void insertMovie(String id, String name) {
        String query;
        query = "CREATE (n:Movie {id: \"%s\", name: \"%s\"})";
        query = String.format(query, id, name);
        this.session.run(query);
        return;
    }

    public void deleteMovie(String id, String name) {
        String query;
        query = "MATCH (n:Movie {id: \"%s\", name: \"%s\"})";
        query = String.format(query, id, name);
        this.session.run(query);
        query = "Delete n";
        this.session.run(query);
        return;
    }
}
