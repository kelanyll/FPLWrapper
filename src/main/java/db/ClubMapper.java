package db;

import core.Club;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClubMapper implements RowMapper<Club> {
	@Override
	public Club map(ResultSet rs, StatementContext ctx) throws SQLException {
		return new Club(rs.getString("name"), rs.getInt("code"), rs.getInt("id"));
	}
}
