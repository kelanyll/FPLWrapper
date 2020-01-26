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
import util.SimpleUrlStreamSource;
import util.UrlStreamSource;

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
        UrlStreamSource urlStreamSource = new SimpleUrlStreamSource();
        FplUtilities fplUtilities = new FplUtilities(urlStreamSource);
        DAOInitialiser daoInitialiser = new DAOInitialiserImpl(urlStreamSource, fplUtilities);
        environment.jersey().register(new MyTeamResource(urlStreamSource, fplUtilities, daoInitialiser));
        environment.jersey().register(new DropwizardExceptionMapper());
    }
}
