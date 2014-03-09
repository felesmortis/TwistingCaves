package caves.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class CaveUtil {
	private static ArrayList<Integer> dir = new ArrayList<Integer>();
	
	/*
	 * Full
	 */
	public static final int PLACE_OR = 0, PLACE_OR2 = 1, PLACE_THETA = 2, PLACE_THETA2 = 3;
	/*
	 * Pairs
	 */
	public static final int PAIR_OR = 0, PAIR_THETA = 1;
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
			//loop through the Second Origin (for each secondary origin)
			for(byte o2 = 1; o2 < 5; o2++)
				//loop throught the Second origin's top row or bottom row keeping track of where we are in ours.
				for(byte t2 = 0; t2 < 4; t2++) {//+9
					dir.add(fromFullCoords((byte)(o + ROW_2), (byte)(o2+ROW_2), t, (byte)(t2 + (o == 0 ? 0 : 8))));
					t++;
				}
		}
		for(byte o = 1; o < 5; o++) {
			dir.add(fromCoords(o, (byte)0));
			for(byte t = 0; t < 8; t++)
				dir.add(fromCoords((byte)(o + ROW_1), t));
			byte t = 4;
			//if we're the priority secondary origins
			if(o == 1 || o == 3) {
				byte o2 = 2;
				//loop through their thetas if we are at the already done primary 5 origin
				//skip over
				for(byte t2 = 15; t2 >= 4; t2 -=(t2 <= 12 && t2 > 7) ? 5 : 1) {
					dir.add(fromFullCoords((byte)(o+ROW_2), (byte)(o2+ROW_2), t, t2));
					if(t == 7) {
						t+=5;
						o2++;
					}
					else
						t++;
				}
			}

		}
	}
	public static void testCoords() {
		//System.out.println(dir.size());
		for(int i : dir) {
			System.out.println(String.format("ID: %9s,\tO: %9s,\tOrigin: %9s,\tRow: %9s,\tTheta: %9s,\tO2: %9s,\tOrigin2: %9s,\tRow2: %9s,\tTheta2: %9s",
					i, getFull(i, 0), (int)(getFull(i, 0) % 10), (int)(getFull(i, 0) * .1), getFull(i, 2),
					getFull(i, 1), (int)(getFull(i, 1) % 10), (int)(getFull(i, 1) * .1), getFull(i, 3)));
		}
		//System.out.println(getPair(fromCoordPair((byte)1,(byte)1), 0));
		//System.out.println(getPair(fromCoordPair((byte)1,(byte)1), 1));
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
		return fromFullCoords(oR, (byte)-1, theta, (byte)-1);
	}
	public static int fromFullCoords(byte oR, byte oR2, byte theta, byte theta2) {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put(oR);
		bb.put(oR2);
		bb.put(theta);
		bb.put(theta2);
		return bb.getInt(0);
	}
	public static byte getFull(int dir, int place) {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.putInt(0, dir);
		return bb.get(place);
	}
	public static short fromCoordPair(byte oR, byte theta) {
		ByteBuffer bb = ByteBuffer.allocate(2);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put(oR);
		bb.put(theta);
		return bb.getShort(0);
	}
	public static byte getPair(short dir, int place) {
		ByteBuffer bb = ByteBuffer.allocate(2);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.putShort(0, dir);
		return bb.get(place);
	}
	
}
