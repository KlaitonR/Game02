package entities;

import java.awt.image.BufferedImage;

import entities.itens.Root;
import main.Game;
import util.Mapa;
import util.Regiao;

public class Stump extends Entity{

	public Stump(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		mapa.add(Mapa.MAPA_FLORESTA);
		regiao.add(Regiao.REGIAO_FLORESTA);
	}
	
	public void destroySelf() {
		Root root = new Root(x+5, y-7, 16, 16, Entity.RAIZ_EN);
		root.show = true;
		root.tipo = "raiz";
		root.clear = true;
		Game.collision.createGroundOnStump(this.getX(), this.getY(), psTiles);
		Game.entities.add(root);
		Game.entities.remove(this);
	}


}
