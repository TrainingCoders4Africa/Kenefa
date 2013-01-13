package c4a.kenefa.web;

import java.io.Serializable;

public class Doc implements Serializable {
	private String name;

	public Doc() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Doc(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
