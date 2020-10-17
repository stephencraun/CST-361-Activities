package controllers;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import beans.Album;
import business.MusicManager;
import business.MusicManagerInterface;
import util.AlbumNotFoundException;
import util.TracksNotFoundException;

@ManagedBean
@ViewScoped
public class AlbumController {	
	@EJB
	MusicManagerInterface manager;
	
	public String onSubmit(Album album) {	
		try {
			album = manager.addAlbum(album);
		}
		catch (TracksNotFoundException e) {
			System.out.println("Tracks not found exception");
		}
		
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("album", album);
		return "AddAlbumResponse.xhtml";
	}
	
	public String onFind(Album album) {
		try {
			manager.getAlbum(album);
		}
		catch(AlbumNotFoundException e) {
			System.out.println("Album not found exception");
		}
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("album", album);
		return "AddAlbumResponse.xhtml";
	}
}
