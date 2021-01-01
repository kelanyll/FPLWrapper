package db;

import core.Club;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

@RegisterRowMapper(ClubMapper.class)
public interface ClubDAO {
	@SqlQuery("SELECT * FROM clubs WHERE id = ?")
	public Optional<Club> get(int id);

	@SqlQuery("SELECT * FROM clubs WHERE code = ?")
	public Optional<Club> getByCode(int code);

	@SqlQuery("SELECT * FROM clubs")
	public List<Club> getAll();

	@SqlUpdate("INSERT INTO clubs (code, name, id) VALUES (:code, :name, :id) ON CONFLICT (code) DO UPDATE SET " +
		"(name, id) = (EXCLUDED.name, EXCLUDED.id)")
	public void insert(@BindBean Club club);
}
