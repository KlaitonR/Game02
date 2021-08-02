package entities.itens;

import java.awt.image.BufferedImage;

import util.Id;
import util.Mapa;
import util.Regiao;

public class PigBeef extends Beef{

	public PigBeef(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		pack = true;
		id = Id.ID_PIG_BEEF;
		qtPack = 63;
		mapa.add(Mapa.MAPA_FLORESTA);
		regiao.add(Regiao.REGIAO_FLORESTA);
		
	}

}
