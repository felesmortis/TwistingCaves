package caves.nodes;
import java.util.ArrayList;

import caves.alive.*;
public class Location {
	private ArrayList<LivingEntity> contains;
	public Location() {
		
	}
	public boolean isEmpty() {
		return contains.isEmpty();
	}
	public ArrayList<LivingEntity> getContains() {
		return contains;
	}
	public ArrayList<Person> getPlayers() {
		ArrayList<Person> players = new ArrayList<Person>();
		for(int i = 0; i < contains.size(); i++) {
			if(contains.get(i) instanceof Person) {
				players.add((Person)contains.get(i));
			}
		}
		return players;
	}
}
