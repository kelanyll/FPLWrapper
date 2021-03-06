package core;

import java.util.List;
import java.util.function.Predicate;

public final class Util {
	private Util() {
	}

	public static <T> T findInList(List<T> list, Predicate<T> condition) {
		return list
			.stream()
			.filter(condition)
			.findAny()
			.orElse(null);
	}

	public static String sanitiseString(String string) {
		return string.toLowerCase().replaceAll("[^a-z]", "");
	}
}
