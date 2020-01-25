package dropwizard;

import dao.DAOInitialiser;
import dao.DAOInitialiserImpl;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import resources.MyTeamResource;

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
        DAOInitialiser daoInitialiser = new DAOInitialiserImpl();
        environment.jersey().register(new MyTeamResource(daoInitialiser));
        environment.jersey().register(new DropwizardExceptionMapper());
    }
}
