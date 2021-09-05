package util;

import java.util.ArrayList;

public enum Mapa {
	
	MAPA_FLORESTA,
	MAPA_CALABOUÇO,
	MAPA_ROOM_HOUSE_01;
	
	public static ArrayList<Mapa> allMapas = new ArrayList<>();

	public static ArrayList<Mapa> addAll() {
		allMapas.add(MAPA_FLORESTA);
		allMapas.add(MAPA_CALABOUÇO);
		allMapas.add(MAPA_ROOM_HOUSE_01);
		
		return allMapas;
	}
}
