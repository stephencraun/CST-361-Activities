package business;

import javax.ejb.Local;

import beans.Album;

@Local
public interface MusicManagerInterface {
	public Album addAlbum(Album album);
}
