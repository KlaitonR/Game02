package entities;

import java.awt.image.BufferedImage;
import util.Mapa;
import util.Regiao;

public class Staircase extends Entity{

	public Staircase(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		maskx = 0;
		masky = 0;
		mwidth = 20;
		mheigth = 16;
		
		mapa.add(Mapa.MAPA_CALABOUCO);
		regiao.add(Regiao.REGIAO_CALABOUÇO);
		
		depth = 0;
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
