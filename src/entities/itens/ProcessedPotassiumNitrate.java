package entities.itens;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entities.Entity;
import util.Id;
import util.Mapa;
import util.Regiao;

public class ProcessedPotassiumNitrate extends Entity {
	
	public ProcessedPotassiumNitrate(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		mwidth = 16;
		mheigth = 16;
		maskx = 0;
		masky = 0;
		
		id = Id.ID_PROCESSED_POTASSIUM_NITRATE;
		pack = true;
		qtPack = 63;
		mapa.add(Mapa.MAPA_FLORESTA);
		regiao.add(Regiao.REGIAO_FLORESTA);
	}
	
	public void render(Graphics g) {
		
//		g.setColor(Color.black);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheigth);
		super.render(g);
		
	}

}
