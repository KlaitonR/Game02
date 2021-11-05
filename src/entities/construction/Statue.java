package entities.construction;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import util.Id;
import util.Mapa;
import util.Regiao;

public class Statue extends Construction{

	public Statue(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		maskx = 3;
		masky = 2;
		mwidth = 58;
		mheigth = 60;
		
		id = Id.ID_STATUE;
		mapa.add(Mapa.MAPA_CALABOUÇO);
		regiao.add(Regiao.REGIAO_CALABOUÇO);
		
	}
	
	public void render(Graphics g) {
		super.render(g);
		
//		maskx = 3;
//		masky = 2;
//		mwidth = 58;
//		mheigth = 60;
//		
//		g.setColor(Color.black);
//		g.fillRect(this.getX() - Camera.x + maskx, this.getY() - Camera.y + masky, mwidth, mheigth);
		
	}

}
