package entities.construction;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import util.Id;
import util.Mapa;
import util.Regiao;

public class Mine extends Construction{
	
	public static int psX;
	public static int psY;
	public static boolean unlocked;
	public static boolean disable;
	public static int price = 200;
	public static List<String> description = Arrays.asList("Mina: Você poderá coletar recursos minerais.",
			"Poderá ter apenas um item desse recurso.",
			"");

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
		
		maskxNoConstruction = -16;
		maskyNoConstruction = -16;
		maskmNoConstruction = 64;
		maskhNoConstruction = 64;
		
//		g.setColor(Color.red);
//		g.fillRect(this.getX() - Camera.x + maskxNoConstruction, this.getY() - Camera.y + maskyNoConstruction, maskmNoConstruction, maskhNoConstruction);
		
	}
}
