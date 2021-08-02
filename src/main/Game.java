package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import entities.BulletShoot;
import entities.Enemy;
import entities.Entity;
import entities.Particle;
import entities.Player;
import entities.construction.Mine;
import entities.mobs.Mob;
import graficos.Spritsheet;
import graficos.UI;
import util.CollisonPlayer;
import util.CreationItem;
import util.Mapa;
import util.Regiao;
import util.SystemBag;
import util.SystemCreation;
import util.SystemInventory;
import world.World;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	static public final int WIDTH = 240;
	static public final int HEIGHT = 160; 
	static public final int SCALE  = 3;

	private BufferedImage image;
	
	static public ArrayList <World> worlds;
	static public ArrayList <Entity> entities;
	static public ArrayList <Enemy> enemies;
	static public ArrayList <Mob> mobs;
	static public ArrayList <BulletShoot> bulletShootes;
	static public ArrayList <Particle> particles;
	static public Spritsheet spritesheet;
	static public Spritsheet spritePlayer;
	static public Spritsheet spriteMobs;
	static public Spritsheet spriteContruction;
	static public Spritsheet spriteButton;
	
	static public Mapa mapaGame;
	static public Regiao regiaoGame;
	
	public static World world;
	public static Player player;
	public static CollisonPlayer collision;
	public static SystemCreation sysCre;
	public static CreationItem createItem;
	public static SystemInventory sysInv;
	public static SystemBag sysBag;
	public Menu menu;
	public static UI ui;

	public static Random rand;
	
	public int [] pixels;
	public int [] pixels2;
	public int [] pixels3;
	public int [] pixels4;
	//public int xx, yy;
	
	public int [] lightMapPixels;
	public BufferedImage lightmap;
	public int [] lightMapPixels2;
	public BufferedImage lightmap2;
	public int [] lightMapPixels3;
	public BufferedImage lightmap3;
	public int [] lightMapPixels4;
	public BufferedImage lightmap4;
	public int [] lightCalaboucoMapPixels;
	public BufferedImage lightCalaboucoMap;
	
	double mx, my;
	
//	public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("pixelfont.ttf");
//	public Font newfont;
	
	public boolean saveGame;
	
	public static String gameState = "MENU";
	private boolean showMessageGameOver = true;
	private int framesGameOver = 0;
	
	public static int CUR_LEVEL = 1; //	MAX_LVL  = 2;
	
	private boolean restartGame;
	
	private double timer;
	public static int hour = 8, minute = 0, second, darken;
	private boolean dusk, dawn;
	private int controlDarken;
	
	public static int entrada = 1;
	public static int comecar = 2;
	public static int jogando = 3;
	public static int estado_cena = jogando;
	public int darkenScena = 255;
	
	public int timeCena = 0, maxTimeCena = 60*3;

	public Game() {
	
		Sound.Clips.medievalMusic.loop();
		rand = new Random();
		
		addKeyListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);

		setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		
