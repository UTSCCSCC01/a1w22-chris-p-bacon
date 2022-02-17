package ca.utoronto.utm.mcs;

import com.sun.net.httpserver.HttpServer;
import dagger.Module;
import dagger.Provides;

import java.io.IOException;
import java.net.InetSocketAddress;

@Module
public class ServerModule {

    @Provides
    public HttpServer provideHttpServer() {
        int port = 8080;
        try {
            return HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0);
        }
        catch (IOException ioException) {
            // TODO: Handle in a better way
            return null;
        }
    }
}
