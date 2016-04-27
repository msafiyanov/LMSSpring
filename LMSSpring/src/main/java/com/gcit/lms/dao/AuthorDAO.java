/**
 * 
 */
package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;

/**
 * @author Meirbek
 *
 */
public class AuthorDAO extends BaseDAO implements ResultSetExtractor<List<Author>>{
	
	
	public void addAuthor(Author author) throws ClassNotFoundException, SQLException
	{
		template.update("insert into tbl_author (authorName) values (?)", new Object[] {author.getAuthorName()});
	}
	
	public Integer addAuthorWithID(Author author) throws ClassNotFoundException, SQLException
	{
		final String authorName = author.getAuthorName();
		final String INSERT_SQL = "insert into tbl_author (authorName) values (?)";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection)	throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setString(1, authorName);
				ps.setInt(2, 2);
				return ps;
			}
		}, keyHolder);
		
		int authorId = keyHolder.getKey().intValue();
		
		for (Book b: author.getBooks()) {
			template.update("insert into tbl_book_authors (authorId, bookId) values (?,?)", new Object[] {authorId, b.getBookId()});
		}
		return authorId;
	}
	
	public void updateAuthor(Author author) throws ClassNotFoundException, SQLException
	{
		template.update("update tbl_author set authorName = ? where authorId = ?", new Object[] {author.getAuthorName(), author.getAuthorId()});
	}
	
	public void deleteAuthor(Author author) throws ClassNotFoundException, SQLException
	{
		template.update("delete from tbl_author where authorId = ?", new Object[] {author.getAuthorId()});
	}
	
	public List<Author> readAllAuthors(int pageNo, int pageSize) throws ClassNotFoundException, SQLException {
		setPageNo(pageNo);
		setPageSize(pageSize);
		return template.query("select * from tbl_author", this);
	}
	
	public List<Author> readAuthorsByName(String name) throws ClassNotFoundException, SQLException{
		return template.query("select * from tbl_author where authorName like ?", new Object[] {name}, this);
	}
	
	public List<Author> readAuthorsBySearch(String searchString, String searchType, Integer pageNo, Integer pageSize) throws ClassNotFoundException, SQLException {
		setPageNo(pageNo);
		setPageSize(pageSize);
		String query = "select a.authorId, a.authorName from tbl_author as a "
				+ "left join tbl_book_authors as ba "
				+ "on a.authorId = ba.authorId "
				+ "left join tbl_book as b "
				+ "on ba.bookId = b.bookId ";
		Object[] vals = new Object[] {"%"+searchString+"%"};
		
		if (searchType.equals("All")) {
				query += "where a.authorName like ? OR b.title like ? group by a.authorId";
				vals = new Object[] {"%"+searchString+"%", "%"+searchString+"%"};
			}
		else if (searchType.equals("Books"))
			query += "where b.title like ? group by a.authorId";
		else if (searchType.equals("Author Name")) 
			query += "where a.authorName like ? group by a.authorId";
		return (List<Author>) template.query(query, vals, this);
	}
	
	public Author readAuthorByID(Integer authorId) throws ClassNotFoundException, SQLException {
		List<Author> authors = (List<Author>) template.query("select * from tbl_author where authorId = ?", new Object[] {authorId}, this);
		if (authors != null && authors.size() > 0)
			return authors.get(0);
		else return null;
	}

	public void linkBooks(Author author) throws ClassNotFoundException, SQLException {
		template.update("delete from tbl_book_authors where authorId = ?", new Object[] {author.getAuthorId()});
		if (author != null && author.getBooks() != null && author.getBooks().size() > 0)
			for (Book b: author.getBooks())
				template.update("insert into tbl_book_authors (authorId, bookId) values (?,?)", new Object[] {author.getAuthorId(), b.getBookId()});
	}
	
	public Integer getCount() throws ClassNotFoundException, SQLException{
		List<Author> authors = template.query("select * from tbl_author", this);
		if (authors != null) return authors.size();
		else return null;
	}
	
	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<Author>();
		while(rs.next()) {			
			Author author = new Author();
			author.setAuthorId(rs.getInt("authorId"));
			author.setAuthorName(rs.getString("authorName"));
			
			authors.add(author);			
		}		
		if (authors.size() > 0)
			return authors;
		else return null;		
	}

}
