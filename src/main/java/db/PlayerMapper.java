package db;

import core.Player;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerMapper implements RowMapper<Player> {
	@Override
	public Player map(ResultSet rs, StatementContext ctx) throws SQLException {
		return new Player(rs.getString("first_name"), rs.getString("second_name"), rs.getInt("id"), rs.getInt(
			"position_id"), rs.getInt("club_code"), rs.getString("form"), rs.getInt("price"), rs.getInt(
				"gameweek_points"), rs.getInt("total_points"), rs.getString("selected_by_percent"), rs.getString(
					"influence"), rs.getString("creativity"), rs.getString("threat"), rs.getString("ict_index"));
	}
}
