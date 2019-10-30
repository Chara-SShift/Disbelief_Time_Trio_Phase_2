import javax.swing.*;

import javafx.scene.transform.Rotate;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.util.Random;

public class Trio_Main{

	public static void main(String[] args) {
		Trio_sub trio = new Trio_sub();
	}
}

class Trio_sub extends JFrame implements KeyListener, Runnable{
	int f_width;
	int f_height;
	
	int x, y, gravity, gravtime;
	int rect_up, rect_down, rect_left, rect_right;
	
	//interface
	boolean keyUp = false;
	boolean keyDown = false;
	boolean keyLeft = false;
	boolean keyRight = false;
	boolean exitx = false;
	boolean keyReDown = false;
	int cnt;
	attackStarter atkst;//공격이 나오는 점
	enemy.OrangeKnife okn;
	enemy.Knife kn;
	enemy.BlueKnife bkn;
	enemy.Bone bo;
	enemy.BlueBone bbo;
	enemy.OrangeBone obo;
	enemy.Knife nif;
	enemy.wallknife wnif;
	int player_speed, knife_speed, player_Status, game_time, Player_HP, Heart_Status;//0: died, 1: Red, 2: Orange 3. Blue
	Thread thr;
	int atk_status;//phase and methods to attack
	
	Toolkit toki = Toolkit.getDefaultToolkit();
	Image redHeart, blueHeart, orangeHeart;
	Image orangeknife_image,orangebone_image, blueknife_image, bluebone_image, Knife_image, bone_image;
	ArrayList attackStarter_List = new ArrayList();
	ArrayList Knife_List = new ArrayList();
	ArrayList orangeKnife_List = new ArrayList();
	ArrayList blueKnife_List = new ArrayList();
	ArrayList bone_List = new ArrayList();
	ArrayList bluebone_List = new ArrayList();
	ArrayList orangebone_List = new ArrayList();
	ArrayList wallknife_List = new ArrayList();
	Image buffimage; 
	Graphics buffg;
	
