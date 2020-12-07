package entities;

public class Club {
	private String name;
	private int code;
	private int id;

	public Club(String name, int code, int id) {
		this.name = name;
		this.code = code;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getCode() {
		return code;
	}

	public int getId() {
		return id;
	}
}
