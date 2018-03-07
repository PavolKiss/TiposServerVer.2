package sk.akademiasovy.tipos.server;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import sk.akademiasovy.tipos.server.resources.Bets;
import sk.akademiasovy.tipos.server.resources.Draws;
import sk.akademiasovy.tipos.server.resources.Login;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class TiposApplication extends Application<TiposConfiguration> {

    public static void main(final String[] args) throws Exception {
        new TiposApplication().run(args);
    }

    @Override
    public String getName() {
        return "Tipos";
    }

    @Override
    public void initialize(final Bootstrap<TiposConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final TiposConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
        environment.jersey().register( new Login() );
        environment.jersey().register( new Bets() );
        environment.jersey().register( new Draws() );

        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);
// Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }

}