	Image backGround_img;
	Trio_sub(){
		init();
		start();
		setTitle("DisBelief Time Trio");
		setSize(f_width, f_height);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		int f_xcoor = (int)(screen.getWidth() / 2 - f_width / 2);
		int f_ycoor = (int)(screen.getHeight() / 2 - f_height / 2);
		
		setLocation(f_xcoor, f_ycoor);
		setResizable(false);
		setVisible(true);
	}
	public void init() {
		x = 490;
		y = 520;
		f_width = 1000;
		f_height = 750;
		gravity = 0;
		gravtime = 25;
		
		backGround_img = toki.getImage("DTT_BG.png");
		
		redHeart =  toki.getImage("Frisk_redheart.png");
		orangeHeart = toki.getImage("Frisk_orangeheart.png");
		blueHeart = toki.getImage("Frisk_blueheart.png");
		Knife_image = toki.getImage("CharaKnife.png");
		blueknife_image = toki.getImage("blueknife.png");
		orangeknife_image = toki.getImage("orangeknife.png");
		bluebone_image = toki.getImage("bluebone.png");
		orangebone_image = toki.getImage("Orangebone.png");
		bone_image = toki.getImage("Bone.png");
		game_time = 0;
		Player_HP = 92;
		Heart_Status = 3;
		if (Heart_Status == 2) {
			keyRight = true;
		}
	}
	public void start() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addKeyListener(this);
		thr = new Thread(this);
		thr.start();
	}
	@Override
	public void run() {
		try {
			while (true) {
				KeyProcess();
				
				
				
				repaint();
				Thread.sleep(10);
			}
		}catch(Exception e) {}
	}
	
	public void paint(Graphics g) {
		buffimage = createImage(f_width, f_height);
		buffg = buffimage.getGraphics();
		
		update(g);
	
	}

	public void update(Graphics g) {
		Draw_BackGround();
		Draw_Attack();
		Draw_HP();
		Draw_char();
		
		
		
		g.drawImage(buffimage, 0, 0, this);
		
	}
	
	public void Draw_Attack() {
		Draw_Knife();
		Draw_OrangeKnife();
		Draw_BlueKnife();
		Draw_Bone();
		Draw_OrangeBone();
		Draw_BlueBone();
		Draw_Wallknife();
	}
	public void Draw_Wallknife() {
		for (int i = 0; i < wallknife_List.size(); ++i) {
			wnif = (enemy.wallknife)(wallknife_List.get(i));
			buffg.drawImage(Knife_image,okn.pos.x , okn.pos.x, this);
		}
		
	}
	public void Draw_OrangeKnife() {
		for (int i = 0; i < orangeKnife_List.size(); ++i) {
			okn = (enemy.OrangeKnife)(orangeKnife_List.get(i));
			buffg.drawImage(orangeknife_image,okn.pos.x , okn.pos.x, this);
		}
		
	}
	public void Draw_BlueKnife() {
		for (int i = 0; i < blueKnife_List.size(); ++i) {
			bkn = (enemy.BlueKnife)(blueKnife_List.get(i));
			buffg.drawImage(blueknife_image,bkn.pos.x , bkn.pos.x, this);
		}
		
	}
	public void Draw_Bone() {
		for (int i = 0; i < bone_List.size(); ++i) {
			bo = (enemy.Bone)(bone_List.get(i));
			buffg.drawImage(bone_image,bo.pos.x , bo.pos.x, this);
		}
		
	}
	public void Draw_OrangeBone() {
		for (int i = 0; i < orangebone_List.size(); ++i) {
			obo = (enemy.OrangeBone)(orangebone_List.get(i));
			buffg.drawImage(orangebone_image, obo.pos.x , obo.pos.x, this);
		}
		
	}
	public void Draw_BlueBone() {
		for (int i = 0; i < bluebone_List.size(); ++i) {
			bbo = (enemy.BlueBone)(bluebone_List.get(i));
			buffg.drawImage(bluebone_image,bbo.pos.x , bbo.pos.x, this);
		}
	}
	public void Draw_BackGround() {
		buffg.clearRect(0, 0, f_width, f_height);
		
		buffg.drawImage(backGround_img, 0, 0, this);
		
	}
	private void Draw_HP() {
		buffg.setColor(Color.white);
		buffg.setFont(new Font("Default", Font.BOLD, 43));
		buffg.drawString("" + Player_HP, 390, 720);
		
	}
	public void Draw_char() {
		//buffg.clearRect(0, 0, f_width, f_height);
		if(Heart_Status == 1) {
			buffg.drawImage(redHeart, x, y, this);
		}
		else if(Heart_Status == 2) {
			buffg.drawImage(orangeHeart, x, y, this);
		}
		else if(Heart_Status == 3) {
			buffg.drawImage(blueHeart, x, y, this);
			if(y >= 620) {
				gravity = 0;
				gravtime = 25;
				keyReDown = false;
			}
			else if(y < 638) {
				y -= (gravity < 0) ? gravity : -2;
			}
		}
		else {
			
		}
	}
	public void Draw_Knife() {
		for (int i = 0; i < Knife_List.size(); ++i) {
			kn = (enemy.Knife)(Knife_List.get(i));
			buffg.drawImage(Knife_image,kn.pos.x , kn.pos.x, this);
		}
		
		
	}
	public void spawning(enemy enemy, attackStarter atks) {
		double rand = (Math.random() * 2);
		
	}
	public void if_hit(enemy enemy, Object heart) {
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(Heart_Status == 2)
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP:
				keyUp = true;
				keyDown = false;
				keyLeft = false;
				keyRight = false;
				break;
			case KeyEvent.VK_DOWN:
				keyDown = true;
				keyUp = false;
				keyLeft = false;
				keyRight = false;
				break;
			case KeyEvent.VK_LEFT:
				keyUp = false;
				keyDown = false;
				keyRight = false;
				keyLeft = true;
				break;
			case KeyEvent.VK_RIGHT:
				keyRight = true;
				keyUp = false;
				keyDown = false;
				keyLeft = false;
				break;
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
				break;
			}
		else {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP:
				keyUp = true;
				break;
			case KeyEvent.VK_DOWN:
				keyDown = true;
				break;
			case KeyEvent.VK_LEFT:
				keyLeft = true;
				break;
			case KeyEvent.VK_RIGHT:
				keyRight = true;
				break;
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
				break;
			}
		}
	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if(Heart_Status == 2) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP:
				keyUp = true;
				break;
			case KeyEvent.VK_DOWN:
				keyDown = true;
				break;
			case KeyEvent.VK_LEFT:
				keyLeft = true;
				break;
			case KeyEvent.VK_RIGHT:
				keyRight = true;
				break;
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
				break;
			}
		}
		else if (Heart_Status == 3) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP:
				keyUp = false;
				keyReDown = true;
				break;
			case KeyEvent.VK_DOWN:
				keyDown = false;
				break;
			case KeyEvent.VK_LEFT:
				keyLeft = false;
				break;
			case KeyEvent.VK_RIGHT:
				keyRight = false;
				break;
			}
		}
		else {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP:
				keyUp = false;
				break;
			case KeyEvent.VK_DOWN:
				keyDown = false;
				break;
			case KeyEvent.VK_LEFT:
				keyLeft = false;
				break;
			case KeyEvent.VK_RIGHT:
				keyRight = false;
				break;
			}
		}
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void KeyProcess() {
		if(Heart_Status == 1) {
			if (keyUp) {
				if(y > 394) {
					y -= 4;
				}
			}
			if (keyDown) {
				if(y < 630) {
					y += 4;
				}
			}
			if (keyRight) {
				if(x < 610) {
					x += 4;
				}
			}
			if (keyLeft) {
				if(x > 374) {
					x -= 4;
				}
			}
		}
		else if(Heart_Status == 3) {
			if (keyUp) {
				if(y > 394) {
					if(y < 630) {
						if(keyReDown == false) {
							y -= 4 + gravity;
							
							if (gravtime == 0) {
								gravity -= 1;
								gravtime = 25;
							}
							else {
								gravtime -= 1;
							}
						}
					}
				}
				else {
					keyReDown = true;
				}
			}
			
			if (keyRight) {
				if(x < 610) {
					x += 3;
					if(y < 620) {
						y -= gravity;
						/*
						if (gravtime == 0) {
							gravity -= 1;
							gravtime = 25;
						}
						else {
							gravtime -= 1;
						}
						*/
					}
				}
			}
			if (keyLeft) {
				if(x > 374) {
				x -= 3;
				if(y < 620) {
					y -= gravity;
					/*
					if (gravtime == 0) {
						gravity -= 1;
						gravtime = 25;
					}
					else {
						gravtime -= 1;
					}
					*/
				}
				}
			}
		}
		if(Heart_Status == 2) {
			if (keyUp) {
				if(y > 394) {
					y -= 4;
					
				}
			}
			if (keyDown) {
				if(y < 620) {
					y += 4;
				}
			}
			if (keyRight) {
				if(x < 610) {
					x += 4;
				}
			}
			if (keyLeft) {
				if(x > 374) {
					x -= 4;
				}
			}
		}
	}
	public static BufferedImage rotate(BufferedImage image, double angle) {
	    double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
	    int w = image.getWidth(), h = image.getHeight();
	    int neww = (int)Math.floor(w*cos+h*sin), newh = (int) Math.floor(h * cos + w * sin);
	    GraphicsConfiguration gc = getDefaultConfiguration();
	    BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
	    Graphics2D g = result.createGraphics();
	    g.translate((neww - w) / 2, (newh - h) / 2);
	    g.rotate(angle, w / 2, h / 2);
	    g.drawRenderedImage(image, null);
	    g.dispose();
	    return result;
	}

	private static GraphicsConfiguration getDefaultConfiguration() {
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice gd = ge.getDefaultScreenDevice();
	    return gd.getDefaultConfiguration();
	}
}
class attackStarter{
	Point pos;
	attackStarter(int x, int y){
		pos = new Point(x,y);
	}
	public void move(double Speed, double degree) {
		pos.x = (int)(Speed * Math.cos(Math.toRadians(degree)) + pos.x);
		pos.y = (int)(Speed * Math.sin(Math.toRadians(degree)) + pos.y);
	}
}
class enemy{
	class wallknife{
		Point pos;
		public wallknife(int x, int y) {
			pos = new Point(x, y);
		}
		public void move(double Speed, double degree) {
			pos.x = (int)(Speed * Math.cos(Math.toRadians(degree)) + pos.x);
			pos.y = (int)(Speed * Math.sin(Math.toRadians(degree)) + pos.y);
		}
	}

