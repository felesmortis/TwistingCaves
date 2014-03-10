package caves.core;

import javax.swing.*;

import caves.nodes.Exit;
import caves.nodes.Node;
import caves.util.CaveUtil;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
public class Main extends JFrame implements Runnable {
	Thread t;
	ArrayList<Integer> exitdisplay;
	ArrayList<Short> exitTop, exitBot, exitNorth, exitSouth, exitEast, exitWest;
	boolean itemSelected = false;
	Graphics top, bot, north, south, east, west, map;
	BufferedImage bimgTop, bimgBot, bimgNorth, bimgSouth, bimgEast, bimgWest, bimgMap;
	final int winWidth = 250, winHeight = 270;

	public Main() {
		t = new Thread();
		createframe();
		bimgTop = new BufferedImage(winWidth, winHeight, BufferedImage.TYPE_INT_ARGB);
		bimgBot = new BufferedImage(winWidth, winHeight, BufferedImage.TYPE_INT_ARGB);
		bimgNorth = new BufferedImage(winWidth, winHeight, BufferedImage.TYPE_INT_ARGB);
		bimgSouth = new BufferedImage(winWidth, winHeight, BufferedImage.TYPE_INT_ARGB);
		bimgEast = new BufferedImage(winWidth, winHeight, BufferedImage.TYPE_INT_ARGB);
		bimgWest = new BufferedImage(winWidth, winHeight, BufferedImage.TYPE_INT_ARGB);
		bimgMap = new BufferedImage(3*winWidth, 2*winHeight, BufferedImage.TYPE_INT_ARGB);
		top = bimgTop.createGraphics();
		bot = bimgBot.createGraphics();
		north = bimgNorth.createGraphics();
		south = bimgSouth.createGraphics();
		east = bimgEast.createGraphics();
		west = bimgWest.createGraphics();
		map = bimgMap.createGraphics();
		t.start();
		getTestNode();
	}
	public void getTestNode() {
		Node testnode = new Node(10);
		setSelectedNode(testnode);
	}
	public void setSelectedNode(Node node) {
		exitdisplay = new ArrayList<Integer>();
		for(int i : node.getExits()) {
			exitdisplay.add(Exit.exits.get(i).dir);
		}
		exitTop = new ArrayList<Short>();
		populate(0, exitTop);
		exitNorth = new ArrayList<Short>();
		populate(1, exitNorth);
		exitEast = new ArrayList<Short>();
		populate(2, exitEast);
		exitSouth = new ArrayList<Short>();
		populate(3, exitSouth);
		exitWest = new ArrayList<Short>();
		populate(4, exitWest);
		exitBot = new ArrayList<Short>();
		populate(5, exitBot);
		itemSelected = true;
	}
	private void populate(int dir, ArrayList<Short> arr) {
		for(int i : exitdisplay) {
			if(Math.floor((CaveUtil.getFull(i, CaveUtil.PLACE_OR) % 10)) == dir) {
				//System.out.println(CaveUtil.getFull(i, CaveUtil.PLACE_OR) + " " + CaveUtil.getFull(i, CaveUtil.PLACE_THETA));
				byte or = CaveUtil.getFull(i, CaveUtil.PLACE_OR), t = CaveUtil.getFull(i, CaveUtil.PLACE_THETA);
				arr.add(CaveUtil.fromCoordPair(or, t));
//				System.out.println(or + " " + t);
//				System.out.println("asdf:" +i);
				//System.out.println(arr.get(arr.size()-1));
			} else if(Math.floor((CaveUtil.getFull(i, CaveUtil.PLACE_OR2) % 10)) == dir) {
				byte or = CaveUtil.getFull(i, CaveUtil.PLACE_OR2), t = CaveUtil.getFull(i, CaveUtil.PLACE_THETA2);
				arr.add(CaveUtil.fromCoordPair(or, t));
				//System.out.println(arr.get(arr.size()-1));
//				System.out.println(or + " " + t);
//				System.out.println("asdf:" +i);
			}
		}
	}
	public static void main(String[] args) {
		CaveUtil.initDirections();
		//CaveUtil.testCoords();
		Main main = new Main();

	}
	public void createframe() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(1000, 780);
		this.setResizable(false);
		setVisible(true);
	}
	@Override
	public void run() {
		while(true) {
			repaint();
		}
	}
	private void drawUniv(Graphics g, BufferedImage bimg, ArrayList<Short> exits) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(1, 1, bimg.getWidth()-1, bimg.getHeight()-1);
		g2d.setColor(Color.BLACK);
		g2d.drawRect(0, 0, bimg.getWidth(), bimg.getHeight());
		//g2d.fillArc(0 + (bimg.getWidth()/2), 0 + (bimg.getHeight()/2), 50, 50, 0, 360);
		if(exits == null || exits.size() == 0) {
			g2d.drawString("Nothing to Report", (bimg.getWidth()/2), (bimg.getHeight()/2));
		} else if(bimg != bimgMap && itemSelected) {
			for(int i = 0; i < exits.size(); i++) {
				short dir = exits.get(i);
				byte or = CaveUtil.getPair(dir, CaveUtil.PAIR_OR), t = CaveUtil.getPair(dir, CaveUtil.PAIR_THETA);
				byte r = (byte)(or * .10);
				byte o = (byte)(or % 10);
				//System.out.println(or % 10);
				int cx, cy;
				int d = 8;
				int anginc = 1;
				switch(r) {
				case 1:
					anginc = 8;
					break;
				case 2:
					anginc = 16;
					break;
				}
				if(o == 0 || o  == 5) {
					anginc = 16;
				}
				float theta = (360 / anginc) * t;//(360/((o == 0 || o == 5 || r == 2) ? 16 : 8)) * t;
				//if(anginc == 16)
					//System.out.println(anginc + " " + theta + " " + t);
				cx = (int) (50 * (r * Math.cos((Math.PI/180)*theta)));
				cy = (int) (50 * (r * Math.sin((Math.PI/180)*theta)));
				/*if(anginc == 16) {
					System.out.println(Math.cos(theta) + " " + Math.sin(theta));
					System.out.println(theta + " " + t + "\n\t x: " + cx + " y: " + cy);
				}*/
				//System.out.println("cx: " + cx + " cy: " + cy + "\n\t o: " + or);
				g2d.setColor(Color.red);
				g2d.fillOval(cx + ((bimgTop.getWidth()/2) + (d/2)), cy + ((bimgTop.getHeight()/2) + (d/2)), d, d);
				//g2d.drawString(/*"" + or + */"" + t,cx + ((bimgTop.getWidth()/2) + (d/2)), cy + ((bimgTop.getHeight()/2) + (d/2)));//, d, d);
				//top2D.fillRect(1, 1, bimgTop.getWidth(), bimgTop.getHeight());
				//g2d.fillArc(0 + (bimgTop.getWidth()/2), 0 + (bimgTop.getHeight()/2), 50, 50, 0, 360);
				//System.out.println(cx);
				//System.out.println(cy);
			}
		}

	}
	private void drawTop() {
		drawUniv(top, bimgTop, exitTop);
		Graphics2D top2D = (Graphics2D)top;
		top2D.setColor(Color.black);

		//top.setColor(Color.red);
		//top.fillArc(0 + (bimgTop.getWidth()/2), 0 + (bimgTop.getHeight()/2), 50, 50, 0, 360);
	}
	private void drawBot() {
		Graphics2D bot2D = (Graphics2D)bot;
		drawUniv(bot, bimgBot, exitBot);
	}
	private void drawNorth() {
		Graphics2D north2D = (Graphics2D)north;
		drawUniv(north, bimgNorth, exitNorth);
	}
	private void drawSouth() {
		Graphics2D south2D = (Graphics2D)south;
		drawUniv(south, bimgSouth, exitSouth);
	}
	private void drawEast() {
		Graphics2D east2D = (Graphics2D)east;
		drawUniv(east, bimgEast, exitEast);
	}
	private void drawWest() {
		Graphics2D west2D = (Graphics2D)west;
		drawUniv(west, bimgWest, exitWest);
	}
	private void drawMap() {
		Graphics2D map2D = (Graphics2D)map;
		drawUniv(map, bimgMap, null);
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		drawTop();
		drawBot();
		drawNorth();
		drawSouth();
		drawEast();
		drawWest();
		drawMap();
		g2d.drawImage(bimgMap, bimgTop.getWidth(), 0, bimgMap.getWidth(), bimgMap.getHeight(), this);
		g2d.drawImage(bimgTop, 0, 0, bimgTop.getWidth(), bimgTop.getHeight(), this);
		g2d.drawImage(bimgBot, 0, bimgTop.getHeight(), bimgBot.getWidth(), bimgBot.getHeight(), this);
		g2d.drawImage(bimgNorth, 0, bimgTop.getHeight() + bimgBot.getWidth(), bimgNorth.getWidth(), bimgNorth.getHeight(), this);
		g2d.drawImage(bimgEast, bimgNorth.getWidth(), bimgTop.getHeight() + bimgBot.getWidth(), bimgEast.getWidth(), bimgEast.getHeight(), this);
		g2d.drawImage(bimgSouth, bimgNorth.getWidth() + bimgEast.getWidth(), bimgTop.getHeight() + bimgBot.getWidth(), bimgSouth.getWidth(), bimgSouth.getHeight(), this);
		g2d.drawImage(bimgWest, bimgNorth.getWidth() + bimgEast.getWidth() + bimgSouth.getWidth(), bimgTop.getHeight() + bimgBot.getWidth(), bimgWest.getWidth(), bimgWest.getHeight(), this);
	}
}
