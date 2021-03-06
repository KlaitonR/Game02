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
import entities.construction.MineLand;
import entities.construction.Thorn;
import entities.mobs.Mob;
import entities.objectMap.GramaAgua;
import entities.objectMap.VitoriaRegia;
import graficos.Spritsheet;
import graficos.UI;
import util.CollisonPlayer;
import util.CreateItemOvenBonfire;
import util.CreationItem;
import util.GetResource;
import util.Mapa;
import util.Regiao;
import util.SysTime;
import util.SystemBag;
import util.SystemBuild;
import util.SystemCreation;
import util.SystemDestruct;
import util.SystemInventory;
import util.SystemOvenBonfire;
import world.WaterTile;
import world.World;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	static public final int WIDTH = 240;
	static public final int HEIGHT = 160;
	static public final int SCALE = 3;
	public double timer;

	private BufferedImage image;

	static public ArrayList<World> worlds;
	static public ArrayList<Entity> entities;
	static public ArrayList<Enemy> enemies;
	static public ArrayList<Mob> mobs;
	static public ArrayList<BulletShoot> bulletShootes;
	static public ArrayList<Particle> particles;
	static public Spritsheet spritesheet;
	static public Spritsheet spritePlayer;
	static public Spritsheet spritePlayerAnimation;
	static public Spritsheet spriteMobs;
	static public Spritsheet spriteContruction;
	static public Spritsheet spriteButton;
	static public Spritsheet spriteBomb;

	static public Mapa mapaGame;
	static public Regiao regiaoGame;

	public static World world;
	public static Player player;
	public static CollisonPlayer collision;
	public static SystemCreation sysCre;
	public static CreationItem createItem;
	public static CreateItemOvenBonfire createItemOvenBonfire;
	public static SystemInventory sysInv;
	public static SystemBag sysBag;
	public static SystemBuild sysBuild;
	public static SystemDestruct sysDestruct;
	public static SystemOvenBonfire sysOvenBonfire;
	public static SysTime sysTime;
	public static GetResource getResource;
	public Menu menu;
	public static UI ui;

	public static Random rand;

	public int[] pixels;
	public int[] pixels2;
	public int[] pixels3;
	public int[] pixels4;
	// public int xx, yy;

	public int[] lightMapPixels;
	public BufferedImage lightmap;
	public int[] lightMapPixels2;
	public BufferedImage lightmap2;
	public int[] lightMapPixels3;
	public BufferedImage lightmap3;
	public int[] lightMapPixels4;
	public BufferedImage lightmap4;
	public int[] lightCalaboucoMapPixels;
	public BufferedImage lightCalaboucoMap;

	double mx, my;

//	public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("pixelfont.ttf");
//	public Font newfont;

	public boolean saveGame;

	public static String gameState = "MENU";
	private boolean showMessageGameOver = true;
	private int framesGameOver = 0;

	public static int CUR_LEVEL = 1; // MAX_LVL = 2;

	private boolean restartGame;

	public static int entrada = 1;
	public static int comecar = 2;
	public static int jogando = 3;
	public static int estado_cena = jogando;
	public int darkenScena = 255;
	public static boolean enterWorld, modeConstruction, modeDesreuct;

	public int timeCena = 0, maxTimeCena = 60 * 3;

	public Game() {

//		Sound.Clips.medievalMusic.loop();
		rand = new Random();

		addKeyListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);

		setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));

