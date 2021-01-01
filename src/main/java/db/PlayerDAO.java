package db;

import core.Player;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

@RegisterRowMapper(PlayerMapper.class)
public interface PlayerDAO {
	@SqlQuery("SELECT * FROM players WHERE id = ?")
	Optional<Player> get(int id);

	@SqlQuery("SELECT * FROM players WHERE first_name || ' ' || second_name = ?")
	Optional<Player> getByName(String name);

	@SqlQuery("SELECT * FROM players")
	List<Player> getAll();

	@SqlUpdate("INSERT INTO players VALUES (:id, :firstName, :secondName, :positionId, :clubCode, :form, :price, " +
		":gameweekPoints, :totalPoints, :selectedByPercent, :influence, :creativity, :threat, :ictIndex) " +
		"ON CONFLICT (id) DO UPDATE SET (id, first_name, second_name, position_id, club_code, form, price, " +
		"gameweek_points, total_points, selected_by_percent, influence, creativity, threat, ict_index) = (EXCLUDED.id," +
		"EXCLUDED.first_name, EXCLUDED.second_name, EXCLUDED.position_id, EXCLUDED.club_code, EXCLUDED.form, EXCLUDED" +
		".price, EXCLUDED.gameweek_points, EXCLUDED.total_points, EXCLUDED.selected_by_percent, EXCLUDED.influence, " +
		"EXCLUDED.creativity, EXCLUDED.threat, EXCLUDED.ict_index)")
	void insert(@BindBean Player player);
}
