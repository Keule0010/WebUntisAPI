package de.keule.webuntis.response;

import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;

public class Rooms extends WebUntisDataWrapper<Room> {

	public Rooms(JSONObject json) {
		super(json, Room.class);
	}

	public void sortByBuilding() {
		data.sort(Comparator.comparing(Room::getBuilding));
	}

	public Room getRoom(int i) {
		return data.get(i);
	}

	public List<Room> getRooms() {
		return data;
	}
}
