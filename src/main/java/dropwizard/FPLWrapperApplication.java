package dropwizard;

import dao.DAOInitialiser;
import dao.DAOInitialiserImpl;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import resources.MyTeamResource;
import util.FplUtilities;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.http.HttpClient;

public class FPLWrapperApplication extends Application<FPLWrapperConfiguration> {
    public static void main(String[] args) throws Exception {
        new FPLWrapperApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<FPLWrapperConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<FPLWrapperConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(FPLWrapperConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public void run(FPLWrapperConfiguration configuration,
                    Environment environment) {
        HttpClient httpClient = HttpClient.newBuilder()
                .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL))
                .build();
        FplUtilities fplUtilities = new FplUtilities(httpClient);
        DAOInitialiser daoInitialiser = new DAOInitialiserImpl(httpClient, fplUtilities);

        final FplHealthCheck fplHealthCheck = new FplHealthCheck(httpClient);
        environment.healthChecks().register("FPL", fplHealthCheck);

        environment.jersey().register(new DropwizardExceptionMapper());
        environment.jersey().register(new MyTeamResource(httpClient, fplUtilities, daoInitialiser));
    }
}
