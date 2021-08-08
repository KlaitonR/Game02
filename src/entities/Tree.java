package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import main.Sound;
import util.Mapa;
import util.Regiao;

public class Tree extends Entity{
	
	public static int cuttingTime, maxCuttingTime = Game.rand.nextInt(600) + 400;
	
	public int life;
	
	public static BufferedImage CARVALHO_EN = Game.spritesheet.getSprite(48, 0, 16, 16);
	public static BufferedImage PINHEIRO_EN = Game.spritesheet.getSprite(64, 0, 16, 16);
	public static BufferedImage SALGUEIRO_EN = Game.spritesheet.getSprite(80, 0, 16, 16);

	public Tree(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		height += 10;
		width -= 9;
		x += 4;
		y-=7;
		
		life = 1;
		
		mapa.add(Mapa.MAPA_FLORESTA);
		regiao.add(Regiao.REGIAO_FLORESTA);
	}
	
	public void destroySelf() {
		
		//Renderizar cepo
		Stump sp = new Stump(this.getX(), this.getY(), 16, 16, Entity.STUMP_EN);
		sp.tipo = "cepo";
		sp.psTiles = this.psTiles;
		Game.entities.add(sp);
				
		Game.world.tiles[psTiles].en = null;
		Game.entities.remove(this);
		Sound.Clips.cuttingTree.stop();
		Sound.Clips.fallingTree.play();
	}
	
	public void render(Graphics g) {
		super.render(g);
	}

}
