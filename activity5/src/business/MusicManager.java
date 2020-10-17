package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import beans.Album;
import beans.Track;
import data.MusicDataService;
import util.AlbumNotFoundException;
import util.TracksNotFoundException;

@Stateless
@Local(MusicManagerInterface.class)
@LocalBean
public class MusicManager implements MusicManagerInterface{
	@EJB
	MusicDataService service;
	
	HashMap<String, List<Track>> trackInfo;
	
	//Default constructor
	public MusicManager() {
		//initialize info
		trackInfo = new HashMap<String, List<Track>>();
		List<Track> track1 = new ArrayList<Track>();
		track1.add(new Track("For a Pessimist, I'm Pretty Optimistic", 1));
		track1.add(new Track("That's What You Get", 2));
		track1.add(new Track("Hallelujah", 3));
		track1.add(new Track("Misery Business", 4));
		track1.add(new Track("When It Rains", 5));
		track1.add(new Track("Let the Flames Begin", 6));
		track1.add(new Track("Miracle", 7));
		track1.add(new Track("Crushcrushcrush", 8));
		track1.add(new Track("We Are Broken", 9));
		track1.add(new Track("Fences", 10));
		track1.add(new Track("Born For This", 11));
		trackInfo.put("Riot! by Paramore - 2007", track1);
	}
	
	@Override
	public Album addAlbum(Album album) throws TracksNotFoundException{
		Album findAlbum = service.findBy(album);
		
		if(findAlbum == null) {
			System.out.println("The album " + album.getTitle() + " does not already exist. Adding album now.");
			service.create(album);
			album = service.findBy(album);
			if(album.getTracks().isEmpty()) {
				throw new TracksNotFoundException();
			}
		}
		
		else {
			album.setTracks(getTracks(album));
			System.out.println("Album exists");
		}
		return album;
	}
	
	@Override 
	public Album getAlbum(Album album) throws AlbumNotFoundException{
		Album albumFound = service.findBy(album);
		
		if(albumFound == null) {
			throw new AlbumNotFoundException();
		}
		else {
			System.out.println("Album was found");
			return albumFound;
		}
	}
	
	private List<Track> getTracks(Album album){
		String key = album.getTitle() + " by " + album.getArtist() + " - " + album.getYear();
		if (trackInfo.containsKey(key)) {
			return trackInfo.get(key);
		}
		else {
			return new ArrayList<Track>();
		}
	}
}
