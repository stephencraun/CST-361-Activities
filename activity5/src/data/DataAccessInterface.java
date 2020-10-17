package data;

import java.util.List;

public interface DataAccessInterface <T> 
{
	public List<T> findAll();
	public T findById(int id);
	public T findBy(T t);
	public boolean create(T t);
	public boolean update(T t);
	public boolean delete(T t);
}