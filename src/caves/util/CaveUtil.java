package caves.util;

import caves.nodes.Exit;

import com.google.common.collect.HashBiMap;

public class CaveUtil {
	private static HashBiMap<Integer, String> dir = HashBiMap.create();
	private static int nextID = 0;
	public static int getDirection(String cardinal) {
		return dir.inverse().get(cardinal);
	}
	public static String getDirection(int ID) {
		return dir.get(ID);
	}
	public static void initDirections() {
		adir("N");
		adir("S");
		adir("E");
		adir("W");
		adir("U");
		adir("D");
		for(int i = 0; i < 4; i++) {
			//TODO: POPULATE LATER
		}
	}
	public static void adir(String direction) {
		dir.put(nextID++, direction);
	}
	public static boolean intArrContains(int[] arr, int num){
		for(int i : arr)
			if(i == num)
				return true;
		return false;
	}
	public static int dirCount(){
		return nextID;
	}
}
