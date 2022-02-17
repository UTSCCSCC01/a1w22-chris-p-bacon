package ca.utoronto.utm.mcs;

import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = ReqHandlerModule.class)
public interface ReqHandlerComponent {
    public ReqHandler buildHandler();
}
