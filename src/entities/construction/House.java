package entities.construction;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import util.Mapa;
import util.Regiao;

public class House extends Construction{

	public House(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
//		maskx = 0; mascara para entrada
//		masky = 12;
//		mwidth = 10;
//		mheigth = 16;
		
		maskx = 2;
		masky = 5;
		mwidth = 29;
		mheigth = 23;
		
		mapa.add(Mapa.MAPA_FLORESTA);
		regiao.add(Regiao.REGIAO_FLORESTA);
		
	}
	
	public void render(Graphics g) {
		super.render(g);
		
//		maskx = 2;
//		masky = 5;
//		mwidth = 29;
//		mheigth = 23;
//		
//		g.setColor(Color.black);
//		g.fillRect(this.getX() - Camera.x + maskx, this.getY() - Camera.y + masky, mwidth, mheigth);
		
	}
}
