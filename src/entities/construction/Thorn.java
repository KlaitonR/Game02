package entities.construction;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import util.Mapa;
import util.Regiao;

public class Thorn extends Construction{
	
	public double life;
	public static int price = 400;
	public static String description = "Espinhos: Você poderá usar esse recurso como barricada.";
	public static boolean disable;
	public static boolean unlocked;

	public Thorn(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		mwidth = 16;
		mheigth = 16;
		
		life = 100;
		
		mapa.add(Mapa.MAPA_FLORESTA);
		regiao.add(Regiao.REGIAO_FLORESTA);
		
	}
	
	public void render(Graphics g) {
		super.render(g);
		
		maskx = 0;
		masky = 0;
		mwidth = 16;
		mheigth = 16;
//		
//		g.setColor(Color.black);
//		g.fillRect(this.getX() - Camera.x + maskx, this.getY() - Camera.y + masky, mwidth, mheigth);
		
	}

}
