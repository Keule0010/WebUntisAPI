package de.keule.webuntis.response;

import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;

public class Klassen extends WebUntisDataWrapper<Klasse> {

	public Klassen(JSONObject json) {
		super(json, Klasse.class);
	}

	public Klasse getKlasse(String name, boolean ignoreCase) {
		for (Klasse kl : data) {
			if (ignoreCase && kl.getName().equalsIgnoreCase(name))
				return kl;
			else if (!ignoreCase && kl.getName().equals(name))
				return kl;
		}
		return null;
	}

	public void sortByTeacher1() {
		data.sort(Comparator.comparing(Klasse::getTeacher1));
	}

	public void sortByTeatcher2() {
		data.sort(Comparator.comparing(Klasse::getTeacher2));
	}

	public List<Klasse> getKlassen() {
		return data;
	}
}