	class Knife{//Math.rotate()
		Point pos;
		Knife(int x,int y){
			pos = new Point(x, y);
		}
		/*
		public void rotate(int xplus, int yplus) {
			double degree = Math.toDegrees(Math.atan((double)(xplus / yplus)));
		}
		*/
		public void move(double Speed, double degree) {
			pos.x = (int)(Speed * Math.cos(Math.toRadians(degree)) + pos.x);
			pos.y = (int)(Speed * Math.sin(Math.toRadians(degree)) + pos.y);
		}
	}
	class BlueKnife{//Math.rotate()
		Point pos;
		BlueKnife(int x,int y){
			pos = new Point(x, y);
		}
		/*
		public void rotate(int xplus, int yplus) {
			double degree = Math.toDegrees(Math.atan((double)(xplus / yplus)));
		}
		*/
		public void move(double Speed, double degree) {
			pos.x = (int)(Speed * Math.cos(Math.toRadians(degree)) + pos.x);
			pos.y = (int)(Speed * Math.sin(Math.toRadians(degree)) + pos.y);
		}
	}
	class OrangeKnife{//Math.rotate()
		Point pos;
		OrangeKnife(int x,int y){
			pos = new Point(x, y);
		}
		
		public void rotate(double degree) {
			final Rotate rotate = new Rotate(degree);
		}
		
