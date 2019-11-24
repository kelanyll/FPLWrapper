package dropwizard;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.hibernate.validator.constraints.NotEmpty;

public class FPLWrapperConfiguration extends Configuration {
    /**
     * The default email address used to authenticate requests
     * to the FPL API that aren't user specific
     */
    @NotEmpty
    private String email;

    /**
     * The default password used to authenticate requests to
     * the FPL API that aren't user specific
     */
    @NotEmpty
    private String password;

    @JsonProperty
    public String getEmail() {
        return email;
    }

    @JsonProperty
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;
}
