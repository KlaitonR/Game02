package construction;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import util.Mapa;
import util.Regiao;

public class Mine extends Construction{

	public Mine(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		maskx = 32;
		masky = 32;
		mwidth = -20;
		mheigth = -20;
		
		mapa.add(Mapa.MAPA_FLORESTA);
		regiao.add(Regiao.REGIAO_FLORESTA);
	
	}
	
	public void render(Graphics g) {
		super.render(g);
		
		maskx = 2;
		masky = 8;
		mwidth = 28;
		mheigth = 24;
		
//		g.setColor(Color.black);
//		g.fillRect(this.getX() - Camera.x + maskx, this.getY() - Camera.y + masky, mwidth, mheigth);
		
	}

}
