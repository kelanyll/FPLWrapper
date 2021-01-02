import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class FPLWrapperConfiguration extends Configuration {
	@JsonProperty("swagger")
	public SwaggerBundleConfiguration swaggerBundleConfiguration;

	private DataSourceFactory database = new DataSourceFactory();

	@JsonProperty("database")
	public void setDataSourceFactory(DataSourceFactory factory) {
		this.database = factory;
	}

	@JsonProperty("database")
	public DataSourceFactory getDataSourceFactory() {
		return database;
	}
}
