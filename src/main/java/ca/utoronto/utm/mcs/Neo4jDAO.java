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

    public void insertPokemon(String name, String pid, String description, String type1, String type2) {
        String query;
        query = "CREATE (n:pokemon {name: \"%s\", pid: \"%s\", description: \"%s\", type1: \"%s\", type2: \"%s\"})";
        query = String.format(query, name, pid, description, type1, type2);
        this.driver.session().run(query);
        return;
    }


}
