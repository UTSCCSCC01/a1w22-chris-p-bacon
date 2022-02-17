package ca.utoronto.utm.mcs;

import dagger.Component;
import javax.inject.Singleton;

// TODO Uncomment The Line Below When You Have Implemented ServerModule


@Singleton
@Component(modules = ServerModule.class)
public interface ServerComponent {

	public Server buildServer();
}
