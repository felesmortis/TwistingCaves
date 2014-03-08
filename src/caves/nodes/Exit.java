package caves.nodes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import caves.util.CaveUtil;

public class Exit extends Location {
	//public static HashMap(Integer, )
	public static ArrayList<Exit> exits = new ArrayList<Exit>();
	public static HashMap<Integer, Integer> linked;
	public static Exit deadEnd = new Exit((short) -1, null);
	public int dir;
	public Node node;
	static Random r = new Random(new Date().getTime());
	public Exit() {
		this(Node.randomNode());
	}
	public Exit(Node dest) {
		this(allowableDir(dest), dest);
	} 
	public Exit(int dir, Node dest){
		super();
		this.dir = dir;
		this.node = dest;
	}
	public static int addExit(Exit e) {
		exits.add(e);
		return exits.indexOf(e);
	}
	public static int allowableDir(Node dest) {
		int ranDir;
		do {
			ranDir = r.nextInt(CaveUtil.dirCount() - 1);
		} while(dest.contains(ranDir) && !CaveUtil.getDirections().contains(ranDir));
		return ranDir;
	}
	public static Exit add(Exit e) {
		if(!exits.contains(e)) {
			exits.add(e);
			linked.put(exits.indexOf(e), - 1);
		}
		int id = linked.get(exits.indexOf(e));
		return id == -1 ? deadEnd : exits.get(id);
	}
	public static boolean link(Exit e1, Exit e2) {
		if(exits.contains(e1) && exits.contains(e2)) {
			int i1 = exits.indexOf(e1), i2 = exits.indexOf(e2);
			linked.put(i1, i2);
			linked.put(i2, i1);
			return true;
		}
		return false;
	}
}