package caves.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class CaveUtil {
	private static ArrayList<Integer> dir = new ArrayList<Integer>();
	public static final int PLACE_OR = 0, PLACE_OR2 = 1, PLACE_THETA = 2, PLACE_THETA2 = 3;
	public static final byte ROW_1 = 10;
	public static final byte ROW_2 = 20;
	//private static int nextID = 0;

	//	public static String getDirection(int ID) {
	//		return dir.get(ID);
	//	}
	/**
	 * Initialize Directions
	 * Holds 4 bytes for the directions in a single Integer
	 * Each Origin is held in a byte of 0-5
	 * If the point is in a row further out, we multiply by 10 or add 10 * row;
	 * For row 2, each point resides in 2 Origins
	 * Origins 0, 1, 3, and 5 take priority in that order.
	 * This means the first byte o2 will be 0
	 */
	public static void initDirections() {
		for(byte o = 0; o < 6; o+=5) {
			dir.add(fromCoords(o, (byte)0));
			for(byte t = 0; t < 16; t++)
				dir.add(fromCoords((byte)(o + ROW_1), t));
			byte t = 0;
			for(byte o2 = 1; o2 < 5; o2++)
				for(byte t2 = 0; t2 < 4; t2++) {//+9
					dir.add(fromCoords((byte)(o + ROW_2), (byte)(o2+ROW_2), t, (byte)(t + o == 0 ? 0 : 9)));
					t++;
				}
		}
		for(byte o = 1; o < 5; o++) {
			dir.add(fromCoords(o, (byte)0));
			for(byte t = 0; t < 8; t++)
				dir.add(fromCoords((byte)(o + ROW_1), t));
			if(o == 1) {
				
			} else if (o == 3) {
				
			}
		}

	}
	public static boolean intArrContains(int[] arr, int num){
		for(int i : arr)
			if(i == num)
				return true;
		return false;
	}
	public static ArrayList<Integer> getDirections() {
		return dir;
	}
	public static int dirCount(){
		return dir.size();
	}
	public static int fromCoords(byte oR, byte theta) {
		return fromCoords(oR, (byte)0, theta, (byte)0);
	}
	public static int fromCoords(byte oR, byte oR2, byte theta, byte theta2) {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put(oR);
		bb.put(oR2);
		bb.put(theta);
		bb.put(theta2);
		return bb.getInt(0);
	}
	public static byte get(int dir, int place) {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.putInt(0, dir);
		return bb.get(place);
	}
}
