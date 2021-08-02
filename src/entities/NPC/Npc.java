package entities.NPC;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entities.Entity;
import main.Game;
import main.Sound;
import util.Mapa;
import util.Regiao;

public class Npc extends Entity{
	
	public static String[] frases = new String[1];
	public static boolean showMessage;
	public boolean offMessage;
	public static int curIndexMsg;
	public static int fraseIndex = 0;
	public int time = 0;
	public int maxTime = 5;
	
	public Npc(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		maskx = -4;
		masky = -4;
		mwidth = 24;
		mheigth = 24;
		
		depth = 7;
		frases[0] = "Você irá morrer fazendeiro!";
		
		mapa.add(Mapa.MAPA_FLORESTA);
		regiao.add(Regiao.REGIAO_FLORESTA);
		
	}
	
	public void tick() {
		
//		int xPlayer = Game.player.getX();
//		int yPlayer = Game.player.getY();
//		
//		if(Math.abs(xPlayer - this.x) < 20 && Math.abs(yPlayer - this.y) < 20 && !offMessage) { 
//			showMessage = true;
//		}
		
		if(Entity.isColidding(this, Game.player)) {
			showMessage = true;
		}else {
			showMessage = false;
		}
		
		if(showMessage) {
			time++;
			if(time >= maxTime) {
				time = 0;
				if(curIndexMsg < frases[fraseIndex].length()) {
					curIndexMsg++;  
					Sound.Clips.text.loop();
				}else if(fraseIndex < frases.length - 1){
					fraseIndex++;
					curIndexMsg = 0;
				}
			}
			
			if(curIndexMsg == frases[fraseIndex].length()) {
				Sound.Clips.text.stop();
			}
			
		}else {
			fraseIndex = 0;
			time = 0;
			curIndexMsg = 0;
			Sound.Clips.text.stop();
		}
	}
	
	public void render(Graphics g) {
//		g.setColor(Color.black);
//		g.fillRect(this.getX() - Camera.x + maskx, this.getY() - Camera.y + masky, mwidth, mheigth);
		super.render(g);
	}

}
