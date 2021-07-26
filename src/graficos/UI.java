package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import entities.Npc;
import main.Game;
import main.Menu;
import main.Sound;
import util.Mapa;
import world.Camera;

public class UI {
	
	private BufferedImage [] button;
	private BufferedImage [] UI;
	public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("pixelfont.ttf");
	public InputStream stream02 = ClassLoader.getSystemClassLoader().getResourceAsStream("pixelfont.ttf");
	public Font newfont;
	public Font fontSystemCreat;
	static public Spritsheet spriteUI;
	
	public UI(Spritsheet spritButton) {
		
		spriteUI =  new Spritsheet("/spriteUI.png");
		UI = new BufferedImage[7];
		
		UI[0] = spriteUI.getSprite(0, 0, 128, 48);
		UI[1] = spriteUI.getSprite(0, 48, 32, 32);
		UI[2] = spriteUI.getSprite(0, 80, 80, 128);
		UI[3] = spriteUI.getSprite(32, 48, 32, 32);
		UI[4] = spriteUI.getSprite(64, 48, 16, 16);
		UI[5] = spriteUI.getSprite(80, 48, 16, 16);
		UI[6] = spriteUI.getSprite(0, 208, 80, 96);
		
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
			
			if(Game.hour < 10 && Game.minute < 10) 
				g.drawString("0" + Game.hour + ":0" + Game.minute,  7,  25 );
			if(Game.hour >= 10 && Game.minute < 10) 
				g.drawString(Game.hour + ":0" + Game.minute,  7,  25 );
			if(Game.hour < 10 && Game.minute >= 10) 
				g.drawString("0" + Game.hour + ":" + Game.minute,  7,  25 );
			if(Game.hour >= 10 && Game.minute >= 10) 
				g.drawString(Game.hour + ":" + Game.minute,  7,  25 );
		}
	}
	
	private void invSystem(Graphics g) {
		
		g.drawImage(UI[0], 58 ,123 , null);
		
		if(Game.sysInv.handItem != null) {
			g.setFont(newfont);
			g.setColor(Color.white);
			//Ajudar a centralizar o texto
			if(Game.sysInv.handItem.tipo.length() < 5)
				g.drawString(Game.sysInv.handItem.tipo, 115, 135);
			else if (Game.sysInv.handItem.tipo.length() >= 5 &&
			Game.sysInv.handItem.tipo.length() < 10) {
				g.drawString(Game.sysInv.handItem.tipo, 105, 135);
			}else if (Game.sysInv.handItem.tipo.length() >= 10 &&
					Game.sysInv.handItem.tipo.length() < 15) {	
				g.drawString(Game.sysInv.handItem.tipo, 95, 135);
			}else if(Game.sysInv.handItem.tipo.length() >= 15) {
				g.drawString(Game.sysInv.handItem.tipo, 85, 135);
			}
		}
				
		//Apenas renderizando oque foi colocado no buffer do inventario
		for(int i=0; i < Game.sysInv.inv.length; i++) {
					
			g.drawImage(Game.sysInv.inv[i], 76 + (i*19), 139, null);
			
			if(Game.sysInv.inv[i] != null &&
				Game.sysInv.inventario[i] != null &&
				Game.sysInv.inventario[i].itensPack.size()+1 > 1 ){
					g.setFont(new Font("arial", Font.BOLD, 9));
					g.setColor(Color.yellow);
					g.drawString((Game.sysInv.inventario[i].itensPack.size() + 1) + "", 76 + (i*19), 154);
			}
					
			if(Game.sysInv.handIndexItem == i) { //Indica qual item est� na m�o do Player
				g.drawImage(UI[1], 76 + (i*19) ,140 , null);
			}		
		}
	}
	
	private void lifePlayer(Graphics g) {
		g.setColor(Color.black); 
		g.fillRect(7, 3, 72, 10);
		g.setColor(Color.red);
		g.fillRect(8, 4, 70, 8);
		g.setColor(Color.green);
		g.fillRect(8, 4, (int)((Game.player.life/Game.player.maxLife)*70), 8);
		g.setFont(newfont);
		g.setColor(Color.white);
		g.drawString((int)Game.player.life + "/" + (int)Game.player.maxLife, 30, 11);
		
		g.setFont(newfont);
		g.setColor(Color.white);
		g.drawString((int)Game.player.life + "/" + (int)Game.player.maxLife, 30, 11);
	}
	
	public void lifeMobs(Graphics g) {
		
		if(!Game.mapaGame.equals(Mapa.MAPA_CALABOU�O)) {
			for(int i=0; i<Game.mobs.size();i++) {
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
		
		if(Game.player.clickBag) {
			Sound.Clips.selectedInventory.play();
		}
		
	}
	
	public void systemCreation(Graphics g) {
		
		g.setColor(new Color(0xA0A0A0));
		g.fillRect(75, 65, 100, 65);
				
		//Janela de Cria��o
		//barra esquerda
		g.setColor(Color.black); 
		g.fillRect(175, 57, 1, 73);
		//barra direita
		g.setColor(Color.black); 
		g.fillRect(74, 65, 1, 65);
		//barra de cima
		g.setColor(Color.black); 
		g.fillRect(74, 57, 101, 8);
		//baara de baixo
		g.setColor(Color.black); 
		g.fillRect(74, 130, 102, 1);
		
		g.setFont(newfont);
		g.setColor(Color.white);
		g.drawString("Cria��o", 111, 64);
		
		//Espa�o para criar Itens 01
		g.setColor(new Color(0xC0C0C0));
		g.fillRect(80, 70, 20, 20);
	
		//barra direita
		g.setColor(new Color(0x404040)); 
		g.fillRect(80, 70, 1, 20);
		//barra de cima
		g.setColor(new Color(0x404040)); 
		g.fillRect(80, 70, 20, 1);
		
		//Espa�o para criar Itens 02
		g.setColor(new Color(0xC0C0C0));
		g.fillRect(110, 70, 20, 20);
			
		//barra direita
		g.setColor(new Color(0x404040)); 
		g.fillRect(110, 70, 1, 20);
		//barra de cima
		g.setColor(new Color(0x404040)); 
		g.fillRect(110, 70, 20, 1);
		
		//Espa�o para criar Itens 03
		g.setColor(new Color(0xC0C0C0));
		g.fillRect(80, 105, 20, 20);
							
		//barra direita
		g.setColor(new Color(0x404040)); 
		g.fillRect(80, 105, 1, 20);
		//barra de cima
		g.setColor(new Color(0x404040)); 
		g.fillRect(80, 105, 20, 1);
		
		//Espa�o para criar Itens 04
		g.setColor(new Color(0xC0C0C0));
		g.fillRect(110, 105, 20, 20);
					
		//barra direita
		g.setColor(new Color(0x404040)); 
		g.fillRect(110, 105, 1, 20);
		//barra de cima
		g.setColor(new Color(0x404040)); 
		g.fillRect(110, 105, 20, 1);
		
		//Espa�o para criar Itens 05
		g.setColor(new Color(0xC0C0C0));
		g.fillRect(150, 88, 20, 20);
							
		//barra direita
		g.setColor(new Color(0x404040)); 
		g.fillRect(150, 88, 1, 20);
		//barra de cima
		g.setColor(new Color(0x404040)); 
		g.fillRect(150, 88, 20, 1);
		
		//Sinal de +
		g.setFont(fontSystemCreat);
		g.setColor(Color.white);
		g.drawString("+", 102, 102);
		
		//Sinal de =
		g.setFont(fontSystemCreat);
		g.setColor(Color.white);
		g.drawString("=", 138, 102);
		
		//Apenas renderizando oque foi colocado no buffer do inventari
							
		g.drawImage(Game.sysCre.itens[0], 83, 72, null);
		
		g.drawImage(Game.sysCre.itens[1], 113, 72, null);
		
		g.drawImage(Game.sysCre.itens[2], 83, 107, null);
		
		g.drawImage(Game.sysCre.itens[3], 113, 107, null);
		
		g.drawImage(Game.sysCre.itens[4], 153, 90, null);
					
		if(Game.sysCre.slot[0] != null &&
				Game.sysCre.slot[0] != null &&
						Game.sysCre.slot[0].itensPack.size()+1 > 1 ){
			g.setFont(new Font("arial", Font.BOLD, 9));
			g.setColor(Color.white);
			g.drawString((Game.sysCre.slot[0].itensPack.size() + 1) + "", 83, 89);
		}
		
		if(Game.sysCre.slot[1] != null &&
				Game.sysCre.slot[1] != null &&
						Game.sysCre.slot[1].itensPack.size()+1 > 1 ){
			g.setFont(new Font("arial", Font.BOLD, 9));
			g.setColor(Color.white);
			g.drawString((Game.sysCre.slot[1].itensPack.size() + 1) + "", 113, 89);
		}
		
		if(Game.sysCre.slot[2] != null &&
				Game.sysCre.slot[2] != null &&
						Game.sysCre.slot[2].itensPack.size()+1 > 1 ){
			g.setFont(new Font("arial", Font.BOLD, 9));
			g.setColor(Color.white);
			g.drawString((Game.sysCre.slot[2].itensPack.size() + 1) + "", 83, 124);
		}
		
		if(Game.sysCre.slot[3] != null &&
				Game.sysCre.slot[3] != null &&
						Game.sysCre.slot[3].itensPack.size()+1 > 1 ){
			g.setFont(new Font("arial", Font.BOLD, 9));
			g.setColor(Color.white);
			g.drawString((Game.sysCre.slot[3].itensPack.size() + 1) + "", 113, 124);
		}
		
		if(Game.sysCre.slot[4] != null &&
				Game.sysCre.slot[4] != null &&
						Game.sysCre.slot[4].itensPack.size()+1 > 1 ){
			g.setFont(new Font("arial", Font.BOLD, 9));
			g.setColor(Color.white);
			g.drawString((Game.sysCre.slot[4].itensPack.size() + 1) + "", 152, 106);
		}
		
	}
	
	public void systemDialog(Graphics g) {
		if(Npc.showMessage) {
			
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
		
		g.setFont(new Font("arial", Font.BOLD, 8));
		g.setColor(Color.yellow);
		g.drawString("Muni��o: " + Game.player.ammo, 185, 7);
//		g.setColor(Color.darkGray); 
//		g.drawString("Level " + Game.CUR_LEVEL, 10, 745);

		lifePlayer(g);
		lifeMobs(g);
		timeSystem(g);
		invSystem(g);
		levelTab(g);
		systemDialog(g);
		
		if(Game.player.useBag)
			systemBag(g);
		
		if(Game.player.creation)
			systemCreation(g);
	
	}
}
