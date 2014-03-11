package caves.core;

import javax.swing.*;

import javax.swing.Timer;

import caves.nodes.Exit;
import caves.nodes.Node;
import caves.util.CaveUtil;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
public class Main extends JFrame implements Runnable {
	Thread t;
	ArrayList<Integer> exitdisplay;
	ArrayList<ArrayList<Short>> exits = new ArrayList<ArrayList<Short>>();// exitTop, exitBot, exitNorth, exitSouth, exitEast, exitWest;
	boolean itemSelected = false;
	/*
	 * Top
	 * Bot
	 * North
	 * South
	 * East
	 * West
	 * Map
	 */
	BufferedImage[] bimgs = new BufferedImage[7];
	BufferedImage mainbuffer;
	Graphics[] gs = new Graphics[7];
	Graphics buffer;
	Timer time;
	final int winWidth = 250, winHeight = 270;

	public Main() {
		t = new Thread(this);
		createframe();
		for(int i = 0; i < 6; i++) {
			bimgs[i] = new BufferedImage(winWidth, winHeight, BufferedImage.TYPE_INT_ARGB);
		}
		bimgs[6] = new BufferedImage(3*winWidth, 2*winHeight, BufferedImage.TYPE_INT_ARGB);
		mainbuffer = new BufferedImage(4* winWidth, 3* winHeight, BufferedImage.TYPE_INT_ARGB);
		buffer = mainbuffer.createGraphics();
		for(int i = 0; i < 7; i++) {
			gs[i] = bimgs[i].createGraphics();
		}
		ActionListener draw = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				drawExits();
				repaint();

			}

		};
		t.start();
		time = new Timer(10, draw);
		time.start();
		getTestNode();
	}
	public void getTestNode() {
		Node testnode = new Node(true);
		setSelectedNode(testnode);
	}
	public void setSelectedNode(Node node) {
		exitdisplay = new ArrayList<Integer>();
		for(int i = 0; i < 7; i++){
			exits.add(i, null);
		}
		for(int i : node.getExits()) {
			exitdisplay.add(Exit.exits.get(i).dir);
		}
		for(int i = 0; i < 6; i++) {
			exits.set(i, new ArrayList<Short>());
			populate(i, exits.get(i));
		}
		itemSelected = true;
	}
	private void populate(int dir, ArrayList<Short> arr) {
		for(int i : exitdisplay) {
			if(Math.floor((CaveUtil.getFull(i, CaveUtil.PLACE_OR) % 10)) == dir) {
				byte or = CaveUtil.getFull(i, CaveUtil.PLACE_OR), t = CaveUtil.getFull(i, CaveUtil.PLACE_THETA);
				arr.add(CaveUtil.fromCoordPair(or, t));
			} else if(Math.floor((CaveUtil.getFull(i, CaveUtil.PLACE_OR2) % 10)) == dir) {
				byte or = CaveUtil.getFull(i, CaveUtil.PLACE_OR2), t = CaveUtil.getFull(i, CaveUtil.PLACE_THETA2);
				arr.add(CaveUtil.fromCoordPair(or, t));
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
		setSize(winWidth * 4, winHeight * 3);
		this.setResizable(false);
		setVisible(true);
	}
	@Override
	public void run() {
		while(true) {


			PointerInfo pi = MouseInfo.getPointerInfo();
			Point p = pi.getLocation();

		}
	}
	private void drawUniv(Graphics g, BufferedImage bimg, ArrayList<Short> exits) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.BLACK);
		g2d.drawRect(0, 0, bimg.getWidth(), bimg.getHeight());
		g2d.setColor(Color.WHITE);
		g2d.fillRect(1, 1, bimg.getWidth()-1, bimg.getHeight()-1);
		if(exits == null || exits.size() == 0) {
			g2d.drawString("Nothing to Report", (bimg.getWidth()/2), (bimg.getHeight()/2));
		} else if(bimg != bimgs[6] && itemSelected) {
			for(int i = 0; i < exits.size(); i++) {
				short dir = exits.get(i);
				byte or = CaveUtil.getPair(dir, CaveUtil.PAIR_OR), t = CaveUtil.getPair(dir, CaveUtil.PAIR_THETA);
				byte r = (byte)(or * .10);
				byte o = (byte)(or % 10);
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
				float theta = (360 / anginc) * t;
				cx = (int) (50 * (r * Math.cos((Math.PI/180)*theta)));
				cy = (int) (50 * (r * Math.sin((Math.PI/180)*theta)));
				g2d.setColor(Color.red);
				g2d.fillOval(cx + ((bimg.getWidth()/2) + (d/2)), cy + ((bimg.getHeight()/2) + (d/2)), d, d);
				//g2d.drawString("" + t, cx + ((bimg.getWidth()/2) + (d/2)), cy + ((bimg.getHeight()/2) + (d/2)));
			}
		}
	}
	@Override
	public void paint(Graphics g) {
		//super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		createBuffer();
		g2d.drawImage(mainbuffer, 0, 0, getWidth(), getHeight(), this);
	}
	private void drawExits() {
		for(int i = 0; i < 7; i++)
			if(exits != null && exits.size() != 0)
				drawUniv(gs[i], bimgs[i], exits.get(i));
	}
	private void createBuffer() {
		Graphics2D g2d = (Graphics2D)buffer;
		boolean draw = true;
		for(int i = 0; i < bimgs.length; i++)
			if(bimgs[i] == null)
				draw = false;
		if(draw) {
			g2d.drawImage(bimgs[0], 0, 0, winWidth, winHeight, this);
			g2d.drawImage(bimgs[5], 0, winHeight, winWidth, winHeight, this);
			g2d.drawImage(bimgs[1], 0, winHeight * 2, winWidth, winHeight, this);
			g2d.drawImage(bimgs[2], winWidth * 2, winHeight * 2, winWidth, winHeight, this);
			g2d.drawImage(bimgs[3], winWidth, winHeight * 2, winWidth, winHeight, this);
			g2d.drawImage(bimgs[4], winWidth * 3, winHeight * 2, winWidth, winHeight, this);
			g2d.drawImage(bimgs[6], winWidth, 0, winWidth * 3, winHeight * 2, this);
		}
	}
}
