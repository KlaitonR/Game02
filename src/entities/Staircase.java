package entities;

import java.awt.image.BufferedImage;
import util.Mapa;
import util.Regiao;

public class Staircase extends Entity{

	public Staircase(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		mapa.add(Mapa.MAPA_CALABOU�O);
		regiao.add(Regiao.REGIAO_CALABOU�O);
		maskx = 0;
		masky = 0;
		mwidth = 14;
		mheigth = 16;
	}
	
//	public void render(Graphics g) {
//	
//		super.render(g);
//		
//		g.setColor(Color.black);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheigth);
//	
//	}
	
}
