package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import entities.Npc;
import main.Game;
import main.Menu;

public class UI {
	
	private BufferedImage [] button;
	public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("pixelfont.ttf");
	public InputStream stream02 = ClassLoader.getSystemClassLoader().getResourceAsStream("pixelfont.ttf");
	public Font newfont;
	public Font fontSystemCreat;
	
	public UI(Spritsheet spritButton) {
		
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
				g.drawString("Hora: 0" + Game.hour + ":0" + Game.minute,  20,  30 );
			if(Game.hour >= 10 && Game.minute < 10) 
				g.drawString("Hora: " + Game.hour + ":0" + Game.minute,  20,  30 );
			if(Game.hour < 10 && Game.minute >= 10) 
				g.drawString("Hora: 0" + Game.hour + ":" + Game.minute,  20,  30 );
			if(Game.hour >= 10 && Game.minute >= 10) 
				g.drawString("Hora: " + Game.hour + ":" + Game.minute,  20,  30 );
		}
	}
	
	private void invSystem(Graphics g) {
		
		if(Game.sysInv.handItem != null) {
			g.setFont(newfont);
			g.setColor(Color.white);
			//Ajudar a centralizar o texto
			if(Game.sysInv.handItem.tipo.length() < 5)
				g.drawString(Game.sysInv.handItem.tipo, 115, 132);
			else if (Game.sysInv.handItem.tipo.length() >= 5 &&
			Game.sysInv.handItem.tipo.length() < 10) {
				g.drawString(Game.sysInv.handItem.tipo, 105, 132);
			}else if (Game.sysInv.handItem.tipo.length() >= 10 &&
					Game.sysInv.handItem.tipo.length() < 15) {	
				g.drawString(Game.sysInv.handItem.tipo, 95, 132);
			}else if(Game.sysInv.handItem.tipo.length() >= 15) {
				g.drawString(Game.sysInv.handItem.tipo, 85, 132);
			}
		}
				
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(45, 135, 150, 20);
				
		g.setColor(Color.black); 
		g.fillRect(45, 135, 1, 20);
		g.setColor(Color.black); 
		g.fillRect(195, 135, 1, 20);
		g.setColor(Color.black); 
		g.fillRect(45, 154, 150, 1);
		g.setColor(Color.black); 
		g.fillRect(45, 135, 150, 1);
				
		for(int i=1; i<5; i++) {
			g.setColor(Color.black); 
			g.fillRect(45 + (i*30), 135, 1, 20);
		}
				
		//Apenas renderizando oque foi colocado no buffer do inventario
		for(int i=0; i < Game.sysInv.inv.length; i++) {
					
			g.drawImage(Game.sysInv.inv[i], 52 + (i*30), 137, null);
			
			if(Game.sysInv.inv[i] != null &&
				Game.sysInv.inventario[i] != null &&
				Game.sysInv.inventario[i].itensPack.size()+1 > 1 ){
					g.setFont(new Font("arial", Font.BOLD, 9));
					g.setColor(Color.white);
					g.drawString((Game.sysInv.inventario[i].itensPack.size() + 1) + "", 48 + (i*30), 152);
			}
					
			if(Game.sysInv.handIndexItem == i) { //Indica qual item está na mão do Player
				g2.setColor(new Color(255,255,255,150));
				g2.fillRect(46 + (i*30), 136, 1, 18);
				g2.fillRect(74 + (i*30), 136, 1, 18);
				g2.fillRect(47 + (i*30), 153, 27, 1); 
				g2.fillRect(47 + (i*30), 136, 27, 1);
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
	
	private void levelTab(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,150));
			
			if(Game.player.openLvls && !Game.player.offLvls) {
					
				g2.fillRect(5, 40, 75, 90);
					
				g.setColor(Color.black); 
				g.fillRect(4, 40, 77, 10);
				g.drawImage(button[1], 42 ,42 , null);
					
				g.setColor(Color.black);
				g.fillRect(7, 65, 72, 10);
				g.setColor(Color.blue);
				g.fillRect(8, 66, 70, 8);
				g.setColor(Color.yellow);
					
				double dif;
				dif = Game.player.maxExp[Game.player.levelPlayer] - Game.player.exp;
				
				if(dif <= Game.player.maxExp[Game.player.levelPlayer] && dif > 0){
					g.fillRect(8, 66,(int)((Game.player.exp/Game.player.maxExp[Game.player.levelPlayer])*70), 8);
				}else {
					g.fillRect(8, 66,(int)(((Game.player.exp + dif)/Game.player.maxExp[Game.player.levelPlayer])*70), 8);
				}

				g.setFont(newfont);
				g.setColor(Color.white);
				g.drawString("EXP  " + (int)Game.player.exp + "/" + (int)Game.player.maxExp[Game.player.levelPlayer], 8, 73);
				
				//barra esquerda
				g.setColor(Color.black); 
				g.fillRect(4, 40, 1, 90);
				//barra direita
				g.setColor(Color.black); 
				g.fillRect(80, 40, 1, 90);
				//barra de cima
				g.setColor(Color.black); 
				g.fillRect(4, 40, 77, 1);
				//baara de baixo
				g.setColor(Color.black); 
				g.fillRect(4, 130, 77, 1);
				
				g.setFont(newfont);
				g.setColor(Color.orange);
				g.drawString("Nível do jogador:" + (int)(Game.player.levelPlayer + 1), 7, 60);
				
			}else {
				
				g.setColor(Color.black); 
				g.fillRect(4, 40, 77, 10);
				g.drawImage(button[0], 42 ,42 , null);
				Game.player.offLvls = false;
			}
	}
	
	public void systemBag(Graphics g) {
		g.setColor(new Color(0xC0C0C0));
		g.fillRect(75, 10, 100, 120);
				
		//barra esquerda
		g.setColor(Color.black); 
		g.fillRect(175, 2, 1, 129);
		//barra direita
		g.setColor(Color.black); 
		g.fillRect(74, 2, 1, 128);
		//barra de cima
		g.setColor(Color.black); 
		g.fillRect(74, 2, 101, 8);
		//baara de baixo
		g.setColor(Color.black); 
		g.fillRect(74, 130, 102, 1);
		
		g.setFont(newfont);
		g.setColor(Color.white);
		g.drawString("Mochila", 78, 9);
		
		for(int i=1; i<4; i++) { // linhas horizontais
			g.setColor(Color.white); 
			g.fillRect(75 + (i*25), 10, 1, 120);
		}
		
		for(int i=1; i<6; i++) { // linhas verticais
			g.setColor(Color.white); 
			g.fillRect(75, 10 + (i*20) , 100, 1);
		}
		
		//Apenas renderizando oque foi colocado no buffer do inventario
		for(int j=0; j < 6; j++) {
			for(int i=0; i < 4; i++) {
				g.drawImage(Game.sysBag.bag[i][j], 82 + (i*25), 12 + (j*20), null);
				
				if(Game.sysBag.bag[i][j] != null &&
					Game.sysBag.bagpack[i][j] != null &&
					Game.sysBag.bagpack[i][j].itensPack.size()+1 > 1 ){
						g.setFont(new Font("arial", Font.BOLD, 9));
						g.setColor(Color.white);
						g.drawString((Game.sysBag.bagpack[i][j].itensPack.size() + 1) + "", 78 + (i*25), 27 + (j*20));
				}
			}
		}
	}
	
	public void systemCreation(Graphics g) {
		
		g.setColor(new Color(0xA0A0A0));
		g.fillRect(75, 65, 100, 65);
				
		//Janela de Criação
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
		g.drawString("Criação", 111, 64);
		
		//Espaço para criar Itens 01
		g.setColor(new Color(0xC0C0C0));
		g.fillRect(80, 70, 20, 20);
	
		//barra direita
		g.setColor(new Color(0x404040)); 
		g.fillRect(80, 70, 1, 20);
		//barra de cima
		g.setColor(new Color(0x404040)); 
		g.fillRect(80, 70, 20, 1);
		
		//Espaço para criar Itens 02
		g.setColor(new Color(0xC0C0C0));
		g.fillRect(110, 70, 20, 20);
			
		//barra direita
		g.setColor(new Color(0x404040)); 
		g.fillRect(110, 70, 1, 20);
		//barra de cima
		g.setColor(new Color(0x404040)); 
		g.fillRect(110, 70, 20, 1);
		
		//Espaço para criar Itens 03
		g.setColor(new Color(0xC0C0C0));
		g.fillRect(80, 105, 20, 20);
							
		//barra direita
		g.setColor(new Color(0x404040)); 
		g.fillRect(80, 105, 1, 20);
		//barra de cima
		g.setColor(new Color(0x404040)); 
		g.fillRect(80, 105, 20, 1);
		
		//Espaço para criar Itens 04
		g.setColor(new Color(0xC0C0C0));
		g.fillRect(110, 105, 20, 20);
					
		//barra direita
		g.setColor(new Color(0x404040)); 
		g.fillRect(110, 105, 1, 20);
		//barra de cima
		g.setColor(new Color(0x404040)); 
		g.fillRect(110, 105, 20, 1);
		
		//Espaço para criar Itens 05
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

		lifePlayer(g);
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
