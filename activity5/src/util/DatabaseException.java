package util;

public class DatabaseException extends RuntimeException
{
	private static final long serialVersionUID = 8749912411505566655L;
	
	public DatabaseException(Throwable e)
	{
		super(e);
	}
}
