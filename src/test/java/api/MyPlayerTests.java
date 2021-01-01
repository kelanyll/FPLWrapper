package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public class MyPlayerTests {
	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

	@Test
	public void serializesToJSON() throws Exception {
		final MyPlayer myPlayer = new MyPlayer(
			"Yll Kelani",
			"Chelsea",
			"midfielder",
			true,
			true,
			false,
			"Spurs",
			true
		);

		final String expected = MAPPER.writeValueAsString(
			MAPPER.readValue(fixture("fixtures/myPlayer.json"), MyPlayer.class));

		assertThat(MAPPER.writeValueAsString(myPlayer)).isEqualTo(expected);
	}
}
