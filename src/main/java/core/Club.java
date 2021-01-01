package core;

public class Club {
	private String name;
	private int code;
	private int id;

	// Jackson
	private Club() {}

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

	public void setName(String name) {
		this.name = name;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setId(int id) {
		this.id = id;
	}
}