		public void move(double Speed, double degree) {
			pos.x = (int)(Speed * Math.cos(Math.toRadians(degree)) + pos.x);
			pos.y = (int)(Speed * Math.sin(Math.toRadians(degree)) + pos.y);
		}
	}
	class Bone{//Math.rotate()
		Point pos;
		Bone(int x,int y){
			pos = new Point(x, y);
		}
		/*
		public void rotate(int xplus, int yplus) {
			double degree = Math.toDegrees(Math.atan((double)(xplus / yplus)));
		}
		*/
		public void move(double Speed, double degree) {
			pos.x = (int)(Speed * Math.cos(Math.toRadians(degree)) + pos.x);
			pos.y = (int)(Speed * Math.sin(Math.toRadians(degree)) + pos.y);
		}
	}
	class OrangeBone{//Math.rotate()
		Point pos;
		OrangeBone(int x,int y){
			pos = new Point(x, y);
		}
		/*
		public void rotate(int xplus, int yplus) {
			double degree = Math.toDegrees(Math.atan((double)(xplus / yplus)));
		}
		*/
		public void move(double Speed, double degree) {
			pos.x = (int)(Speed * Math.cos(Math.toRadians(degree)) + pos.x);
			pos.y = (int)(Speed * Math.sin(Math.toRadians(degree)) + pos.y);
		}
	}
	class BlueBone{//Math.rotate()
		Point pos;
		BlueBone(int x,int y){
			pos = new Point(x, y);
		}
		/*
		public void rotate(double degree) {
			double degree = Math.toDegrees(Math.atan((double)(xplus / yplus)));
		}
		*/
		public void move(double Speed, double degree) {
			pos.x = (int)(Speed * Math.cos(Math.toRadians(degree)) + pos.x);
			pos.y = (int)(Speed * Math.sin(Math.toRadians(degree)) + pos.y);
		}
	}
}