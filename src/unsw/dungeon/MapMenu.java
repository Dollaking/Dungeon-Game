package unsw.dungeon;

public class MapMenu {
	private MapPicture picture;
	private String mapFileName;
	
	public MapMenu(MapPicture mp, String fileName) {
		this.picture = mp;
		this.mapFileName = fileName;
	}

	public MapPicture getPicture() {
		return picture;
	}

	public String getMapFileName() {
		return mapFileName;
	}
}
