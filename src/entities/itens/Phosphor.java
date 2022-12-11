package entities.itens;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import util.Id;
import util.Mapa;
import util.Regiao;

public class Phosphor extends Ore {
	
	public Phosphor(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		mwidth = 16;
		mheigth = 16;
		maskx = 0;
		masky = 0;
		
		pack = true;
		qtPack = 63;
		id = Id.ID_PHOSPHOR;
		mapa.add(Mapa.MAPA_CALABOUCO);
		regiao.add(Regiao.REGIAO_CALABOU�O);
	}
	
	public void render(Graphics g) {
		
//		g.setColor(Color.black);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheigth);
		super.render(g);
		
	}

}