//		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		spriteButton =  new Spritsheet("/button.png");
		ui = new UI(spriteButton);
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		try {
			lightmap = ImageIO.read(getClass().getResource("/lightmap.png"));
			lightmap2 = ImageIO.read(getClass().getResource("/lightmap2.png"));
			lightmap3 = ImageIO.read(getClass().getResource("/lightmap3.png"));
			lightmap4 = ImageIO.read(getClass().getResource("/lightmap4.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		lightMapPixels =  new int [lightmap.getWidth() * lightmap.getHeight()];
		lightmap.getRGB(0, 0, lightmap.getWidth(), lightmap.getHeight(), lightMapPixels, 0 , lightmap.getWidth());
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		
		lightMapPixels2 =  new int [lightmap2.getWidth() * lightmap2.getHeight()];
		lightmap2.getRGB(0, 0, lightmap2.getWidth(), lightmap2.getHeight(), lightMapPixels2, 0 , lightmap2.getWidth());
		pixels2 = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		
		lightMapPixels3 =  new int [lightmap3.getWidth() * lightmap3.getHeight()];
		lightmap3.getRGB(0, 0, lightmap3.getWidth(), lightmap3.getHeight(), lightMapPixels3, 0 , lightmap3.getWidth());
		pixels3 = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		
		lightMapPixels4 =  new int [lightmap4.getWidth() * lightmap4.getHeight()];
		lightmap4.getRGB(0, 0, lightmap4.getWidth(), lightmap4.getHeight(), lightMapPixels4, 0 , lightmap4.getWidth());
		pixels4 = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		
		worlds = new ArrayList<World>();
		entities =  new ArrayList<Entity>();
		enemies =  new ArrayList<Enemy>();
		mobs = new ArrayList<Mob>();
		bulletShootes = new  ArrayList<BulletShoot>();
		particles =  new ArrayList<Particle>();
		spritesheet =  new Spritsheet("/spritesheet.png");
		spritePlayer =  new Spritsheet("/spritePlayer.png");
		spriteMobs =  new Spritsheet("/spriteMobs.png");
		spriteContruction = new Spritsheet("/spriteConstruction.png");
		player  = new Player(0, 0, 16, 16, spritePlayer.getSprite(0, 0, 16, 16));
		player.mapa.addAll(Mapa.addAll());
		player.regiao.addAll(Regiao.addAll());
		sysInv = new SystemInventory();
		sysBag = new SystemBag();
		sysCre = new SystemCreation();
		createItem = new CreationItem();
		collision = new CollisonPlayer();
		entities.add(player);
		mapaGame = Mapa.MAPA_FLORESTA;
		regiaoGame = Regiao.REGIAO_FLORESTA;
		world =  new World("/level1.png");
		world.mapa = mapaGame;
		world.regiao = regiaoGame;
		worlds.add(world);
		menu = new Menu();
		
//		try {
//			newfont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(70f);
//		}catch (FontFormatException f) {
//			f.printStackTrace();
//		}catch(IOException e){
//			e.printStackTrace();
//		}
	}
	
	public static void main(String args[]) {
		Game game = new Game();
		game.start();
	}
	
	public void initFrame() {
		frame = new JFrame("Game #1");
		frame.add(this);
		frame.setUndecorated(true);
		//alterar Cursor
//		Image image = toolkit.getImage("C:\\Users\\klait\\eclipse-workspace\\Game\\res\\mira.png");
//		Cursor c = toolkit.createCustomCursor(image , new Point(frame.getX() + 10, frame.getY() + 15), "C:\\Users\\klait\\eclipse-workspace\\Game\\res\\mira.png");
		java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(getClass().getResource("/mira.png"));
		Cursor c = toolkit.createCustomCursor(image , new Point(0,0), "img");
		frame.setCursor (c);
		//Alterar icone da janela
		Image icon = null;
		try {
			icon = ImageIO.read(getClass().getResource("/icon.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
		frame.setIconImage(icon);
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning =  true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		
		if(this.saveGame) {
			this.saveGame = false;
			String[] opt1 = {"levelRoom", "vida", "levelPlayer", "exp"};
			int[] opt2 = {CUR_LEVEL, (int)player.life, (int)player.levelPlayer, (int)player.exp};
			Menu.saveGame(opt1,opt2,10);
			System.out.println("Jogo salvo com sucesso!");
		}
		
		if(gameState.equals("NORMAL")) {
			
			//xx++;
			restartGame = false;
			
			if(estado_cena == jogando) {
				
				for(int i=0; i<mobs.size(); i++) {
					if(!entities.contains(mobs.get(i))) {
						entities.add(mobs.get(i));
					}
				}
				
				for(int i = 0; i<entities.size(); i++) {
					Entity e = entities.get(i);
					if(e.mapa.contains(Game.mapaGame) && e.regiao.contains(Game.regiaoGame)) {
						e.tick();
					}
				}
				
				for(int i = 0; i<mobs.size(); i++) {
					Entity e = mobs.get(i);
					if(e.mapa.contains(Game.mapaGame) && e.regiao.contains(Game.regiaoGame)) {
						e.tick();
					}
				}
				
				for(int i = 0; i<bulletShootes.size(); i++) {
					BulletShoot b = bulletShootes.get(i);
					if(b.mapa.contains(Game.mapaGame) && b.regiao.contains(Game.regiaoGame)) {
						bulletShootes.get(i).tick();
					}
				}
				
				if(particles != null) {
					for(int i = 0; i<particles.size(); i++) {
						Particle p = particles.get(i);
						if(p.mapa.contains(Game.mapaGame) && p.regiao.contains(Game.regiaoGame)) {
							particles.get(i).tick();
						}
					}
				}
				
				player.speed = 1.4;
				
			}else {
				if(estado_cena == entrada) {
					if(player.getX() < 200) {
						player.moved = true;
						player.rigth = true;
						player.speed = 1;
						player.dir = player.rightDir;
						player.tick();
						player.updateCamera();
					}else {
						estado_cena = comecar;
					}
				}else if(estado_cena == comecar) {
					player.moved = false;
					player.rigth = false;
					player.tick();
					timeCena++;
					if(timeCena == maxTimeCena) {
						estado_cena = jogando;
					}
				}
			}

			//Up Niveis
//			if (enemies.size() == 0) {
//				CUR_LEVEL++;
//				if(CUR_LEVEL > MAX_LVL) {
//					CUR_LEVEL = 1;
//				}
//				
//				String newWorld = "level" + CUR_LEVEL + ".png";
//				player.levelRoom = newWorld;
//				World.restarGame(newWorld);
//			}
			
			boolean criarNovoMundo = false;
			
			if(player.enter && player.enterRoom && mapaGame.equals(Mapa.MAPA_FLORESTA)) {
				
				mapaGame = Mapa.MAPA_CALABOUÇO;
				regiaoGame = Regiao.REGIAO_CALABOUÇO;
				
				for(int i=0; i<worlds.size();i++) {
					if(!worlds.get(i).mapa.equals(Mapa.MAPA_CALABOUÇO)) { //para criar esse novo mundo
						criarNovoMundo = true;
						break;
					}else if (worlds.get(i).mapa.equals(Mapa.MAPA_CALABOUÇO)) { // Se o mundo já foi criado
						world = worlds.get(i);
						break;
					}
				}
				
				if(criarNovoMundo) {
					World w = new World("/cal.png");
					world = w;
					worlds.add(world);
					player.dir = player.rightDir;
				}
				
			//Mapa inicial, sempre começara iniciado, não a necessidade de verificar se ele existe
			}else if (player.enter && player.enterRoom && !mapaGame.equals(Mapa.MAPA_FLORESTA)) {
				mapaGame = Mapa.MAPA_FLORESTA;
				regiaoGame = Regiao.REGIAO_FLORESTA;
				world = worlds.get(0);
				for(int i=0; i<entities.size();i++) {
					if(entities.get(i) instanceof Mine) {
						player.setX(entities.get(i).getX() + 5);
						player.setY(entities.get(i).getY() + 30);
						break;
					}
				}
			}
			
			player.enterRoom = false;
			player.enter = false;
			criarNovoMundo = false;
			
		}else if (gameState.equals("GAME OVER")) {
			framesGameOver++;
			if(framesGameOver == 30) {
				framesGameOver = 0;
				if(showMessageGameOver)
					showMessageGameOver = false;
				else
					showMessageGameOver =  true;
			}
			
			if(restartGame) {
				restartGame = false;
				gameState =  "NORMAL";
				// CUR_LEVEL = 1;
				String newWorld = "level" + CUR_LEVEL + ".png";
				player.levelRoom = newWorld;
				World.restarGame(newWorld);
			}
		} else if(gameState.equals("MENU")) {
			menu.tick();
		}else if(gameState.equals("CONTROLES")) {
			menu.tick();
		}
		
//		TimeSystem
		if(Game.gameState.equals("NORMAL")) {
//			System.out.println(timer);
			
			if(timer >= 1)
				second++;
				
			if(second == 60) {
				minute++;
				second = 0;
			}
				
			if(minute == 59) {
				minute = 0;
				hour++;
			}
				
			if(hour == 24)
				hour = 0;
				
			if(hour >= 18 || hour < 5) {
				controlDarken ++;
				if(controlDarken == 15) {
					darken++;
					controlDarken = 0;
				}
				dusk = true;
				dawn = false;
					
			}else if(hour >= 5 && hour <= 7){
				controlDarken ++;
				if(controlDarken == 15) {
					darken ++;
					controlDarken = 0;
				}
				dawn = true;
				dusk = false;
					
			}else{
				dawn = false;
				dusk = false;
			}	
		}
	}
	
//	public void drawRectangleExemple(int xoff, int yoff) {
//		for (int xx = 0; xx<32; xx++) {
//			for(int yy = 0; yy<32; yy++) {
//				
//				int xOff = xx + xoff;
//				int yOff = yy + yoff;
//				
//				if(xOff < 0 || yOff < 0 || xOff >= WIDTH || yOff >= HEIGHT)
//					continue;
//				
//				pixels[xOff + (yOff*WIDTH)] = 0xFF0000;
//			}
//		}
//	}
	
	public void applayLight() {
		for (int xx = 0; xx< Game.WIDTH; xx++) {
			for(int yy = 0; yy < Game.HEIGHT; yy++) {
				if(lightMapPixels[xx + (yy * Game.WIDTH)] == 0xFFFFFFFF) {
					int pixel = Pixel.getLightBlend(pixels[xx+yy*WIDTH], 0xBCBCBC, 0);
					pixels[xx+ (yy*Game.WIDTH)] = pixel;
				}
				
				if(lightMapPixels2[xx + (yy * Game.WIDTH)] == 0xFFFFFFFF) {
					int pixel2 = Pixel.getLightBlend(pixels2[xx+yy*WIDTH], 0xBCBCBC, 0);
					pixels2[xx+ (yy*Game.WIDTH)] = pixel2;
				}
				
				if(lightMapPixels3[xx + (yy * Game.WIDTH)] == 0xFFFFFFFF) {
					int pixel3 = Pixel.getLightBlend(pixels3[xx+yy*WIDTH], 0x808080, 0);
					pixels3[xx+ (yy*Game.WIDTH)] = pixel3;
				}
				
				if(lightMapPixels4[xx + (yy * Game.WIDTH)] == 0xFFFFFFFF) {
					int pixel4 = Pixel.getLightBlend(pixels4[xx+yy*WIDTH], 0x808080, 0);
					pixels4[xx+ (yy*Game.WIDTH)] = pixel4;
				}
			}
		}
	}
	
	public void applayLightCalabouco() {
		for (int xx = 0; xx< Game.WIDTH; xx++) {
			for(int yy = 0; yy < Game.HEIGHT; yy++) {
				if(lightMapPixels[xx + (yy * Game.WIDTH)] == 0xFFFFFFFF) {
					int pixel = Pixel.getLightBlend(pixels[xx+yy*WIDTH], 0xBCBCBC, 0);
					pixels[xx+ (yy*Game.WIDTH)] = pixel;
				}
			}
		}
	}
	
	public void render() {
		
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
		
		world.render(g);
		Collections.sort(entities, Entity.nodeSorter);
		
		for(int i = 0; i<mobs.size(); i++) {
			Mob m = mobs.get(i);
			if(m.mapa.contains(Game.mapaGame) && m.regiao.contains(Game.regiaoGame)) {
				m.render(g);
			}
		}
		
		for(int i = 0; i<entities.size(); i++) {
			Entity e = entities.get(i);
//			if(e.show)
			if(e.mapa.contains(Game.mapaGame) && e.regiao.contains(Game.regiaoGame)) {
				e.render(g);
			}
		}
		
		for(int i = 0; i<bulletShootes.size(); i++) {
			BulletShoot b = bulletShootes.get(i);
			if(b.mapa.contains(Game.mapaGame) && b.regiao.contains(Game.regiaoGame)) {
				bulletShootes.get(i).render(g);
			}
		}
		
		if(particles != null) {
			for(int i = 0; i<particles.size(); i++) {
				Particle p = particles.get(i);
				if(p.mapa.contains(Game.mapaGame) && p.regiao.contains(Game.regiaoGame)) {
					particles.get(i).render(g);
				}
			}
		}

		if(Game.player.useLighter)
			applayLight();
		
		if(estado_cena == jogando)
			ui.render(g);
		
		if(estado_cena == comecar) {
			g.setFont(new Font("arial", Font.BOLD, 50));
			g.setColor(Color.white);
			g.drawString("GO!", Game.WIDTH/2 - 40, Game.HEIGHT/2 + 20);		
		}else if (estado_cena == entrada) {
			player.render(g);
		}
		//drawRectangleExemple(xx, yy);
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0,Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, null);
		
//		g.setFont(newfont);
//		g.setColor(Color.red);
//		g.drawString("teste", 90, 90);
		
		if(gameState.equals("NORMAL") || Menu.pause) {
			
			if(player.openMap)
				g.drawImage(world.minimapa, 1070, 40, Toolkit.getDefaultToolkit().getScreenSize().width/5, Toolkit.getDefaultToolkit().getScreenSize().height/3, null);
			
			Graphics2D g2 = (Graphics2D) g;
			
			if(dusk && !player.useLighter) { // se estiver anoitecendo 
				if (darken < 235) {
					g2.setColor(new Color(0,0,0, darken));
				}else {
					g2.setColor(new Color(0,0,0,235));
				}
				g2.fillRect(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
			
			}
			
			if(dawn && !player.useLighter) { // se estiver amanhecendo 
				if(235 - darken >= 0) {
					g2.setColor(new Color(0,0,0, 235 - darken));
				}else {
					g2.setColor(new Color(0,0,0,0));
				}
		
				g2.fillRect(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
		
			}
		}
		
		if(gameState.equals("GAME OVER")) {
			
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,150));
			g2.fillRect(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
			g.setFont(new Font("arial", Font.BOLD, 40));
			g.setColor(Color.white);
			g.drawString("GAME OVER", (Toolkit.getDefaultToolkit().getScreenSize().width)/2 - 105, (Toolkit.getDefaultToolkit().getScreenSize().height)/2 + 20);
			g.setFont(new Font("arial", Font.BOLD, 20));
			g.setColor(Color.white);
			if(showMessageGameOver)
				g.drawString("Pressione ENTER para reiniciar", (Toolkit.getDefaultToolkit().getScreenSize().width)/2 - 130, (Toolkit.getDefaultToolkit().getScreenSize().height)/2 + 45 );
			
		}else if (gameState.equals("MENU")) {
			menu.render(g);
		}
		else if (gameState.equals("CONTROLES")) {
			g.setColor(new Color(0,0,0));
			menu.render(g);
		}
		
		//shade de entrada
		if(estado_cena == entrada && gameState.equals("NORMAL")) {
			Graphics2D g2 = (Graphics2D) g;
			darkenScena -= second;
			if(darkenScena < 0)
				g2.setColor(new Color(0,0,0,0));
			else
				g2.setColor(new Color(0,0,0,darkenScena));
		
			g2.fillRect(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
		}
		
		//Rotacionar objetos
//		Graphics2D g2 = (Graphics2D) g;
//		double angleMouse = Math.atan2((200+25) - my, (200+25) - mx);
//		g2.rotate(angleMouse, 200+25, 200+25);
//		g.setColor(Color.red);
//		g.fillRect(200, 200, 50, 50);
	
		bs.show();
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amoutOfTicks =60.0;
		double ns = 1000000000/ amoutOfTicks;
		double delta = 0;
//		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
//				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
//				System.out.println("FPS: " + frames);
//				frames = 0;
				timer += 1000;
				this.timer = timer;
			}
			
		}

		stop();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(estado_cena == jogando) {
		
			if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
				player.rigth = true;
			}else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
				player.left = true;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_W) {
				player.up = true;
			}else if (e.getKeyCode() == KeyEvent.VK_S) {
				player.down = true;	
			}
			
			if(e.getKeyCode() == KeyEvent.VK_X) {
				player.shoot = true;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				player.jump = true;
				
			}
			
			if(e.getKeyCode() == KeyEvent.VK_P) {
				if(gameState.equals("NORMAL")) {
					this.saveGame =  true;
				}
			}
			
			if(e.getKeyCode() == KeyEvent.VK_R) {
				player.useItem = true;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_F){
				player.dropItem = true;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_G){
				player.getItem = true;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_Q){
				sysInv.scrollItemLef = true;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_E) {
				sysInv.scrollItemDir = true;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_B) {
				
				if(!player.creation) {
					if(!player.useBag)
						player.useBag = true;
					else
						player.useBag = false;
					Sound.Clips.openBag.play();
				}
				
			}
			
			if(e.getKeyCode() == KeyEvent.VK_K) {
				if(!player.useBag) {
					if(!player.creation)
						player.creation = true;
					else
						player.creation = false;
					Sound.Clips.selectedInventory.play();
				}
			}
			
			if(e.getKeyCode() == KeyEvent.VK_Z){
				if(!player.creation && !player.useBag)
					player.enter = true;
			}
			
//			if(e.getKeyCode() == KeyEvent.VK_ENTER) { 
//				
//				if(npc.showMessage) 
//					npc.showMessage = false;
//			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			if(gameState.equals("MENU")) {
				menu.up = true;
				Sound.Clips.selected.play();
			}
			
		}else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			if(gameState.equals("MENU")) {
				menu.down = true;
				Sound.Clips.selected.play();
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if(estado_cena == jogando) {
		
			if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
				player.rigth = false;
			}else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
				player.left = false;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_W) {
				player.up = false;
			}else if (e.getKeyCode() == KeyEvent.VK_S) {
				player.down = false;	
			}
			
			if(e.getKeyCode() == KeyEvent.VK_R) { 
				player.useItem = false;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_F){
				player.dropItem = false;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_G){
				player.getItem = false;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_Q){
				sysInv.scrollItemLef = false;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_Z){
				player.enter = false;
			}
		
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) { 
			restartGame =  true;
			
			if(gameState.equals("MENU") || gameState.equals("CONTROLES"))
				menu.enter = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) { 
			
			if(gameState.equals("NORMAL")) {
				gameState = "MENU";
				Menu.pause =  true;
			}else if(gameState.equals("MENU")) {
				gameState = "NORMAL";			
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		player.mouseShoot =  true;
		player.mx = e.getX()/3;
		player.my = e.getY()/3;
		
//		System.out.println("x: " + player.mx + "    y:" + player.my);
		
		if(!player.openLvls && player.mx >= 10 && player.my >= 43 && player.mx <= 37 && player.my <= 63) {
			player.openLvls = true;
			player.offLvls = false;
			player.mouseShoot =  false;
			Sound.Clips.selectedInventory.play();
			
		}else if(!player.offLvls && player.mx >= 10 && player.my >= 43 && player.mx <= 37 && player.my <= 63) {
			player.offLvls = true;
			player.openLvls = false;
			player.mouseShoot =  false;
			Sound.Clips.selectedInventory.play();
		}
		
		if(!player.openMap && player.mx >= 11 && player.my >= 64 && player.mx <= 37 && player.my <= 83) {
			player.openMap = true;
			player.offMap = false;
			player.mouseShoot =  false;
			Sound.Clips.paper.play();

		}else if (!player.offMap && player.mx >= 11 && player.my >= 64 && player.mx <= 37 && player.my <= 83) {
			player.offMap = true;
			player.openMap = false;
			player.mouseShoot =  false;
			Sound.Clips.paper.play();

		}
		
		if(player.useBag) {
			
			player.mouseShoot = false;
			 
			//Mascaras do inventario
			if(player.mx >= 138 && player.my >= 218 && player.mx <= 170 && player.my <= 245) { 
				sysInv.checkClickPositionItemInv(0);
				player.clickInv = true;
				
			}else if (player.mx >= 174 && player.my >= 218 && player.mx <= 206 && player.my <= 245) {
				sysInv.checkClickPositionItemInv(1);
				player.clickInv = true;
				
			}else if (player.mx >= 210 && player.my >= 218 && player.mx <= 242 && player.my <= 245) {
				sysInv.checkClickPositionItemInv(2);
				player.clickInv = true;
				
			}else if (player.mx >= 245 && player.my >= 218 && player.mx <= 278 && player.my <= 245) {
				sysInv.checkClickPositionItemInv(3);
				player.clickInv = true;
				
			}else if (player.mx >= 282 && player.my >= 218 && player.mx <= 314 && player.my <= 245) {
				sysInv.checkClickPositionItemInv(4);
				player.clickInv = true;
				
			}
			
			int [] index = {-1, -1};
			//Mascaras da mochila
			if(player.mx >=  158 && player.my >= 27 && player.mx <= 191 && player.my <= 54) {
				index[0] = 0;
				index[1] = 0;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;
					
			}else if (player.mx >= 195 && player.my >= 27 && player.mx <= 227 && player.my <= 54) {
				index[0] = 0;
				index[1] = 1;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;
					
			}else if (player.mx >= 230 && player.my >= 27 && player.mx <= 263 && player.my <= 54) {
				index[0] = 0;
				index[1] = 2;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;
					
			}else if (player.mx >= 266 && player.my >= 27 && player.mx <= 300 && player.my <= 54) {
				index[0] = 0;
				index[1] = 3;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;
					
			}else if (player.mx >= 158 && player.my >= 56 && player.mx <= 191 && player.my <= 82) {
				index[0] = 1;
				index[1] = 0;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;
					
			}else if (player.mx >= 195 && player.my >= 56 && player.mx <= 227 && player.my <= 82) {
				index[0] = 1;
				index[1] = 1;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;
				
			}else if (player.mx >= 230 && player.my >= 56 && player.mx <= 263 && player.my <= 82) {
				index[0] = 1;
				index[1] = 2;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;
					
			}else if (player.mx >= 266 && player.my >= 56 && player.mx <= 300 && player.my <= 82) {
				index[0] = 1;
				index[1] = 3;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;
					
			}else if (player.mx >= 158 && player.my >= 85 && player.mx <= 191 && player.my <= 111) {
				index[0] = 2;
				index[1] = 0;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;
					
			}else if (player.mx >= 195 && player.my >= 85 && player.mx <= 227 && player.my <= 111) {
				index[0] = 2;
				index[1] = 1;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;
					
			}else if (player.mx >= 230 && player.my >= 85 && player.mx <= 263 && player.my <= 111) {
				index[0] = 2;
				index[1] = 2;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;
					
			}else if (player.mx >= 266 && player.my >= 85 && player.mx <= 300 && player.my <= 111) {
				index[0] = 2;
				index[1] = 3;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;
					
			}else if (player.mx >= 158 && player.my >= 113 && player.mx <= 191 && player.my <= 140) {
				index[0] = 3;
				index[1] = 0;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;
					
			}else if (player.mx >= 195 && player.my >= 113 && player.mx <= 227 && player.my <= 140) {
				index[0] = 3;
				index[1] = 1;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;
					
			}else if (player.mx >= 230 && player.my >= 113 && player.mx <= 263 && player.my <= 140) {
				index[0] = 3;
				index[1] = 2;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;
					
			}else if (player.mx >= 266 && player.my >= 113 && player.mx <= 300 && player.my <= 140) {
				index[0] = 3;
				index[1] = 3;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;
					
			}else if (player.mx >= 158 && player.my >= 142 && player.mx <= 191 && player.my <= 168) {
				index[0] = 4;
				index[1] = 0;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;
					
			}else if (player.mx >= 195 && player.my >= 142 && player.mx <= 227 && player.my <= 168) {
				index[0] = 4;
				index[1] = 1;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;
					
			}else if (player.mx >= 230 && player.my >= 142 && player.mx <= 263 && player.my <= 168) {
				index[0] = 4;
				index[1] = 2;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;
					
			}else if (player.mx >= 266 && player.my >= 142 && player.mx <= 300 && player.my <= 168) {
				index[0] = 4;
				index[1] = 3;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;
					
			}else if (player.mx >= 158 && player.my >= 171 && player.mx <= 191 && player.my <= 198) {
				index[0] = 5;
				index[1] = 0;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;
					
			}else if (player.mx >= 195 && player.my >= 171 && player.mx <= 227 && player.my <= 198) {
				index[0] = 5;
				index[1] = 1;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;
					
			}else if (player.mx >= 230 && player.my >= 171 && player.mx <= 263 && player.my <= 198) {
				index[0] = 5;
				index[1] = 2;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;
					
			}else if (player.mx >= 266 && player.my >= 171 && player.mx <= 300 && player.my <= 198) {
				index[0] = 5;
				index[1] = 3;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;
					
			}
		}
			
		if (player.creation) {
			
			player.mouseShoot = false;
			
			//inventario
			if(player.mx >= 138 && player.my >= 218 && player.mx <= 170 && player.my <= 245) { 
				sysInv.checkClickPositionItemInv(0);
				player.clickInv = true;
				
			}else if (player.mx >= 174 && player.my >= 218 && player.mx <= 206 && player.my <= 245) {
				sysInv.checkClickPositionItemInv(1);
				player.clickInv = true;
				
			}else if (player.mx >= 210 && player.my >= 218 && player.mx <= 242 && player.my <= 245) {
				sysInv.checkClickPositionItemInv(2);
				player.clickInv = true;
				
			}else if (player.mx >= 245 && player.my >= 218 && player.mx <= 278 && player.my <= 245) {
				sysInv.checkClickPositionItemInv(3);
				player.clickInv = true;
				
			}else if (player.mx >= 282 && player.my >= 218 && player.mx <= 314 && player.my <= 245) {
				sysInv.checkClickPositionItemInv(4);
				player.clickInv = true;
				
			}
				 
			//mascaras do Creation
			if(player.mx >= 147 && player.my >= 91 && player.mx <= 179 && player.my <= 117) { 
				sysCre.checkClickPositionItemCreation(0);
				player.clickCreation = true;
					
			}else if(player.mx >= 208 && player.my >= 92 && player.mx <= 239 && player.my <= 117) { 
				sysCre.checkClickPositionItemCreation(1);
				player.clickCreation = true;
					
			}else if(player.mx >= 147 && player.my >= 140 && player.mx <= 179 && player.my <= 164) { 
				sysCre.checkClickPositionItemCreation(2);
				player.clickCreation = true;
					
			}else if(player.mx >= 207 && player.my >= 140 && player.mx <= 239 && player.my <= 165) { 
				sysCre.checkClickPositionItemCreation(3);
				player.clickCreation = true;
					
			}else if(player.mx >= 270 && player.my >= 116 && player.mx <= 302 && player.my <= 140) { 
				sysCre.checkClickPositionItemCreation(4);
				player.clickCraft = true;
			}
				
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		if(player != null) {
			player.moveMx = (e.getX() / 3);
			player.moveMy = (e.getY() / 3);
		}
	}

}
