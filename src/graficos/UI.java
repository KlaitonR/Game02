package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import entities.NPC.Npc;
import main.Game;
import main.Menu;
import world.Camera;

public class UI {
	
	private BufferedImage [] button;
	public BufferedImage [] UI;
	public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("fonts/pixelfont.ttf");
	public InputStream stream02 = ClassLoader.getSystemClassLoader().getResourceAsStream("fonts/pixelfont.ttf");
	public Font newfont;
	public Font fontSystemCreat;
	static public Spritsheet spriteUI;
	public boolean buttonColletct;
	public boolean buttonEnterRoom;
	public boolean buttonUseItem;
	public boolean buttonEsc;
	
	public UI(Spritsheet spritButton) {
		
		spriteUI =  new Spritsheet("/spritesSheet/spriteUI.png");
		UI = new BufferedImage[21];
		
		UI[0] = spriteUI.getSprite(0, 0, 128, 48); //Inventario
		UI[1] = spriteUI.getSprite(0, 48, 32, 32); //Seleção do inventário
		UI[2] = spriteUI.getSprite(0, 80, 80, 128); //Mochila
		UI[3] = spriteUI.getSprite(32, 48, 32, 32); //Selecção da mochila
		UI[4] = spriteUI.getSprite(64, 48, 16, 16); //Icone nível
		UI[5] = spriteUI.getSprite(80, 48, 16, 16); //Icone mapa
		UI[6] = spriteUI.getSprite(0, 208, 80, 96); //Aba de níveis
		UI[7] = spriteUI.getSprite(0, 304, 80, 48); //Craft
		UI[8] = spriteUI.getSprite(64, 64, 16, 16); //Icone Munição
		UI[9] = spriteUI.getSprite(0, 368, 80, 16); //Life Player
		UI[10] = spriteUI.getSprite(80, 64, 16, 16); //Button R
		UI[11] = spriteUI.getSprite(96, 64, 16, 16); //Button G
		UI[12] = spriteUI.getSprite(112, 64, 16, 16); //Button Z
		UI[13] = spriteUI.getSprite(80, 80, 16, 16); //Dinheiro $
		UI[14] = spriteUI.getSprite(128, 0, 48, 80); //Forno
		UI[15] = spriteUI.getSprite(96, 48, 16, 16); //Icone Build
		UI[16] = spriteUI.getSprite(112, 48, 16, 16); //Icone Remover
		UI[17] = spriteUI.getSprite(176, 0, 128, 112); //Aba Build Constructions
		UI[18] = spriteUI.getSprite(96, 80, 16, 16); //Icone de informação
		UI[19] = spriteUI.getSprite(160, 80, 16, 16); //Forno ligado
		UI[20] = spriteUI.getSprite(112, 80, 16, 16); //Button ESC
		
		button = new BufferedImage[2];
		button[0] = Game.spriteButton.getSprite(0, 0, 5, 5);
		button[1] = Game.spriteButton.getSprite(0, 5, 5, 5);
		
		try {
			newfont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(14f);
			fontSystemCreat = Font.createFont(Font.TRUETYPE_FONT, stream02).deriveFont(22f);
		}catch (FontFormatException f) {
			f.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	private void timeSystem(Graphics g) {
		if(Game.gameState.equals("NORMAL") || Menu.pause) {
			g.setFont(new Font("arial", Font.BOLD, 9));
			g.setColor(Color.white);
			
			if(Game.sysTime.hour < 10 && Game.sysTime.minute < 10) 
				g.drawString("0" + Game.sysTime.hour + ":0" + Game.sysTime.minute,  7,  25 );
			if(Game.sysTime.hour >= 10 && Game.sysTime.minute < 10) 
				g.drawString(Game.sysTime.hour + ":0" + Game.sysTime.minute,  7,  25 );
			if(Game.sysTime.hour < 10 && Game.sysTime.minute >= 10) 
				g.drawString("0" + Game.sysTime.hour + ":" + Game.sysTime.minute,  7,  25 );
			if(Game.sysTime.hour >= 10 && Game.sysTime.minute >= 10) 
				g.drawString(Game.sysTime.hour + ":" + Game.sysTime.minute,  7,  25 );
			
			g.drawString("Day " + Game.sysTime.days, 45, 25);
			
		}
	}
	
	private void invSystem(Graphics g) {
		
		g.drawImage(UI[0], 58 ,123 , null);
		
		if(Game.sysInv.handItem != null) {
			g.setFont(newfont);
			g.setColor(Color.white);
			
			//Ajudar a centralizar o texto
		
			g.drawString(Game.sysInv.handItem.tipo, 121 - Game.sysInv.handItem.tipo.length()*2, 135);
			
		}
				
		//Apenas renderizando oque foi colocado no buffer do inventario
		for(int i=0; i < Game.sysInv.inv.length; i++) {
					
			g.drawImage(Game.sysInv.inv[i], 76 + (i*19), 139, null);
			
			if(Game.sysInv.inv[i] != null &&
				Game.sysInv.inventario[i] != null &&
				Game.sysInv.inventario[i].itensPack.size()+1 > 1 ){
					g.setFont(new Font("arial", Font.BOLD, 9));
					g.setColor(Color.white);
					g.drawString((Game.sysInv.inventario[i].itensPack.size() + 1) + "", 76 + (i*19), 154);
			}
					
			if(Game.sysInv.handIndexItem == i) { //Indica qual item está na mão do Player
				g.drawImage(UI[1], 76 + (i*19) ,140 , null);
			}		
		}
	}
	
	private void lifePlayer(Graphics g) {

		g.setColor(Color.red);
		g.fillRect(8, 4, 65, 8);
		g.setColor(Color.green);
		g.fillRect(8, 4, (int)((Game.player.life/Game.player.maxLife)*65), 8);
//		g.setFont(newfont);
//		g.setColor(Color.white);
//		g.drawString((int)Game.player.life + "/" + (int)Game.player.maxLife, 25, 11);
		
//		g.setFont(newfont);
//		g.setColor(Color.white);
//		g.drawString((int)Game.player.life + "/" + (int)Game.player.maxLife, 25, 11);
		
		g.drawImage(UI[9], 3, 1, null);
		
	}
	
	public void lifeMobs(Graphics g) {
		
		for(int i=0; i<Game.mobs.size();i++) {
			if(Game.mobs.get(i).mapa.contains(Game.mapaGame)) {
				g.setColor(Color.black); 
				g.fillRect((int)Game.mobs.get(i).x + 2 - Camera.x, (int)Game.mobs.get(i).y - 5 - Camera.y, 12, 3);
				g.setColor(Color.red);
				g.fillRect((int)Game.mobs.get(i).x + 3 - Camera.x, (int)Game.mobs.get(i).y - 4 - Camera.y, 10, 1);
				g.setColor(Color.green);
				g.fillRect((int)Game.mobs.get(i).x + 3 - Camera.x, (int)Game.mobs.get(i).y - 4 - Camera.y, (int)((Game.mobs.get(i).life/Game.mobs.get(i).maxLife)*10), 1);
			}
		}
	}
	
	private void levelTab(Graphics g) {
		
		g.drawImage(UI[4], 7, 28, null);
		g.drawImage(UI[5], 7, 41, null);
		g.drawImage(UI[15], 7, 54, null);
		g.drawImage(UI[16], 7, 67, null);
			
		if(Game.player.openLvls && !Game.player.offLvls) {
			
			g.drawImage(UI[6], 23 ,29 , null);
				
			//attack
			g.setColor(Color.black);
			g.fillRect(58, 44, 40, 5);
			g.setColor(new Color(93,128,158));
			g.fillRect(59, 45, 38, 3);
			g.setColor(Color.yellow);
			
			g.setFont(new Font("arial", Font.BOLD, 7));
			g.setColor(Color.white);
			g.drawString("" + (int)Game.player.exp + "/" + (int)Game.player.maxExp[Game.player.levelPlayer], 37, 49);
			
			double dif;
			dif = Game.player.maxExp[Game.player.levelPlayer] - Game.player.exp;
			
			if(dif <= Game.player.maxExp[Game.player.levelPlayer] && dif > 0){
				g.fillRect(58, 65,(int)((Game.player.exp/Game.player.maxExp[Game.player.levelPlayer])*38), 3);
			}else {
				g.fillRect(58, 65,(int)(((Game.player.exp + dif)/Game.player.maxExp[Game.player.levelPlayer])*38), 3);
			}
			
			//WoodCutting
			g.setColor(Color.black);
			g.fillRect(58, 55, 40, 5);
			g.setColor(new Color(93,128,158));
			g.fillRect(59, 56, 38, 3);
			g.setColor(Color.yellow);
			
			g.setFont(new Font("arial", Font.BOLD, 7));
			g.setColor(Color.white);
			g.drawString("" + (int)Game.player.expWoodCutting + "/" + (int)Game.player.maxExp[Game.player.levelWoodCutting], 37, 60);
			
			double dif2;
			dif2 = Game.player.maxExp[Game.player.levelWoodCutting] - Game.player.expWoodCutting;
			
			if(dif2 <= Game.player.maxExp[Game.player.levelWoodCutting] && dif2 > 0) {
				g.fillRect(58, 56,(int)((Game.player.expWoodCutting/Game.player.maxExp[Game.player.levelWoodCutting])*39), 3);
			}else {
				g.fillRect(58, 56,(int)(((Game.player.expWoodCutting + dif2)/Game.player.maxExp[Game.player.levelWoodCutting])*39), 3);
			}
			
//			System.out.println(Game.player.levelWoodCutting);
			System.out.println(Game.player.expWoodCutting);
//			System.out.println(Game.player.expWoodCuttingTtl);
			
			g.setColor(Color.black);
			g.fillRect(58, 66, 40, 5);
			g.setColor(new Color(93,128,158));
			g.fillRect(59, 67, 38, 3);
			g.setColor(Color.yellow);
			
			g.setFont(new Font("arial", Font.BOLD, 7));
			g.setColor(Color.white);
			g.drawString("" + (int)Game.player.exp + "/" + (int)Game.player.maxExp[Game.player.levelPlayer], 37, 71);
			
			g.setColor(Color.black);
			g.fillRect(58, 77, 40, 5);
			g.setColor(new Color(93,128,158));
			g.fillRect(59, 78, 38, 3);
			g.setColor(Color.yellow);
			
			g.setFont(new Font("arial", Font.BOLD, 7));
			g.setColor(Color.white);
			g.drawString("" + (int)Game.player.exp + "/" + (int)Game.player.maxExp[Game.player.levelPlayer], 37, 82);
			
			g.setColor(Color.black);
			g.fillRect(58, 87, 40, 5);
			g.setColor(new Color(93,128,158));
			g.fillRect(59, 88, 38, 3);
			g.setColor(Color.yellow);
			
			g.setFont(new Font("arial", Font.BOLD, 7));
			g.setColor(Color.white);
			g.drawString("" + (int)Game.player.exp + "/" + (int)Game.player.maxExp[Game.player.levelPlayer], 37, 92);
			
			g.setColor(Color.black);
			g.fillRect(58, 97, 40, 5);
			g.setColor(new Color(93,128,158));
			g.fillRect(59, 98, 38, 3);
			g.setColor(Color.yellow);
			
			g.setFont(new Font("arial", Font.BOLD, 7));
			g.setColor(Color.white);
			g.drawString("" + (int)Game.player.exp + "/" + (int)Game.player.maxExp[Game.player.levelPlayer], 37, 102);
			
			g.setColor(Color.black);
			g.fillRect(58, 107, 40, 5);
			g.setColor(new Color(93,128,158));
			g.fillRect(59, 108, 38, 3);
			g.setColor(Color.yellow);
			
			g.setFont(new Font("arial", Font.BOLD, 7));
			g.setColor(Color.white);
			g.drawString("" + (int)Game.player.exp + "/" + (int)Game.player.maxExp[Game.player.levelPlayer], 37, 112);
			
			g.setFont(new Font("arial", Font.BOLD, 7));
			g.setColor(new Color(255,157,0));
			g.drawString("" + (int)(Game.player.levelPlayer), 54, 122);
			
		}else {
			Game.player.offLvls = false;
		}
	}
	
	private void abaBuild(Graphics g) {
		
		if(Game.player.build) {
			g.drawImage(UI[17], 65, 30, null);
			g.drawImage(Game.sysBuild.buildImage[0], 67, 38, null);
			g.drawImage(Game.sysBuild.buildImage[1], 86, 38, null);
			g.drawImage(Game.sysBuild.buildImage[2], 105, 38, null);
			
			if(Game.player.clickBuildDescription) {
				g.drawImage(UI[18], 64, 90, null);
				g.drawImage(UI[13], 65, 114, null);
			}
		}
	}
	
//	public void sysBuild(Graphics g){
//		
//		Graphics2D g2 = (Graphics2D) g;
//		g2.setColor(new Color(48, 204, 40, 150));
//		if(Game.sysBuild.building)
//			g2.fillRect(Game.sysBuild.xTarget, Game.sysBuild.yTarget, 16, 16);
//		
//	}
	
	public void systemBag(Graphics g) {
		
		g.drawImage(UI[2], 85 , 8 , null);
		
		//Apenas renderizando oque foi colocado no buffer do inventario
		for(int j=0; j < 6; j++) {
			for(int i=0; i < 4; i++) {
				g.drawImage(Game.sysBag.bag[i][j], 87 + (i*19), 19 + (j*18), null);
				
				if(Game.sysBag.bag[i][j] != null &&
					Game.sysBag.bagpack[i][j] != null &&
					Game.sysBag.bagpack[i][j].itensPack.size()+1 > 1 ){
					g.setFont(new Font("arial", Font.BOLD, 9));
					g.setColor(Color.white);
					g.drawString((Game.sysBag.bagpack[i][j].itensPack.size() + 1) + "", 87 + (i*19), 35 + (j*18));
				}
			}
		}
	
		int [] index = Game.sysBag.getIndexClickBag();
		if(index != null)
			g.drawImage(UI[3], 87 + (index[1]*19), 20 + (index[0]*18) , null);
		
	}
	
	public void systemCreation(Graphics g) {
		
		g.drawImage(UI[7], 85 , 50, null);	
		
		//Apenas renderizando oque foi colocado no buffer do inventari
							
		g.drawImage(Game.sysCre.itens[0], 89, 60, null);
		
		g.drawImage(Game.sysCre.itens[1], 108, 60, null);
		
		g.drawImage(Game.sysCre.itens[2], 89, 78, null);
		
		g.drawImage(Game.sysCre.itens[3], 108, 78, null);
		
		g.drawImage(Game.sysCre.itens[4], 133, 69, null);
					
		if(Game.sysCre.slot[0] != null &&
				Game.sysCre.slot[0] != null &&
						Game.sysCre.slot[0].itensPack.size()+1 > 1 ){
			g.setFont(new Font("arial", Font.BOLD, 9));
			g.setColor(Color.white);
			g.drawString((Game.sysCre.slot[0].itensPack.size() + 1) + "", 89, 75);
		}
		
		if(Game.sysCre.slot[1] != null &&
				Game.sysCre.slot[1] != null &&
						Game.sysCre.slot[1].itensPack.size()+1 > 1 ){
			g.setFont(new Font("arial", Font.BOLD, 9));
			g.setColor(Color.white);
			g.drawString((Game.sysCre.slot[1].itensPack.size() + 1) + "", 108, 75);
		}
		
		if(Game.sysCre.slot[2] != null &&
				Game.sysCre.slot[2] != null &&
						Game.sysCre.slot[2].itensPack.size()+1 > 1 ){
			g.setFont(new Font("arial", Font.BOLD, 9));
			g.setColor(Color.white);
			g.drawString((Game.sysCre.slot[2].itensPack.size() + 1) + "", 89, 93);
		}
		
		if(Game.sysCre.slot[3] != null &&
				Game.sysCre.slot[3] != null &&
						Game.sysCre.slot[3].itensPack.size()+1 > 1 ){
			g.setFont(new Font("arial", Font.BOLD, 9));
			g.setColor(Color.white);
			g.drawString((Game.sysCre.slot[3].itensPack.size() + 1) + "", 108, 93);
		}
		
		if(Game.sysCre.slot[4] != null &&
				Game.sysCre.slot[4] != null &&
						Game.sysCre.slot[4].itensPack.size()+1 > 1 ){
			g.setFont(new Font("arial", Font.BOLD, 9));
			g.setColor(Color.white);
			g.drawString((Game.sysCre.slot[4].itensPack.size() + 1) + "", 157, 106);
		}
		
	}
	
	public void systemDialog(Graphics g) {
		if(Npc.showMessage) {
			
			Game.player.useBag = false;
			Game.player.creation = false;
			
			g.setColor(Color.black);
			g.fillRect(48, 90, 146, 35);
			g.setColor(Color.white);
			g.fillRect(49, 91, 144, 33);
			g.setColor(Color.black);
			g.fillRect(50, 92, 142, 31);
			
			g.setColor(Color.white);
			g.setFont(new Font("Arial", Font.BOLD, 9));
			g.drawString(Npc.frases[Npc.fraseIndex].substring(0, Npc.curIndexMsg), 58, 109);
//			g.setFont(new Font("Arial", Font.BOLD, 9));
//			g.drawString("> ENTER par fechar <", 55, 115);
		}
	}
	
	public void sysOven(Graphics g) {
		
		g.drawImage(UI[14], 95, 40, null);
		
		if(Game.sysOvenBonfire.imageSlot[2] != null)
			g.drawImage(UI[19], 111, 56, null);
		
		g.drawImage(Game.sysOvenBonfire.imageSlot[0], 111, 89, null);
		g.drawImage(Game.sysOvenBonfire.imageSlot[1], 111, 69, null);
		g.drawImage(Game.sysOvenBonfire.imageSlot[2], 111, 42, null);
		
		g.setFont(new Font("arial", Font.BOLD, 9));
		g.setColor(Color.white);
		
		if(Game.sysOvenBonfire.slot[0] != null &&
						Game.sysOvenBonfire.slot[0].itensPack.size()+1 > 1 ){
			g.drawString((Game.sysOvenBonfire.slot[0].itensPack.size() + 1) + "", 111, 105);
			
		}
		
		if(Game.sysOvenBonfire.slot[1] != null &&
				Game.sysOvenBonfire.slot[1].itensPack.size()+1 > 1 ){
				g.drawString((Game.sysOvenBonfire.slot[1].itensPack.size() + 1) + "", 111, 85);
	
		}
		
		if(Game.sysOvenBonfire.slot[2] != null ){
				g.drawString((Game.sysOvenBonfire.slot[2].itensPack.size() + 1) + "", 111, 57);
	
		}
		
		
	}
	
	public void render(Graphics g) {
		
		lifeMobs(g);
		
		g.drawImage(UI[8], 203, 1, null);
		g.setFont(new Font("arial", Font.BOLD, 8));
		g.setColor(Color.black);
		g.drawString("" + Game.player.ammo, 221, 8);
		g.setColor(Color.yellow);
		g.drawString("" + Game.player.ammo, 220, 8);
		
		g.drawImage(UI[13], 201, 10, null); 
		g.setFont(new Font("arial", Font.BOLD, 8));
		g.setColor(Color.black);
		g.drawString("" + Game.player.money, 215, 19);
		g.setColor(new Color(0,210,25));
		g.drawString("" + Game.player.money, 214, 19);
		
//		g.setColor(Color.darkGray); 
//		g.drawString("Level " + Game.CUR_LEVEL, 10, 745);

		lifePlayer(g);
		timeSystem(g);
		invSystem(g);
		levelTab(g);
		abaBuild(g);
//		sysBuild(g);
		systemDialog(g);
		
//		Game.abaOvenAndBonfire = false;
//		Game.player.openOven = false;
//		
//		Game.abaOvenAndBonfire = true;
//		Game.player.openOven = true;
		if(Game.player.openOven)
			sysOven(g);
		
		
		if(Game.player.useBag)
			systemBag(g);
		
		if(Game.player.creation)
			systemCreation(g);
		
		if(buttonColletct) {
			g.drawImage(UI[11], 220, 140, null);
		}
		
		if (buttonUseItem) {
			g.drawImage(UI[10], 220, 140, null);
		}
		
		if (buttonEnterRoom) {
			g.drawImage(UI[12], 220, 140, null);
		}
		
		if(buttonEsc) {
			g.drawImage(UI[20], 220, 140, null);
		}
	
	}
}
