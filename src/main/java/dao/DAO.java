package dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

	Optional<T> get(int id);

	List<T> getAll();

	void save(T t);
}
