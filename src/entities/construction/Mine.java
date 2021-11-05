package entities.construction;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import util.Id;
import util.Mapa;
import util.Regiao;

public class Mine extends Construction{
	
	public static int psX;
	public static int psY;
	public static boolean unlocked;
	public static boolean disable;
	public static int price = 200;
	public static String description = "Mina: Você poderá coletar recursos minerais.";

	public Mine(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		maskx = 0;
		masky = 6;
		mwidth = 32;
		mheigth = 26;
		
		psX = (int)x;
		psY = (int)y;
		
		id = Id.ID_MINE;
		mapa.add(Mapa.MAPA_FLORESTA);
		regiao.add(Regiao.REGIAO_FLORESTA);
	}
	
	public void render(Graphics g) {
		super.render(g);
		
//		maskx = 0;
//		masky = 6;
//		mwidth = 32;
//		mheigth = 26;
//		
//		g.setColor(Color.black);
//		g.fillRect(this.getX() - Camera.x + maskx, this.getY() - Camera.y + masky, mwidth, mheigth);
		
	}

}
