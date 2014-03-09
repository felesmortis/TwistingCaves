package caves.nodes;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import caves.util.CaveUtil;

public class Node extends Location {
	public static Random r = new Random((new Date()).getTime());
	public static ArrayList<Node> nodes = new ArrayList<Node>();
	//holds direction id and the destination node
	//private HashMap<Integer, Passage> paths = new HashMap<Integer, Node>();
	private ArrayList<Integer> exits = new ArrayList<Integer>();
	public Node() {
		this(4);
	}
	public Node(int numpaths) {
		this(numpaths, genRanDirs(numpaths));
	}
	public Node(int numpaths, int[] dirs) {
		super();
		addExits(numpaths, dirs);
		nodes.add(this);
	}
	public void addExits(int numpaths, int[] dirs) {
		if(dirs != null && numpaths >= dirs.length) {
			for(int i = 0; i < numpaths; i++) {
				Exit exit = i < dirs.length ? new Exit(dirs[i], this) : new Exit(this);
				setExit(exit);
			}
		}
	}
	public void setExit(Exit exit) {
		exits.add(Exit.addExit(exit));
	}
	public ArrayList<Integer> getExits() {
		return exits;
	}
	public static int[] genRanDirs(int num) {
		int[] dirs = new int[num];
		for(int i = 0; i < num; i++) {
			boolean dupe = false;
			do {
				dupe = false;
				int ranDir;
				ranDir = r.nextInt(CaveUtil.getDirections().size());
				for(int in : dirs){
					if(ranDir == in){
						dupe = true;
						break;
					}
				}
				dirs[i] = CaveUtil.getDirections().get(ranDir);
			} while(dupe);
		}
		return dirs;
	}
	public static Node randomNode() {
		return nodes.get(r.nextInt(nodes.size() - 1));
	}
	public boolean contains(Node node) {
		for(int e : exits){
			if(Exit.exits.get(e).node == node)
				return true;
		}
		return false;
	}
	public boolean contains(int dir) {
		for(int e : exits){
			if(Exit.exits.get(e).dir == dir)
				return true;
		}
		return false;
	}
}