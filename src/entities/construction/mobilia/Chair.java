package entities.construction.mobilia;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entities.construction.Construction;
import util.Mapa;
import util.Regiao;

public class Chair extends Construction{

	public Chair(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		maskx = 5;
		masky = 3;
		mwidth = 8;
		mheigth = 12;
		
		mapa.add(Mapa.MAPA_ROOM_HOUSE_01);
		regiao.add(Regiao.REGIAO_ROOM_HOUSE_01);
	}
	
	public void render(Graphics g) {
		super.render(g);
		
//		maskx = 3;
//		masky = 3;
//		mwidth = 8;
//		mheigth = 12;
//		
//		g.setColor(Color.black);
//		g.fillRect(this.getX() - Camera.x + maskx, this.getY() - Camera.y + masky, mwidth, mheigth);
		
	}

}
