package caves.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class CaveUtil {
	private static ArrayList<Short> dir = new ArrayList<Short>();
	//private static int nextID = 0;
	
//	public static String getDirection(int ID) {
//		return dir.get(ID);
//	}
	public static void initDirections() {
		for(byte y = 0; y < 9; y++)
			for(byte x = 0; y < 16; y++) {
				ByteBuffer bb = ByteBuffer.allocate(2);
				bb.order(ByteOrder.LITTLE_ENDIAN);
				bb.put(y);
				bb.put(x);
				dir.add(bb.getShort(0));
			}
	}
	public static boolean intArrContains(int[] arr, int num){
		for(int i : arr)
			if(i == num)
				return true;
		return false;
	}
	public static ArrayList<Short> getDirections() {
		return dir;
	}
	public static int dirCount(){
		return dir.size();
	}
}
