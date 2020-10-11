package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import beans.Album;
import beans.Track;
import util.TracksNotFoundException;

@Stateless
@Local(MusicManagerInterface.class)
@LocalBean
public class MusicManager implements MusicManagerInterface{
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
	public Album addAlbum(Album album) {
		album.setTracks(getTracks(album));
		return album;
		
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
