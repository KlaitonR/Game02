package entities;

import java.awt.image.BufferedImage;

import util.Mapa;
import util.Regiao;

public class Oak extends Tree{
	
	public Oak(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
			mapa.add(Mapa.MAPA_FLORESTA);
			regiao.add(Regiao.REGIAO_FLORESTA);
		}

	public void tick() {
		if(life <= 0) {
			destroySelf(Entity.FIREWOOD_CARVALHO_EN, Entity.SEED_CARVALHO_EN);
		}
	}
	
}
