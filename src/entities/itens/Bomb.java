package entities.itens;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entities.Entity;
import main.Game;
import util.Id;
import util.Mapa;
import util.Regiao;
import world.Camera;

public class Bomb extends Entity{
	
	private BufferedImage animation[];
	private int frames, maxFrames = 5, index, maxIndex = 29, maxTime = 3*60;
	public boolean explode, drop;
	int time;
	
	public Bomb(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		pack = true;
		qtPack = 63;
		id = Id.ID_BOMB;
		mapa.add(Mapa.MAPA_FLORESTA);
		regiao.add(Regiao.REGIAO_FLORESTA);
		
		animation = new BufferedImage[30];
	
		animation[0] = Game.spriteBomb.getSprite(0, 0, 32, 32);
		animation[1] = Game.spriteBomb.getSprite(32, 0, 32, 32);
		animation[2] = Game.spriteBomb.getSprite(64, 0, 32, 32);
		animation[3] = Game.spriteBomb.getSprite(96, 0, 32, 32);
		animation[4] = Game.spriteBomb.getSprite(128, 0, 32, 32);
		animation[5] = Game.spriteBomb.getSprite(160, 0, 32, 32);
		animation[6] = Game.spriteBomb.getSprite(192, 0, 32, 32);
		animation[7] = Game.spriteBomb.getSprite(224, 0, 32, 32);
		animation[8] = Game.spriteBomb.getSprite(256, 0, 32, 32);
		animation[9] = Game.spriteBomb.getSprite(288, 0, 32, 32);
		animation[10] = Game.spriteBomb.getSprite(320, 0, 32, 32);
		animation[11] = Game.spriteBomb.getSprite(352, 0, 32, 32);
		
		animation[12] = Game.spriteBomb.getSprite(0, 32, 32, 32);
		animation[13] = Game.spriteBomb.getSprite(32, 32, 32, 32);
		animation[14] = Game.spriteBomb.getSprite(64, 32, 32, 32);
		animation[15] = Game.spriteBomb.getSprite(96, 32, 32, 32);
		animation[16] = Game.spriteBomb.getSprite(128, 32, 32, 32);
		animation[17] = Game.spriteBomb.getSprite(160, 32, 32, 32);
		animation[18] = Game.spriteBomb.getSprite(192, 32, 32, 32);
		animation[19] = Game.spriteBomb.getSprite(224, 32, 32, 32);
		animation[20] = Game.spriteBomb.getSprite(256, 32, 32, 32);
		animation[21] = Game.spriteBomb.getSprite(288, 32, 32, 32);
		animation[22] = Game.spriteBomb.getSprite(320, 32, 32, 32);
		animation[23] = Game.spriteBomb.getSprite(352, 32, 32, 32);
		
		animation[24] = Game.spriteBomb.getSprite(0, 64, 32, 32);
		animation[25] = Game.spriteBomb.getSprite(32, 64, 32, 32);
		animation[26] = Game.spriteBomb.getSprite(64, 64, 32, 32);
		animation[27] = Game.spriteBomb.getSprite(96, 64, 32, 32);
		animation[28] = Game.spriteBomb.getSprite(128, 64, 32, 32);
		animation[29] = Game.spriteBomb.getSprite(160, 64, 32, 32);
		
	}
	
	public void tick() {
		
		if(drop) {
			time++;
		}else {
			time = 0;
			frames = 0;
			index = 0;
		}
		
		if(time >= maxTime) {
			explode = true;
		}
		
//		explode = true;
//		explode = false;
		
		if(explode) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
					explode = false;
					drop = false;
					Game.entities.remove(this);
				}
			}
		}
	}
	
	public void render(Graphics g) {
		
		super.render(g);
		
		if(explode) {
			g.drawImage(animation[index], this.getX() - Camera.x - 10, this.getY() - Camera.y - 8, null);
			setSprite(null);
		}else {
			if(drop) 
				setSprite(Entity.BOMBA_ATIVA_EN);
			else
				setSprite(Entity.BOMBA_EN);
		}
	}

}
