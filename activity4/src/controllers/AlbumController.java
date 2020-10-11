package controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import beans.Album;
import business.MusicManager;
import util.TracksNotFoundException;

@ManagedBean
@ViewScoped
public class AlbumController {

	public String onSubmit(Album album) {
		//Forwards to view along with the user bean
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("album", album);
		
		//call music manager business service
		MusicManager manager = new MusicManager();
		
		try {
			album = manager.addAlbum(album);
		}
		catch (TracksNotFoundException e) {
			System.out.println("Tracks not found");
		}
		
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("album", album);
		return "AddAlbumResponse.xhtml";
	}
	
}
