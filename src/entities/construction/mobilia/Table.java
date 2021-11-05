package entities.construction.mobilia;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entities.construction.Construction;
import util.Mapa;
import util.Regiao;

public class Table extends Construction{

	public Table(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		maskx = 8;
		masky = -2;
		mwidth = 19;
		mheigth = 17;
		
		mapa.add(Mapa.MAPA_ROOM_HOUSE_01);
		regiao.add(Regiao.REGIAO_ROOM_HOUSE_01);
	}
	
	public void render(Graphics g) {
		super.render(g);
		
//		maskx = 10;
//		masky = 5;
//		mwidth = 17;
//		mheigth = 10;
//		
//		g.setColor(Color.black);
//		g.fillRect(this.getX() - Camera.x + maskx, this.getY() - Camera.y + masky, mwidth, mheigth);
		
	}

}
