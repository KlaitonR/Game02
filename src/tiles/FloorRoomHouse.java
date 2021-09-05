package tiles;

import java.awt.image.BufferedImage;

import util.Mapa;
import util.Regiao;
import world.Tile;

public class FloorRoomHouse extends Tile{

	public FloorRoomHouse(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
		
		mapa.add(Mapa.MAPA_ROOM_HOUSE_01);
		regiao.add(Regiao.REGIAO_ROOM_HOUSE_01);
	}
	
	

}
