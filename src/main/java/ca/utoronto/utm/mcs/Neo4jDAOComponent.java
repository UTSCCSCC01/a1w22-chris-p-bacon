package ca.utoronto.utm.mcs;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = Neo4jDAOModule.class)
public interface Neo4jDAOComponent {
    public Neo4jDAO buildNeo4jDAO();
}
