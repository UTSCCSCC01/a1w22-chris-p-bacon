package ca.utoronto.utm.mcs;

import dagger.Module;
import dagger.Provides;
import io.github.cdimascio.dotenv.Dotenv;
import org.neo4j.driver.*;

@Module
public class Neo4jDAOModule {

    @Provides
    public Driver provideDriver(){
        // credentials given in the handout
        String username = "neo4j";
        String password = "123456";

        // This code is used to get the neo4j address, you must use this so that we can mark :)
        Dotenv dotenv = Dotenv.load();
        String addr = dotenv.get("NEO4J_ADDR");

        String dbUrl = String.format("bolt://%s:7687", addr);
        return GraphDatabase.driver(dbUrl, AuthTokens.basic(username, password));
    }

}
