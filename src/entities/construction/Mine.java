package entities.construction;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import util.Mapa;
import util.Regiao;

public class Mine extends Construction{

	public Mine(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		maskx = 0;
		masky = 8;
		mwidth = 36;
		mheigth = 30;
		
		mapa.add(Mapa.MAPA_FLORESTA);
		regiao.add(Regiao.REGIAO_FLORESTA);
	
	}
	
	public void render(Graphics g) {
		super.render(g);
		
//		maskx = 0;
//		masky = 8;
//		mwidth = 36;
//		mheigth = 30;
//		
//		g.setColor(Color.black);
//		g.fillRect(this.getX() - Camera.x + maskx, this.getY() - Camera.y + masky, mwidth, mheigth);
		
	}

}
