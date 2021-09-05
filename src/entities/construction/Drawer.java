package entities.construction;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import util.Mapa;
import util.Regiao;

public class Drawer extends Construction{

	public Drawer(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		
		maskx = 2;
		masky = 3;
		mwidth = 13;
		mheigth = 10;
		
		mapa.add(Mapa.MAPA_ROOM_HOUSE_01);
		regiao.add(Regiao.REGIAO_ROOM_HOUSE_01);
	}
	
	public void render(Graphics g) {
		super.render(g);
		
//		maskx = 2;
//		masky = 3;
//		mwidth = 13;
//		mheigth = 10;
//		
//		g.setColor(Color.black);
//		g.fillRect(this.getX() - Camera.x + maskx, this.getY() - Camera.y + masky, mwidth, mheigth);
		
	}


}
