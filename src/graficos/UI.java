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
	private BufferedImage [] UI;
	public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("fonts/pixelfont.ttf");
	public InputStream stream02 = ClassLoader.getSystemClassLoader().getResourceAsStream("fonts/pixelfont.ttf");
	public Font newfont;
	public Font fontSystemCreat;
	static public Spritsheet spriteUI;
	public boolean buttonColletct;
	public boolean buttonEnterRoom;
	public boolean buttonUseItem;
	
	public UI(Spritsheet spritButton) {
		
		spriteUI =  new Spritsheet("/spritesSheet/spriteUI.png");
		UI = new BufferedImage[14];
		
		UI[0] = spriteUI.getSprite(0, 0, 128, 48); //Inventario
		UI[1] = spriteUI.getSprite(0, 48, 32, 32); //Seleção do inventário
		UI[2] = spriteUI.getSprite(0, 80, 80, 128); //Mochila
		UI[3] = spriteUI.getSprite(32, 48, 32, 32); //Selecção da mochila
		UI[4] = spriteUI.getSprite(64, 48, 16, 16); //Icone nível
		UI[5] = spriteUI.getSprite(80, 48, 16, 16); //Icone mapa
		UI[6] = spriteUI.getSprite(0, 208, 80, 96); //Aba de níveis
		UI[7] = spriteUI.getSprite(0, 304, 96, 64); //Craft
		UI[8] = spriteUI.getSprite(64, 64, 16, 16); //Icone Munição
		UI[9] = spriteUI.getSprite(0, 368, 80, 16); //Life Player
		UI[10] = spriteUI.getSprite(80, 64, 16, 16); //Button R
		UI[11] = spriteUI.getSprite(96, 64, 16, 16); //Button G
		UI[12] = spriteUI.getSprite(112, 64, 16, 16); //Button Z
		UI[13] = spriteUI.getSprite(80, 80, 16, 16); //Dinheiro $
		
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
			
		if(Game.player.openLvls && !Game.player.offLvls) {
			
			g.drawImage(UI[6], 23 ,29 , null);
				
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
			
			g.setColor(Color.black);
			g.fillRect(58, 55, 40, 5);
			g.setColor(new Color(93,128,158));
			g.fillRect(59, 56, 38, 3);
			g.setColor(Color.yellow);
			
			g.setFont(new Font("arial", Font.BOLD, 7));
			g.setColor(Color.white);
			g.drawString("" + (int)Game.player.exp + "/" + (int)Game.player.maxExp[Game.player.levelPlayer], 37, 60);
			
			double dif2;
			dif2 = Game.player.maxExp[Game.player.levelPlayer] - Game.player.exp;
			
			if(dif2 <= Game.player.maxExp[Game.player.levelPlayer] && dif2 > 0){
				g.fillRect(58, 65,(int)((Game.player.exp/Game.player.maxExp[Game.player.levelPlayer])*38), 3);
			}else {
				g.fillRect(58, 65,(int)(((Game.player.exp + dif2)/Game.player.maxExp[Game.player.levelPlayer])*38), 3);
			}
			
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
			g.drawString("" + (int)(Game.player.levelPlayer + 1), 54, 122);
			
		}else {
			Game.player.offLvls = false;
		}
	}
	
	public void systemBag(Graphics g) {
		
		g.drawImage(UI[2], 85 ,10 , null);
		
		//Apenas renderizando oque foi colocado no buffer do inventario
		for(int j=0; j < 6; j++) {
			for(int i=0; i < 4; i++) {
				g.drawImage(Game.sysBag.bag[i][j], 87 + (i*19), 20 + (j*18), null);
				
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
			g.drawImage(UI[3], 87 + (index[1]*19), 21 + (index[0]*18) , null);
		
	}
	
	public void systemCreation(Graphics g) {
		
		g.drawImage(UI[7], 75 , 50, null);	
		
		//Apenas renderizando oque foi colocado no buffer do inventari
							
		g.drawImage(Game.sysCre.itens[0], 83, 60, null);
		
		g.drawImage(Game.sysCre.itens[1], 115, 60, null);
		
		g.drawImage(Game.sysCre.itens[2], 83, 90, null);
		
		g.drawImage(Game.sysCre.itens[3], 115, 90, null);
		
		g.drawImage(Game.sysCre.itens[4], 148, 75, null);
					
		if(Game.sysCre.slot[0] != null &&
				Game.sysCre.slot[0] != null &&
						Game.sysCre.slot[0].itensPack.size()+1 > 1 ){
			g.setFont(new Font("arial", Font.BOLD, 9));
			g.setColor(Color.white);
			g.drawString((Game.sysCre.slot[0].itensPack.size() + 1) + "", 83, 75);
		}
		
		if(Game.sysCre.slot[1] != null &&
				Game.sysCre.slot[1] != null &&
						Game.sysCre.slot[1].itensPack.size()+1 > 1 ){
			g.setFont(new Font("arial", Font.BOLD, 9));
			g.setColor(Color.white);
			g.drawString((Game.sysCre.slot[1].itensPack.size() + 1) + "", 115, 75);
		}
		
		if(Game.sysCre.slot[2] != null &&
				Game.sysCre.slot[2] != null &&
						Game.sysCre.slot[2].itensPack.size()+1 > 1 ){
			g.setFont(new Font("arial", Font.BOLD, 9));
			g.setColor(Color.white);
			g.drawString((Game.sysCre.slot[2].itensPack.size() + 1) + "", 83, 105);
		}
		
		if(Game.sysCre.slot[3] != null &&
				Game.sysCre.slot[3] != null &&
						Game.sysCre.slot[3].itensPack.size()+1 > 1 ){
			g.setFont(new Font("arial", Font.BOLD, 9));
			g.setColor(Color.white);
			g.drawString((Game.sysCre.slot[3].itensPack.size() + 1) + "", 115, 105);
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
	
	public void render(Graphics g) {
		
		lifeMobs(g);
		
		g.drawImage(UI[8], 203, 1, null);
		g.setFont(new Font("arial", Font.BOLD, 8));
		g.setColor(Color.black);
		g.drawString("" + Game.player.ammo, 221, 8);
		g.setColor(Color.yellow);
		g.drawString("" + Game.player.ammo, 220, 8);
		
		g.drawImage(UI[13], 207, 10, null); 
		g.setFont(new Font("arial", Font.BOLD, 8));
		g.setColor(Color.black);
		g.drawString("" + 245, 222, 19);
		g.setColor(new Color(0,210,25));
		g.drawString("" + 245, 221, 19);
		
//		g.setColor(Color.darkGray); 
//		g.drawString("Level " + Game.CUR_LEVEL, 10, 745);

		lifePlayer(g);
		timeSystem(g);
		invSystem(g);
		levelTab(g);
		systemDialog(g);
		
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
	
	}
}
