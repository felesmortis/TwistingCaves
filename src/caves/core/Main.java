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
		Node testnode = new Node();
		setSelectedNode(testnode);
	}
	public void setSelectedNode(Node node) {
		exitdisplay = node.getExits();
		itemSelected = true;
	}
	public static void main(String[] args) {
		CaveUtil.initDirections();
		Main main = new Main();

	}
	public void createframe() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(1000, 750);
		this.setResizable(false);
		setVisible(true);
	}
	@Override
	public void run() {
		while(true) {
			repaint();
		}
	}
	private void drawUniv(Graphics g, BufferedImage bimg) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(1, 1, bimg.getWidth()-1, bimg.getHeight()-1);
		g2d.setColor(Color.BLACK);
		g2d.drawRect(0, 0, bimg.getWidth(), bimg.getHeight());
	}
	private void drawTop() {
		Graphics2D top2D = (Graphics2D)top;
		drawUniv(top, bimgTop);
		top2D.setColor(Color.black);
		if(itemSelected) {
			System.out.println("sup");
			for(int i = 0; i < exitdisplay.size(); i++) {
				int dir = Exit.exits.get(exitdisplay.get(i)).dir;
				byte or = CaveUtil.get(dir, 0), 
						or2 = CaveUtil.get(dir, 1), 
						theta = CaveUtil.get(dir, 2), 
						theta2 = CaveUtil.get(dir, 3);
				int cx, cy;
				//cx = (int) (13 * y * Math.cos((360/16) * x));
				//cy = (int) (13 * y * Math.sin((360/16) * x));
				//top2D.fillRect(1, 1, bimgTop.getWidth(), bimgTop.getHeight());
				top2D.setColor(Color.red);
				//top2D.fillArc(cx + (bimgTop.getWidth()/2), cy + (bimgTop.getHeight()), 1, 1, 0, 360);
				//System.out.println(cx);
				//System.out.println(cy);
			}
		}
	}
	private void drawBot() {
		Graphics2D bot2D = (Graphics2D)bot;
		drawUniv(bot, bimgBot);
	}
	private void drawNorth() {
		Graphics2D north2D = (Graphics2D)north;
		drawUniv(north, bimgNorth);
	}
	private void drawSouth() {
		Graphics2D south2D = (Graphics2D)south;
		drawUniv(south, bimgSouth);
	}
	private void drawEast() {
		Graphics2D east2D = (Graphics2D)east;
		drawUniv(east, bimgEast);
	}
	private void drawWest() {
		Graphics2D west2D = (Graphics2D)west;
		drawUniv(west, bimgWest);
	}
	private void drawMap() {
		Graphics2D map2D = (Graphics2D)map;
		drawUniv(map, bimgMap);
	}
	@Override
	public void paint(Graphics g) {
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
