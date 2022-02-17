package ca.utoronto.utm.mcs;

import dagger.Module;
import dagger.Provides;

// Module is an interface used to generate the injector

@Module
public class ReqHandlerModule {

    @Provides
    public Neo4jDAO provideNeo4jDAO(){
        return DaggerNeo4jDAOComponent.create().buildNeo4jDAO();
    }

}
