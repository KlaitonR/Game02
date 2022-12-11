package entities.construction;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import util.Id;
import util.Mapa;
import util.Regiao;

public class House extends Construction{
	
	public double life;

	public House(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		maskx = 2;
		masky = 5;
		mwidth = 29;
		mheigth = 23;
		
		maskxNoConstruction = -16;
		maskyNoConstruction = -16;
		maskmNoConstruction = 64;
		maskhNoConstruction = 64;
		
		life = 100;
		
		id = Id.ID_HOUSE;
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
		
//		g.setColor(Color.red);
//		g.fillRect(this.getX() - Camera.x + maskxNoConstruction, this.getY() - Camera.y + maskyNoConstruction, maskmNoConstruction, maskhNoConstruction);
		
	}
}
