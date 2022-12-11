package entities.construction;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import util.Id;
import util.Mapa;
import util.Regiao;

public class MineLand extends Construction{
	
	public double life;
	public static int price = 550;
	public static List<String> description = Arrays.asList("Mina terrestre: Pege seus inimigos de surpresa!",
			"",
			"");
	public static boolean disable;
	public static boolean unlocked;

	public MineLand(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		mwidth = 16;
		mheigth = 16;
		
		life = 100;
		
		id = Id.ID_MINE_LAND;
		mapa.add(Mapa.MAPA_FLORESTA);
		regiao.add(Regiao.REGIAO_FLORESTA);
	}
}