//		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));  //modo Janela
		initFrame();
		spriteButton = new Spritsheet("/button.png");
		ui = new UI(spriteButton);

		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

		try {
			lightmap = ImageIO.read(getClass().getResource("/images/lightmap.png"));
			lightmap2 = ImageIO.read(getClass().getResource("/images/lightmap2.png"));
			lightmap3 = ImageIO.read(getClass().getResource("/images/lightmap3.png"));
			lightmap4 = ImageIO.read(getClass().getResource("/images/lightmap4.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		lightMapPixels = new int[lightmap.getWidth() * lightmap.getHeight()];
		lightmap.getRGB(0, 0, lightmap.getWidth(), lightmap.getHeight(), lightMapPixels, 0, lightmap.getWidth());
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

		lightMapPixels2 = new int[lightmap2.getWidth() * lightmap2.getHeight()];
		lightmap2.getRGB(0, 0, lightmap2.getWidth(), lightmap2.getHeight(), lightMapPixels2, 0, lightmap2.getWidth());
		pixels2 = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

		lightMapPixels3 = new int[lightmap3.getWidth() * lightmap3.getHeight()];
		lightmap3.getRGB(0, 0, lightmap3.getWidth(), lightmap3.getHeight(), lightMapPixels3, 0, lightmap3.getWidth());
		pixels3 = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

		lightMapPixels4 = new int[lightmap4.getWidth() * lightmap4.getHeight()];
		lightmap4.getRGB(0, 0, lightmap4.getWidth(), lightmap4.getHeight(), lightMapPixels4, 0, lightmap4.getWidth());
		pixels4 = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

		worlds = new ArrayList<World>();
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		mobs = new ArrayList<Mob>();
		bulletShootes = new ArrayList<BulletShoot>();
		particles = new ArrayList<Particle>();
		spritesheet = new Spritsheet("/spritesSheet/spritesheet.png");
		spritePlayer = new Spritsheet("/spritesSheet/spritePlayer.png");
		spritePlayerAnimation = new Spritsheet("/spritesSheet/spritePlayerAnimation.png");
		spriteMobs = new Spritsheet("/spritesSheet/spriteMobs.png");
		spriteContruction = new Spritsheet("/spritesSheet/spriteConstruction.png");
		spriteBomb = new Spritsheet("/spritesSheet/spriteBomb.png");
		player = new Player(0, 0, 16, 16, spritePlayer.getSprite(0, 0, 16, 16));
		player.mapa.addAll(Mapa.addAll());
		player.regiao.addAll(Regiao.addAll());
		sysInv = new SystemInventory();
		sysBag = new SystemBag();
		sysCre = new SystemCreation();
		sysTime = new SysTime();
		sysBuild = new SystemBuild();
		sysDestruct = new SystemDestruct();
		sysOvenBonfire = new SystemOvenBonfire();
		createItem = new CreationItem();
		createItemOvenBonfire = new CreateItemOvenBonfire();
		collision = new CollisonPlayer();
		getResource = new GetResource();
		entities.add(player);
		mapaGame = Mapa.MAPA_FLORESTA;
		regiaoGame = Regiao.REGIAO_FLORESTA;
//		mapaGame = Mapa.MAPA_ROOM_HOUSE_01;
//		regiaoGame = Regiao.REGIAO_ROOM_HOUSE_01;
		world = new World("/maps/level1.png");
//		world = new World("/maps/room01.png");
//		player.setX(16);
//		player.setY(48);
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
		// alterar Cursor
//		Image image = toolkit.getImage("C:\\Users\\klait\\eclipse-workspace\\Game\\res\\mira.png");
//		Cursor c = toolkit.createCustomCursor(image , new Point(frame.getX() + 10, frame.getY() + 15), "C:\\Users\\klait\\eclipse-workspace\\Game\\res\\mira.png");
		java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(getClass().getResource("/mira.png"));
		Cursor c = toolkit.createCustomCursor(image, new Point(0, 0), "img");
		frame.setCursor(c);
		// Alterar icone da janela
		Image icon = null;
		try {
			icon = ImageIO.read(getClass().getResource("/icon.png"));
		} catch (IOException e) {
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
		isRunning = true;
		thread.start();
	}

	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void tick() {

		if (this.saveGame) {
			this.saveGame = false;
			String[] opt1 = { "levelRoom", "vida", "levelPlayer", "exp" };
			int[] opt2 = { CUR_LEVEL, (int) player.life, (int) player.levelPlayer, (int) player.exp };
			Menu.saveGame(opt1, opt2, 10);
			System.out.println("Jogo salvo com sucesso!");
		}

		if (gameState.equals("NORMAL")) {

			// xx++;
			restartGame = false;

			if (estado_cena == jogando) {

				for (int i = 0; i < mobs.size(); i++) {
					if (!entities.contains(mobs.get(i))) {
						entities.add(mobs.get(i));
					}
				}

				for (int i = 0; i < entities.size(); i++) {
					Entity e = entities.get(i);
					if (e.mapa.contains(Game.mapaGame) && e.regiao.contains(Game.regiaoGame)) {
						e.tick();
					}
				}

				for (int i = 0; i < mobs.size(); i++) {
					Entity e = mobs.get(i);
					if (e.mapa.contains(Game.mapaGame) && e.regiao.contains(Game.regiaoGame)) {
						e.tick();
					}
				}

				for (int i = 0; i < bulletShootes.size(); i++) {
					BulletShoot b = bulletShootes.get(i);
					if (b.mapa.contains(Game.mapaGame) && b.regiao.contains(Game.regiaoGame)) {
						bulletShootes.get(i).tick();
					}
				}

				if (particles != null) {
					for (int i = 0; i < particles.size(); i++) {
						Particle p = particles.get(i);
						if (p.mapa.contains(Game.mapaGame) && p.regiao.contains(Game.regiaoGame)) {
							particles.get(i).tick();
						}
					}
				}

				player.speed = 1.4;

			} else {
				if (estado_cena == entrada) {
					if (player.getX() < 200) {
						player.moved = true;
						player.rigth = true;
						player.speed = 1;
						player.dir = player.rightDir;
						player.tick();
						player.updateCamera();
					} else {
						estado_cena = comecar;
					}
				} else if (estado_cena == comecar) {
					player.moved = false;
					player.rigth = false;
					player.tick();
					timeCena++;
					if (timeCena == maxTimeCena) {
						estado_cena = jogando;
					}
				}
			}

			// Up Niveis
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
			int indexRoom = 0;

			if (player.enter && player.enterRoom) {

				enterWorld = true;
				verificaRoom();

				for (int i = 0; i < worlds.size(); i++) {
					if (!worlds.get(i).mapa.equals(mapaGame)) { // para criar esse novo mundo
						criarNovoMundo = true;
					} else if (worlds.get(i).mapa.equals(mapaGame)) { // Se o mundo j? foi criado
						world = worlds.get(i);
						criarNovoMundo = false;
						indexRoom = i;
						break;
					}
				}

				if (criarNovoMundo) {

					World w = null;

					if (mapaGame.equals(Mapa.MAPA_CALABOU?O)) {
						player.dir = player.rightDir;
						player.setX(14);
						player.setY(96);
						w = new World("/maps/cal.png");
					}else if (mapaGame.equals(Mapa.MAPA_ROOM_HOUSE_01)) {
						player.dir = player.rightDir;
						player.setX(14);
						player.setY(48);
						w = new World("/maps/room01.png");
					}

					w.mapa = Game.mapaGame;
					w.regiao = Game.regiaoGame;
					world = w;
					worlds.add(world);

				} else {

					world = worlds.get(indexRoom);

					//calabou?o >> floresta
					if (mapaGame.equals(Mapa.MAPA_FLORESTA) &&
							regiaoGame.equals(Regiao.REGIAO_FLORESTA) &&
							player.backRoom.equals(Mapa.MAPA_CALABOU?O)) {
						player.setX(Mine.psX + 8);
						player.setY(Mine.psY + 24);
						player.dir = player.rightDir;
						
					//floresta >> calabouco
					} else if (mapaGame.equals(Mapa.MAPA_CALABOU?O) &&
							regiaoGame.equals(Regiao.REGIAO_CALABOU?O) &&
							player.backRoom.equals(Mapa.MAPA_FLORESTA)) {
						player.setX((1*world.TILE_SIZE)-1);
						player.setY(6*world.TILE_SIZE);
						player.dir = player.rightDir;
						
					//floresta >> casa
					} else if (mapaGame.equals(Mapa.MAPA_ROOM_HOUSE_01) &&
							regiaoGame.equals(Regiao.REGIAO_ROOM_HOUSE_01) &&
							player.backRoom.equals(Mapa.MAPA_FLORESTA)) {
						player.setX((1*world.TILE_SIZE)-2);
						player.setY(3*world.TILE_SIZE);
						player.dir = player.rightDir;
						
					//casa >> floresta
					} else if (mapaGame.equals(Mapa.MAPA_FLORESTA) &&
							regiaoGame.equals(Regiao.REGIAO_FLORESTA) &&
							player.backRoom.equals(Mapa.MAPA_ROOM_HOUSE_01)) {
						player.setX((50*world.TILE_SIZE)-8);
						player.setY((51*world.TILE_SIZE)+2);
						player.dir = player.leftDir;
						
					}
				}
			}

			player.enterRoom = false;
			player.enter = false;
			criarNovoMundo = false;

		} else if (gameState.equals("GAME OVER")) {
			framesGameOver++;
			if (framesGameOver == 30) {
				framesGameOver = 0;
				if (showMessageGameOver)
					showMessageGameOver = false;
				else
					showMessageGameOver = true;
			}

			if (restartGame) {
				restartGame = false;
				gameState = "NORMAL";
				// CUR_LEVEL = 1;
				String newWorld = "level" + CUR_LEVEL + ".png";
				player.levelRoom = newWorld;
				World.restarGame(newWorld);
			}
		} else if (gameState.equals("MENU")) {
			menu.tick();
		} else if (gameState.equals("CONTROLES")) {
			menu.tick();
		}

//		TimeSystem
		if (Game.gameState.equals("NORMAL")) {
			// Frames da ?gua
			tickWaterFrames();
			// CHUVA
			sysTime.tick(timer);
			sysBuild.tick();
			sysDestruct.tick();
		}

	}

	public void verificaRoom() {
		
		//Se est? na floresta vai para a casa
		if(mapaGame.equals(Mapa.MAPA_FLORESTA) && regiaoGame.equals(Regiao.REGIAO_FLORESTA) && player.nextRoom.equals(Mapa.MAPA_ROOM_HOUSE_01)) {
			mapaGame = Mapa.MAPA_ROOM_HOUSE_01;
			regiaoGame = Regiao.REGIAO_ROOM_HOUSE_01;
			
		//Se est? na floresta vai para a mina 
		}else if (mapaGame.equals(Mapa.MAPA_FLORESTA) && regiaoGame.equals(Regiao.REGIAO_FLORESTA) && player.nextRoom.equals(Mapa.MAPA_CALABOU?O)) {
			mapaGame = Mapa.MAPA_CALABOU?O;
			regiaoGame = Regiao.REGIAO_CALABOU?O;
			
		//Se est? na mina, vai para a floresta
		} else if (mapaGame.equals(Mapa.MAPA_CALABOU?O) && regiaoGame.equals(Regiao.REGIAO_CALABOU?O) && player.nextRoom.equals(Mapa.MAPA_FLORESTA)) {
			mapaGame = Mapa.MAPA_FLORESTA;
			regiaoGame = Regiao.REGIAO_FLORESTA;
			
		//Se est? dentro da casa, vai para a floresta
		}else if (mapaGame.equals(Mapa.MAPA_ROOM_HOUSE_01) && regiaoGame.equals(Regiao.REGIAO_ROOM_HOUSE_01) && player.nextRoom.equals(Mapa.MAPA_FLORESTA)) {
			mapaGame = Mapa.MAPA_FLORESTA;
			regiaoGame = Regiao.REGIAO_FLORESTA;
			
		}
	}

	public void tickWaterFrames() {
		WaterTile.frames++;
		if (WaterTile.frames == WaterTile.maxFrames) {
			WaterTile.frames = 0;
			WaterTile.index++;
			if (WaterTile.index > WaterTile.maxIndex)
				WaterTile.index = 0;

			for (int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				if (e instanceof VitoriaRegia || e instanceof GramaAgua) {

					if (WaterTile.index == 1) {
						e.setX(e.getX() - 1);
					} else if (WaterTile.index == 2) {
						e.setX(e.getX() + 1);
					} else if (WaterTile.index == 3) {
						e.setX(e.getX() + 1);
					} else if (WaterTile.index == 4) {
						e.setX(e.getX() - 1);
					}
				}
			}

		}
	}
	
	public void verificaUnlockedBuilding() {
		
		if(player.levelPlayer >= 0) {
			Mine.unlocked = true;
			Thorn.unlocked = true;
			MineLand.unlocked = true;
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
		for (int xx = 0; xx < Game.WIDTH; xx++) {
			for (int yy = 0; yy < Game.HEIGHT; yy++) {
				if (lightMapPixels[xx + (yy * Game.WIDTH)] == 0xFFFFFFFF) {
					int pixel = Pixel.getLightBlend(pixels[xx + yy * WIDTH], 0xBCBCBC, 0);
					pixels[xx + (yy * Game.WIDTH)] = pixel;
				}

				if (lightMapPixels2[xx + (yy * Game.WIDTH)] == 0xFFFFFFFF) {
					int pixel2 = Pixel.getLightBlend(pixels2[xx + yy * WIDTH], 0xBCBCBC, 0);
					pixels2[xx + (yy * Game.WIDTH)] = pixel2;
				}

				if (lightMapPixels3[xx + (yy * Game.WIDTH)] == 0xFFFFFFFF) {
					int pixel3 = Pixel.getLightBlend(pixels3[xx + yy * WIDTH], 0x808080, 0);
					pixels3[xx + (yy * Game.WIDTH)] = pixel3;
				}

				if (lightMapPixels4[xx + (yy * Game.WIDTH)] == 0xFFFFFFFF) {
					int pixel4 = Pixel.getLightBlend(pixels4[xx + yy * WIDTH], 0x808080, 0);
					pixels4[xx + (yy * Game.WIDTH)] = pixel4;
				}
			}
		}
	}

	public void applayLightCalabouco() {
		for (int xx = 0; xx < Game.WIDTH; xx++) {
			for (int yy = 0; yy < Game.HEIGHT; yy++) {
				if (lightMapPixels[xx + (yy * Game.WIDTH)] == 0xFFFFFFFF) {
					int pixel = Pixel.getLightBlend(pixels[xx + yy * WIDTH], 0xBCBCBC, 0);
					pixels[xx + (yy * Game.WIDTH)] = pixel;
				}
			}
		}
	}
	
	private void shade(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		darkenScena -= 4; 
		if (darkenScena < 0) {
			g2.setColor(new Color(0, 0, 0, 0));
			enterWorld = false;
			modeConstruction = false;
			modeDesreuct = false;
			darkenScena = 255;
		}else {
			g2.setColor(new Color(0, 0, 0, darkenScena));
		}
		
		g2.fillRect(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
	}

	public void render() {

		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = image.getGraphics();
		
		// renderizar tela preta atras do mundo
		if(world.HEIGHT < 16 || world.WIDTH < 16) {
			g.setColor(new Color(0,0,0));
			g.fillRect(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
		}
		
		world.render(g);
		// ordenar depth
		Collections.sort(entities, Entity.nodeSorter);

		for (int i = 0; i < mobs.size(); i++) {
			Mob m = mobs.get(i);
			if (m.mapa.contains(Game.mapaGame) && m.regiao.contains(Game.regiaoGame)) {
				m.render(g);
			}
		}

		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (e.mapa.contains(Game.mapaGame) && e.regiao.contains(Game.regiaoGame)) 
				e.render(g);
		}

		for (int i = 0; i < bulletShootes.size(); i++) {
			BulletShoot b = bulletShootes.get(i);
			if (b.mapa.contains(Game.mapaGame) && b.regiao.contains(Game.regiaoGame)) {
				bulletShootes.get(i).render(g);
			}
		}

		if (particles != null) {
			for (int i = 0; i < particles.size(); i++) {
				Particle p = particles.get(i);
				if (p.mapa.contains(Game.mapaGame) && p.regiao.contains(Game.regiaoGame)) {
					particles.get(i).render(g);
				}
			}
		}

		if (Game.player.useLighter)
			applayLight();

		// CHUVA
		if (sysTime.chovendo) {
			if (sysTime.mapa.contains(world.mapa) && sysTime.regiao.contains(world.regiao))
				g.drawImage(sysTime.imageChuva(), 0, 0, WIDTH, HEIGHT, null);
		}

		if (gameState.equals("NORMAL")) {
			sysTime.render(g);
			sysBuild.renderBuilding(g);
			sysDestruct.renderDestruct(g);
			
			if(enterWorld || modeConstruction || modeDesreuct) {
				shade(g);
			}
			
		}

		if (estado_cena == jogando)
			ui.render(g);

		if (estado_cena == comecar) {
			g.setFont(new Font("arial", Font.BOLD, 50));
			g.setColor(Color.white);
			g.drawString("1999", Game.WIDTH / 2 - 40, Game.HEIGHT / 2 + 20);
		} else if (estado_cena == entrada) {
			player.render(g);
		}
		// drawRectangleExemple(xx, yy);

		g.dispose();
		g = bs.getDrawGraphics();
	
		g.drawImage(image, 0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, null);
		
//		g.setFont(newfont);
//		g.setColor(Color.red);
//		g.drawString("teste", 90, 90);

		if (gameState.equals("NORMAL") || Menu.pause) {

			if (player.openMap)
				g.drawImage(world.minimapa, 1070, 105, Toolkit.getDefaultToolkit().getScreenSize().width / 5,
						Toolkit.getDefaultToolkit().getScreenSize().height / 3, null);
			
			//Apresentar descri??o e pre?o das constru??es
			renderDescription(g);
			renderMessageErrorConstruction(g);
			
		}

		if (gameState.equals("GAME OVER")) {

			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 150));
			g2.fillRect(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width,
					Toolkit.getDefaultToolkit().getScreenSize().height);
			g.setFont(new Font("arial", Font.BOLD, 40));
			g.setColor(Color.white);
			g.drawString("GAME OVER", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 105,
					(Toolkit.getDefaultToolkit().getScreenSize().height) / 2 + 20);
			g.setFont(new Font("arial", Font.BOLD, 20));
			g.setColor(Color.white);
			if (showMessageGameOver)
				g.drawString("Pressione ENTER para reiniciar",
						(Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 130,
						(Toolkit.getDefaultToolkit().getScreenSize().height) / 2 + 45);

		} else if (gameState.equals("MENU")) {
			menu.render(g);
		} else if (gameState.equals("CONTROLES")) {
			g.setColor(new Color(0, 0, 0));
			menu.render(g);
		}

		// shade de entrada
		if (estado_cena == entrada && gameState.equals("NORMAL")) {
			Graphics2D g2 = (Graphics2D) g;
			darkenScena -= sysTime.second;
			if (darkenScena < 0)
				g2.setColor(new Color(0, 0, 0, 0));
			else
				g2.setColor(new Color(0, 0, 0, darkenScena));

			g2.fillRect(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width,
					Toolkit.getDefaultToolkit().getScreenSize().height);
		}

		// Rotacionar objetos
//		Graphics2D g2 = (Graphics2D) g;
//		double angleMouse = Math.atan2((200+25) - my, (200+25) - mx);
//		g2.rotate(angleMouse, 200+25, 200+25);
//		g.setColor(Color.red);
//		g.fillRect(200, 200, 50, 50);

		bs.show();
	}
	
	public void renderDescription(Graphics g) {
		if(player.clickBuildDescription && player.build && !player.clickConstruction) {
			
			g.setFont(new Font("arial", Font.BOLD, 20));
			g.setColor(Color.white);
			
			if(Game.player.indexDescription == 0) {
				g.drawString("" + Mine.description, 450, 475);
				g.drawString("" + Mine.price, 450, 580);
				
			}else if(Game.player.indexDescription == 1){
				g.drawString("" + Thorn.description, 450, 475);
				g.drawString("" + Thorn.price, 450, 580);
				
			}else if(Game.player.indexDescription == 2){
				g.drawString("" + MineLand.description, 450, 475);
				g.drawString("" + MineLand.price, 450, 580);
			}
		}
	}
	
	public void renderMessageErrorConstruction(Graphics g) {
		if(sysBuild.message != null) {
			
			g.setColor(new Color(0,0,0,200));
			g.fillRect(12, 522, 500, 45);
			
//			g.setColor(new Color(255,255,255,150));
//			g.drawRect(14, 524, 495, 40);
			
			g.drawImage(ui.UI[18], 1 ,514, ui.UI[18].getWidth()*4, ui.UI[18].getHeight()*4, null);
			
			g.setFont(new Font("arial", Font.BOLD, 20));
			g.setColor(new Color(255,255,255,255));
			g.drawString("" + sysBuild.message, 60, 552);
		}
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amoutOfTicks = 60.0;
		double ns = 1000000000 / amoutOfTicks;
		double delta = 0;
//		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				render();
//				frames++;
				delta--;
			}

			if (System.currentTimeMillis() - timer >= 1000) {
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

		if (estado_cena == jogando) {

			if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
				player.rigth = true;
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
				player.left = true;
			}

			if (e.getKeyCode() == KeyEvent.VK_W) {
				player.up = true;
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				player.down = true;
			}

			if (e.getKeyCode() == KeyEvent.VK_X) {
				if (gameState.equals("NORMAL"))
					player.shoot = true;
			}

			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				if (gameState.equals("NORMAL"))
					player.jump = true;

			}

			if (e.getKeyCode() == KeyEvent.VK_P) {
				if (gameState.equals("NORMAL")) {
					this.saveGame = true;
				}
			}

			if (e.getKeyCode() == KeyEvent.VK_R) {
				if (gameState.equals("NORMAL")) {
					player.useItem = true;
				}
			}

			if (e.getKeyCode() == KeyEvent.VK_F) {
				if (gameState.equals("NORMAL"))
					player.dropItem = true;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_G) {
				if (gameState.equals("NORMAL")) {
					if(!player.openOven)
						player.getItem = true;
				}
			}

			if (e.getKeyCode() == KeyEvent.VK_Q) {
				if (gameState.equals("NORMAL"))
					sysInv.scrollItemLef = true;
			}

			if (e.getKeyCode() == KeyEvent.VK_E) {
				if (gameState.equals("NORMAL"))
					sysInv.scrollItemDir = true;
			}

			if (e.getKeyCode() == KeyEvent.VK_B) {

				if (gameState.equals("NORMAL")) {
					if (!player.creation && !player.openLvls && !player.build && !Game.player.openOven &&
							 !Game.sysBuild.building && !Game.sysDestruct.destruct) {
						if (!player.useBag)
							player.useBag = true;
						else
							player.useBag = false;
						Sound.Clips.openBag.play();
					}
				}

			}

			if (e.getKeyCode() == KeyEvent.VK_K) {
				if (gameState.equals("NORMAL")) {
					if (!player.useBag && !player.openLvls && !player.build && !Game.player.openOven &&
							!Game.sysBuild.building && !Game.sysDestruct.destruct) {
						if (!player.creation)
							player.creation = true;
						else
							player.creation = false;
						Sound.Clips.selectedInventory.play();
					}
				}
			}

			if (e.getKeyCode() == KeyEvent.VK_Z) {
				if (gameState.equals("NORMAL")) {
					if (!player.creation && !player.useBag && !enterWorld && !Game.player.openOven)
						player.enter = true;
				}
			}

//			if(e.getKeyCode() == KeyEvent.VK_ENTER) { 
//				
//				if(npc.showMessage) 
//					npc.showMessage = false;
//			}
		}

		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			if (gameState.equals("MENU")) {
				menu.up = true;
				Sound.Clips.selected.play();
			}

		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			if (gameState.equals("MENU")) {
				menu.down = true;
				Sound.Clips.selected.play();
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

		if (estado_cena == jogando) {

			if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
				player.rigth = false;
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
				player.left = false;
			}

			if (e.getKeyCode() == KeyEvent.VK_W) {
				player.up = false;
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				player.down = false;
			}

			if (e.getKeyCode() == KeyEvent.VK_R) {				
				player.useItem = false;
			}

			if (e.getKeyCode() == KeyEvent.VK_F) {
				player.dropItem = false;
			}

			if (e.getKeyCode() == KeyEvent.VK_G) {
				player.getItem = false;
			}

			if (e.getKeyCode() == KeyEvent.VK_Q) {
				sysInv.scrollItemLef = false;
			}

			if (e.getKeyCode() == KeyEvent.VK_Z) {
					player.enter = false;
			}

		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			restartGame = true;

			if (gameState.equals("MENU") || gameState.equals("CONTROLES"))
				menu.enter = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			
			Game.ui.buttonEsc = false;

			if (gameState.equals("NORMAL")) {
				if(!sysBuild.building && !sysDestruct.destruct) {
					gameState = "MENU";
					Menu.pause = true;
				}else {
					sysBuild.building = false;
					sysBuild.index = -1;
					sysDestruct.destruct = false;
				}
			} else if (gameState.equals("MENU")) {
				gameState = "NORMAL";
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		player.mouseShoot = true;
		player.mx = e.getX() / 3;
		player.my = e.getY() / 3;

		player.mxShoot = e.getX() / 5.4;
		player.myShoot = e.getY() / 4.7;

//		System.out.println("x: " + player.mx + "    y:" + player.my);

		if (gameState.equals("NORMAL")) {
			
			//Aba levels
			if (!player.useBag && !player.creation && !player.build && !player.openLvls &&  !Game.player.openOven &&
					!sysBuild.building && !sysDestruct.destruct &&
					player.mx >= 15 && player.my >= 48 && player.mx <= 41 && player.my <= 69) {
				player.openLvls = true;
				player.offLvls = false;
				player.mouseShoot = false;
				Sound.Clips.selectedInventory.play();

			} else if (!player.offLvls && 
					player.mx >= 15 && player.my >= 48 && player.mx <= 41 && player.my <= 69) {
				player.offLvls = true;
				player.openLvls = false;
				player.mouseShoot = false;
				Sound.Clips.selectedInventory.play();
			}

			//Mapa
			if (!player.openMap && !sysBuild.building && !sysDestruct.destruct &&
					player.mx >= 15 && player.my >= 69 && player.mx <= 41 && player.my <= 88) {
				player.openMap = true;
				player.offMap = false;
				player.mouseShoot = false;
				Sound.Clips.paper.play();

			} else if (!player.offMap && !sysBuild.building &&
					player.mx >= 15 && player.my >= 69 && player.mx <= 41 && player.my <= 88) {
				player.offMap = true;
				player.openMap = false;
				player.mouseShoot = false;
				Sound.Clips.paper.play();

			}
			
			//Aba Build
			if (!player.build && !player.useBag && !player.creation && !player.openLvls && !sysBuild.building &&
					!sysDestruct.destruct &&  !Game.player.openOven &&
					player.mx >= 15 && player.my >= 89 && player.mx <= 41 && player.my <= 109) {
				if(!Game.mapaGame.equals(Mapa.MAPA_ROOM_HOUSE_01)) {
					player.build = true;
					player.mouseShoot = false;
					modeConstruction = true;
					sysBuild.ajustaPosicaoPlayerMapa();
					verificaUnlockedBuilding();
				}else {
					sysBuild.message = "Voc? n?o pode construir aqui.";
				}
				
				Sound.Clips.selectedInventory.play();

			} else if (player.build && player.mx >= 15 && player.my >= 89 && player.mx <= 41 && player.my <= 109) {
				player.build = false;
				player.clickBuildDescription = false;
				player.indexDescription = -1;
				player.mouseShoot = false;
				Sound.Clips.selectedInventory.play();

			}
			
			//Bot?o de remover
			if (!player.build && !player.useBag && !player.creation && !player.openLvls && 
					!Game.sysDestruct.destruct &&  !Game.player.openOven &&
					player.mx >= 15 && player.my >= 110 && player.mx <= 41 && player.my <= 130) {
				
				if(!Game.mapaGame.equals(Mapa.MAPA_ROOM_HOUSE_01)) {
					modeDesreuct = true;
					player.mouseShoot = false;
					player.clickDestruct = true;
					sysDestruct.ajustaPosicaoPlayerMapa();
				}else {
					sysBuild.message = "N?o h? nada para destruir aqui...";
				}
				
				Sound.Clips.selectedInventory.play();
			}
			
		}

		if (player.useBag) {

			player.mouseShoot = false;

			// inventario
			checkClickInv();

			int[] index = { -1, -1 };
			// Mascaras da mochila
			if (player.mx >= 164 && player.my >= 32 && player.mx <= 194 && player.my <= 56) {
				index[0] = 0;
				index[1] = 0;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;

			} else if (player.mx >= 200 && player.my >= 32 && player.mx <= 230 && player.my <= 56) {
				index[0] = 0;
				index[1] = 1;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;

			} else if (player.mx >= 235 && player.my >= 32 && player.mx <= 266 && player.my <= 56) {
				index[0] = 0;
				index[1] = 2;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;

			} else if (player.mx >= 271 && player.my >= 32 && player.mx <= 303 && player.my <= 56) {
				index[0] = 0;
				index[1] = 3;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;

			} else if (player.mx >= 164 && player.my >= 60 && player.mx <= 194 && player.my <= 84) {
				index[0] = 1;
				index[1] = 0;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;

			} else if (player.mx >= 200 && player.my >= 60 && player.mx <= 230 && player.my <= 84) {
				index[0] = 1;
				index[1] = 1;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;

			} else if (player.mx >= 235 && player.my >= 60 && player.mx <= 266 && player.my <= 84) {
				index[0] = 1;
				index[1] = 2;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;

			} else if (player.mx >= 271 && player.my >= 60 && player.mx <= 303 && player.my <= 84) {
				index[0] = 1;
				index[1] = 3;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;

			} else if (player.mx >= 164 && player.my >= 89 && player.mx <= 194 && player.my <= 113) {
				index[0] = 2;
				index[1] = 0;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;

			} else if (player.mx >= 200 && player.my >= 89 && player.mx <= 230 && player.my <= 113) {
				index[0] = 2;
				index[1] = 1;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;

			} else if (player.mx >= 235 && player.my >= 89 && player.mx <= 266 && player.my <= 113) {
				index[0] = 2;
				index[1] = 2;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;

			} else if (player.mx >= 271 && player.my >= 89 && player.mx <= 303 && player.my <= 113) {
				index[0] = 2;
				index[1] = 3;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;

			} else if (player.mx >= 164 && player.my >= 118 && player.mx <= 194 && player.my <= 142) {
				index[0] = 3;
				index[1] = 0;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;

			} else if (player.mx >= 200 && player.my >= 118 && player.mx <= 230 && player.my <= 142) {
				index[0] = 3;
				index[1] = 1;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;

			} else if (player.mx >= 235 && player.my >= 118 && player.mx <= 266 && player.my <= 142) {
				index[0] = 3;
				index[1] = 2;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;

			} else if (player.mx >= 271 && player.my >= 118 && player.mx <= 303 && player.my <= 142) {
				index[0] = 3;
				index[1] = 3;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;

			} else if (player.mx >= 164 && player.my >= 147 && player.mx <= 194 && player.my <= 171) {
				index[0] = 4;
				index[1] = 0;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;

			} else if (player.mx >= 200 && player.my >= 147 && player.mx <= 230 && player.my <= 171) {
				index[0] = 4;
				index[1] = 1;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;

			} else if (player.mx >= 235 && player.my >= 147 && player.mx <= 266 && player.my <= 171) {
				index[0] = 4;
				index[1] = 2;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;

			} else if (player.mx >= 271 && player.my >= 147 && player.mx <= 303 && player.my <= 171) {
				index[0] = 4;
				index[1] = 3;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;

			} else if (player.mx >= 164 && player.my >= 176 && player.mx <= 194 && player.my <= 200) {
				index[0] = 5;
				index[1] = 0;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;

			} else if (player.mx >= 200 && player.my >= 176 && player.mx <= 230 && player.my <= 200) {
				index[0] = 5;
				index[1] = 1;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;

			} else if (player.mx >= 235 && player.my >= 176 && player.mx <= 266 && player.my <= 200) {
				index[0] = 5;
				index[1] = 2;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;

			} else if (player.mx >= 271 && player.my >= 176 && player.mx <= 303 && player.my <= 200) {
				index[0] = 5;
				index[1] = 3;
				sysBag.checkClickPositionItemBag(index);
				player.clickBag = true;

			}
		}

		if (player.creation) {

			player.mouseShoot = false;

			// inventario
			checkClickInv();

			// mascaras do Creation
			if (player.mx >= 168 && player.my >= 97 && player.mx <= 198 && player.my <= 121) {
				sysCre.checkClickPositionItemCreation(0);
				player.clickCreation = true;

			} else if (player.mx >= 203 && player.my >= 97 && player.mx <= 234 && player.my <= 121) {
				sysCre.checkClickPositionItemCreation(1);
				player.clickCreation = true;

			} else if (player.mx >= 167 && player.my >= 125 && player.mx <= 198 && player.my <= 149) {
				sysCre.checkClickPositionItemCreation(2);
				player.clickCreation = true;

			} else if (player.mx >= 203 && player.my >= 125 && player.mx <= 234 && player.my <= 149) {
				sysCre.checkClickPositionItemCreation(3);
				player.clickCreation = true;

			} else if (player.mx >= 251 && player.my >= 111 && player.mx <= 282 && player.my <= 134) {
				sysCre.checkClickPositionItemCreation(4);
				player.clickCraft = true;
			}

		}
		
		if(player.build) {
			// mascaras do build
			
			sysBuild.message = null;
			
			if (player.mx >= 126 && player.my >= 60 && player.mx <= 156 && player.my <= 84) { //1
				if(Mine.unlocked && e.getButton() == MouseEvent.BUTTON1) {
					sysBuild.index = 0;
				}else {
					if (e.getButton() != MouseEvent.BUTTON3) 
						sysBuild.message = "Este recurso est? bloaqueado!";
				}
				
				if (e.getButton() == MouseEvent.BUTTON3) {
					player.clickBuildDescription = true;
					player.indexDescription = 0;
				}
				
			} else if (player.mx >= 162 && player.my >= 55 && player.mx <= 192 && player.my <= 84) { //2
				if(Thorn.unlocked && e.getButton() == MouseEvent.BUTTON1) {
					sysBuild.index = 1;
				}else {
					if (e.getButton() != MouseEvent.BUTTON3) 
						sysBuild.message = "Este recurso est? bloaqueado!";
				}
				
				if (e.getButton() == MouseEvent.BUTTON3) {
					player.clickBuildDescription = true;
					player.indexDescription = 1;
				}
				
			} else if (player.mx >= 198 && player.my >= 60 && player.mx <= 228 && player.my <= 84) { //3
				
				if(MineLand.unlocked && e.getButton() == MouseEvent.BUTTON1) {
					sysBuild.index = 2;
				}else {
					if (e.getButton() != MouseEvent.BUTTON3) 
						sysBuild.message = "Este recurso est? bloaqueado!";
				}
				
				if (e.getButton() == MouseEvent.BUTTON3) {
					player.clickBuildDescription = true;
					player.indexDescription = 2;
				}
				
			} else if (player.mx >= 234 && player.my >= 60 && player.mx <= 264 && player.my <= 84) { //4
				
			} else if (player.mx >= 270 && player.my >= 60 && player.mx <= 301 && player.my <= 84) { //5
				
			} else if (player.mx >= 306 && player.my >= 60 && player.mx <= 337 && player.my <= 84) { //6
				
			}
			
			
			else if (player.mx >= 126 && player.my >= 88 && player.mx <= 156 && player.my <= 113) { //7
				
			}else if (player.mx >= 162 && player.my >= 88 && player.mx <= 192 && player.my <= 113) { //8
				
			}else if (player.mx >= 198 && player.my >= 88 && player.mx <= 228 && player.my <= 113) { //9
				
			}else if (player.mx >= 234 && player.my >= 88 && player.mx <= 264 && player.my <= 113) { //10
				
			}else if (player.mx >= 270 && player.my >= 88 && player.mx <= 301 && player.my <= 113) { //11
				
			}else if (player.mx >= 306 && player.my >= 88 && player.mx <= 337 && player.my <= 113) { //12
				
			}
			
			
			else if (player.mx >= 126 && player.my >= 117 && player.mx <= 156 && player.my <= 141) { //13
				
			}else if (player.mx >= 162 && player.my >= 117 && player.mx <= 192 && player.my <= 141) { //14
				
			}else if (player.mx >= 198 && player.my >= 117 && player.mx <= 228 && player.my <= 141) { //15
				
			}else if (player.mx >= 234 && player.my >= 117 && player.mx <= 264 && player.my <= 141) { //16
				
			}else if (player.mx >= 270 && player.my >= 117 && player.mx <= 301 && player.my <= 141) { //17
				
			}else if (player.mx >= 306 && player.my >= 117 && player.mx <= 337 && player.my <= 141) { //18
				
			}
		}
		
		if(player.openOven) {
			
			player.mouseShoot = false;
			
			// inventario
			checkClickInv();
			
			if (player.mx >= 210 && player.my >= 143 && player.mx <= 240 && player.my <= 166) { //7
				sysOvenBonfire.checkClickPositionOvenBonfire(0);
				player.clickOvenBonfire = true;

			}else if (player.mx >= 210 && player.my >= 111 && player.mx <= 240 && player.my <= 135) { //8
				sysOvenBonfire.checkClickPositionOvenBonfire(1);
				player.clickOvenBonfire = true;
				
			}else if (player.mx >= 210 && player.my >= 68 && player.mx <= 240 && player.my <= 91) { //9
				sysOvenBonfire.checkClickPositionOvenBonfire(2);
				player.clickCraftOvenBonfire = true;
			}
		}
		
		if(sysBuild.building) {
			player.clickConstruction = true;
			sysBuild.xTarget = (int)(e.getX() / 5.7);
			sysBuild.yTarget = (int)(e.getY() / 4.7);
		}
		
		if(sysDestruct.destruct) {
			player.clickDestruction = true;
			sysDestruct.xTarget = (int)(e.getX() / 5.7);
			sysDestruct.yTarget = (int)(e.getY() / 4.7);
		}


	}
	
	public void checkClickInv() {
		// Mascaras do inventario
		if (player.mx >= 143 && player.my >= 223 && player.mx <= 174 && player.my <= 247) {
			sysInv.checkClickPositionItemInv(0);
			player.clickInv = true;

		} else if (player.mx >= 178 && player.my >= 223 && player.mx <= 210 && player.my <= 247) {
			sysInv.checkClickPositionItemInv(1);
			player.clickInv = true;

		} else if (player.mx >= 214 && player.my >= 223 && player.mx <= 246 && player.my <= 247) {
			sysInv.checkClickPositionItemInv(2);
			player.clickInv = true;

		} else if (player.mx >= 250 && player.my >= 223 && player.mx <= 282 && player.my <= 247) {
			sysInv.checkClickPositionItemInv(3);
			player.clickInv = true;

		} else if (player.mx >= 287 && player.my >= 223 && player.mx <= 318 && player.my <= 247) {
			sysInv.checkClickPositionItemInv(4);
			player.clickInv = true;

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

		if (player != null) {
			player.moveMx = ((e.getX() / 5.7));
			player.moveMy = ((e.getY() / 4.7));
			
//			if(sysBuild != null) {
//				sysBuild.xTarget = (int)(e.getX() / 5.7);
//				sysBuild.yTarget = (int)(e.getY() / 4.7);
//			}

		}
	}

}
