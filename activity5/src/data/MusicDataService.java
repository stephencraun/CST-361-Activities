package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import beans.Album;
import beans.Track;
import util.DatabaseException;

/**
 * Session Bean implementation class MusicDataService
 */
@Stateless
@Local(DataAccessInterface.class)
@LocalBean
public class MusicDataService implements DataAccessInterface<Album>
{
    /**
     * Default constructor. 
     */
    public MusicDataService() 
    {
    }

    /**
     * CRUD: finder to return a single entity
     */
	public Album findById(int id)
	{
		return null;
	}

    /**
     * CRUD: finder to return all entities
     */
    public List<Album> findAll() 
    {
		// DB Connection Info
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3307/music";
		String username = "root";
		String password = "root";
		
		// Get all Albums and Tracks
		List<Album> albums = new ArrayList<Album>();
		try 
		{
			// Connect to the Database
			conn = DriverManager.getConnection(url, username, password);
			
			// Execute SQL Query and loop over result set
			String sql1 = "SELECT * FROM ALBUM";
			Statement stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery(sql1);
			while(rs1.next())
			{
				// Get the Album
				Album album = new Album(rs1.getString("TITLE"), rs1.getString("ARTIST"), rs1.getInt("YEAR"));
				
				// Query for all the Albums Tracks
				List<Track> tracks = new ArrayList<Track>();
				String sql2 = "SELECT * FROM TRACK WHERE ALBUM_ID = " + rs1.getInt("ID");
				Statement stmt2 = conn.createStatement();
				ResultSet rs2 = stmt1.executeQuery(sql2);
				while(rs2.next())
				{
					tracks.add(new Track(rs2.getString("TITLE"), rs2.getInt("NUMBER")));
				}
				rs2.close();
				stmt2.close();
				
				// Finish populating the Album and add to the return list
				album.setTracks(tracks);
				albums.add(album);
			}
			
			// Cleanup
			rs1.close();
			stmt1.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new DatabaseException(e);
		}
		finally
		{
			// Cleanup Database
			if(conn != null)
			{
				try 
				{
					conn.close();
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
					throw new DatabaseException(e);
				}
			}
		}
		
		// Return list of Albums
		return albums;
    }

    /**
     * CRUD: finder to return a single entity
     */
	public Album findBy(Album album)
	{
		// DB Connection Info
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3307/music";
		String username = "root";
		String password = "root";
		
		// Get all Albums and Tracks
		try 
		{
			// Connect to the Database
			conn = DriverManager.getConnection(url, username, password);
			
			// Execute SQL Query and loop over result set
			String sql1 = String.format("SELECT * FROM ALBUM WHERE TITLE='%S' AND ARTIST='%S' AND YEAR=%d", album.getTitle(), album.getArtist(), album.getYear());
			Statement stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery(sql1);
			if(!rs1.next())
			{
				rs1.close();
				stmt1.close();
				return null;
			}
			
			// Get the Album
			album.setTitle(rs1.getString("TITLE"));
			album.setArtist(rs1.getString("ARTIST"));
			album.setYear(rs1.getInt("YEAR"));
				
			// Query for all the Albums Tracks
			List<Track> tracks = new ArrayList<Track>();
			String sql2 = "SELECT * FROM TRACK WHERE ALBUM_ID = " + rs1.getInt("ID");
			Statement stmt2 = conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery(sql2);
			while(rs2.next())
			{
				tracks.add(new Track(rs2.getString("TITLE"), rs2.getInt("NUMBER")));
			}
				
			// Finish populating the Album and add to the return list
			album.setTracks(tracks);
			
			// Cleanup
			rs2.close();
			stmt2.close();
			rs1.close();
			stmt1.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new DatabaseException(e);
		}
		finally
		{
			// Cleanup Database
			if(conn != null)
			{
				try 
				{
					conn.close();
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
					throw new DatabaseException(e);
				}
			}
		}
		
		// Return Albums
		return album;
	}

	/**
	 * CRUD: create an entity
	 */
	public boolean create(Album album)
	{
		// DB Connection Info
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3307/music";
		String username = "root";
		String password = "root";
		
		// Insert Album and Tracks
		try 
		{
			// Connect to the Database
			conn = DriverManager.getConnection(url, username, password);

			// Insert an Album
			String sql1 = String.format("INSERT INTO  ALBUM(TITLE, ARTIST, YEAR) VALUES('%s', '%s', %d)", album.getTitle(), album.getArtist(), album.getYear());
			Statement stmt1 = conn.createStatement();
			stmt1.executeUpdate(sql1);
			
			// Get Auto-Increment PK back
			String sql2= "SELECT LAST_INSERT_ID() AS LAST_ID FROM ALBUM";
			ResultSet rs = stmt1.executeQuery(sql2);
			rs.next();
			String albumId = rs.getString("LAST_ID");
			rs.close();
			stmt1.close();
			
			// Insert all the Tracks
			Statement stmt2 = conn.createStatement();
			for(Track track : album.getTracks())
			{
				String sql3 = String.format("INSERT INTO TRACK(ALBUM_ID, TITLE, NUMBER) VALUES(%d, '%s', %d)", Integer.valueOf(albumId), track.getTitle(), track.getNumber());
				stmt2.executeUpdate(sql3);
			}
			stmt2.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new DatabaseException(e);
		}
		finally
		{
			// Cleanup Database
			if(conn != null)
			{
				try 
				{
					conn.close();
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
					throw new DatabaseException(e);
				}
			}
		}
		
		// Return OK
		return true;
	}
	
	/**
	 * CRUD: update an entity
	 */
	public boolean update(Album album)
	{
		return true;
	}
	
	/**
	 * CRUD: delete an entity
	 */
	public boolean delete(Album album)
	{
		return false;
	}
}
