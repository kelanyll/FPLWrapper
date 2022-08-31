import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import db.*;
import exceptions.DropwizardExceptionMapper;
import health.FplHealthCheck;
import client.HttpUtil;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.jdbi.v3.core.Jdbi;
import resources.MyTeamResource;
import resources.PlayerResource;
import client.FplUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
		bootstrap.setConfigurationSourceProvider(
			new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
				new EnvironmentVariableSubstitutor(false)
			)
		);
	}

	@Override
	public void run(FPLWrapperConfiguration configuration,
	                Environment environment) {
		environment.getObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
			.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

		final JdbiFactory factory = new JdbiFactory();
		final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");
		final ClubDAO clubDao = jdbi.onDemand(ClubDAO.class);
		final PlayerDAO playerDAO = jdbi.onDemand(PlayerDAO.class);

		HttpUtil httpUtil = new HttpUtil();
		FplUtil fplUtil = new FplUtil(httpUtil, environment.getObjectMapper());

		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(new BootstrapWriter(httpUtil, environment.getObjectMapper(),
			clubDao, playerDAO
			), 0, 12, TimeUnit.HOURS);

		final FplHealthCheck fplHealthCheck = new FplHealthCheck(httpUtil);
		environment.healthChecks().register("FPL", fplHealthCheck);

		environment.jersey().register(new DropwizardExceptionMapper());
		environment.jersey().register(new MyTeamResource(clubDao, playerDAO, fplUtil));
		environment.jersey().register(new PlayerResource(clubDao, playerDAO, httpUtil, environment.getObjectMapper()));
	}
}
