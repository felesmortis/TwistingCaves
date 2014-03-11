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
		this(numpaths > CaveUtil.getDirections().size() ? CaveUtil.getDirections().size() : numpaths, genRanDirs(numpaths > CaveUtil.getDirections().size() ? CaveUtil.getDirections().size() : numpaths));
	}
	public Node(boolean full) {
		this(CaveUtil.dirCount(), CaveUtil.toIntArray(CaveUtil.getDirections()));
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
		ArrayList<Integer> temp = new ArrayList<Integer>(CaveUtil.getDirections());
		int[] dirs = new int[num];
		for(int i = 0; i < num; i++) {
			int max = temp.size() - 1;
			if(max >= 0) {
				int ranDir;
				ranDir = max > 0 ? r.nextInt(max) : 0;
				dirs[i] = temp.get(ranDir);
				temp.remove(ranDir);
			}
			else 
				break;
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