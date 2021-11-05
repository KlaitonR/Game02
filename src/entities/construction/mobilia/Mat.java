package entities.construction.mobilia;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entities.construction.Construction;
import util.Mapa;
import util.Regiao;

public class Mat extends Construction{

	public Mat(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		maskx = 0;
		masky = 0;
		mwidth = 16;
		mheigth = 16;
		depth = 0;
		
		mapa.add(Mapa.MAPA_ROOM_HOUSE_01);
		regiao.add(Regiao.REGIAO_ROOM_HOUSE_01);
	}
	
	public void render(Graphics g) {
		super.render(g);
		
//		maskx = 0;
//		masky = 0;
//		mwidth = 16;
//		mheigth = 16;
//		
//		g.setColor(Color.black);
//		g.fillRect(this.getX() - Camera.x + maskx, this.getY() - Camera.y + masky, mwidth, mheigth);
		
	}

}
