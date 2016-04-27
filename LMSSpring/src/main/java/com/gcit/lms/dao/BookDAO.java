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
import com.gcit.lms.entity.BookCopy;
import com.gcit.lms.entity.BookLoan;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;

/**
 * @author Meirbek
 *
 */
public class BookDAO extends BaseDAO implements ResultSetExtractor<List<Book>>{

	public void addBook(Book book) throws ClassNotFoundException, SQLException
	{
		if (book.getPublisher() != null)
			template.update("insert into tbl_book (title, pubId) values (?,?)", new Object[] {book.getTitle(), book.getPublisher().getPublisherId()});
		else template.update("insert into tbl_book (title) values (?)", new Object[] {book.getTitle()});
	}
	
	public Integer addBookWithID(Book book) throws ClassNotFoundException, SQLException
	{
		String insert_sql;
		Integer pub = null;
		if (book.getPublisher() != null) {
			insert_sql = "insert into tbl_book (title, pubId) values (?,?)";
			pub = book.getPublisher().getPublisherId();
		} else { 
			insert_sql = "insert into tbl_book (title) values (?)";
			
		}
		final Integer pubId = pub;
		final String title = book.getTitle();
		final String INSERT_SQL = insert_sql;
		
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection)	throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setString(1, title);
				ps.setInt(2, pubId);
				ps.setInt(3, 3);
				return ps;
			}
		}, keyHolder);
		
		int bookId = keyHolder.getKey().intValue();
		
		for (Author a: book.getAuthors()) {
			template.update("insert into tbl_book_authors (authorId, bookId) values (?,?)", new Object[] {a.getAuthorId(), bookId});
		}
		return bookId;
		
	}
	
	public void updateBook(Book book) throws ClassNotFoundException, SQLException
	{
		if (book.getPublisher() != null)
			template.update("update tbl_book set title = ?, pubId = ? where bookId = ?", new Object[] {book.getTitle(), book.getPublisher().getPublisherId(), book.getBookId()});
		else template.update("update tbl_book set title = ?, pubId = null where bookId = ?", new Object[] {book.getTitle(), book.getBookId()});
	}

	public void deleteBook(Book book) throws ClassNotFoundException, SQLException
	{
		template.update("delete from tbl_book where bookId = ?", new Object[] {book.getBookId()});
	}
	
	public List<Book> readAllBooks(Integer pageNo, Integer pageSize) throws ClassNotFoundException, SQLException
	{		
		setPageNo(pageNo);
		setPageSize(pageSize);
		return (List<Book>) template.query("select * from tbl_book", this); 
	}
	
	public List<Book> readBooksByTitle(String title) throws ClassNotFoundException, SQLException {
		return (List<Book>) template.query("select * from tbl_book where title like ?", new Object[] {title}, this);
	}
	
	public Book readBookByID(Integer bookId) throws ClassNotFoundException, SQLException {
		List<Book> books = (List<Book>) template.query("select * from tbl_book where bookId = ?", new Object[] {bookId}, this);
		if (books != null && books.size() > 0)
			return books.get(0);
		else return null;
	}
	
	public List<Book> readBookByAuthorID(Integer authorId) throws ClassNotFoundException, SQLException {
		List<Book> books = (List<Book>) template.query("select * from tbl_book as b "
				+ "join tbl_book_authors as ba "
				+ "on b.bookId = ba.bookId "
				+ "join tbl_authors as a "
				+ "on ba.authorId = a.authorId where a.authorId = ?", new Object[] {authorId}, this);
		return books;
	}
	
	public Integer getCount() throws ClassNotFoundException, SQLException{
		List<Book> books = template.query("select * from tbl_book", this);
		if (books != null) return books.size();
		else return null;
	}
	
	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<Book>();
		/*AuthorDAO authorDAO = new AuthorDAO(getConnection());
		GenreDAO genreDAO = new GenreDAO(getConnection());*/
		
		while (rs.next()) {
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("pubId"));
			System.out.println(p.getPublisherId());
			b.setPublisher(p);			
			books.add(b);
		}		
		if (books.size() > 0)
			return books;
		else return null;
	}

	public void linkAuthors(Book book) throws ClassNotFoundException, SQLException {
		template.update("delete from tbl_book_authors where bookId = ?", new Object[] {book.getBookId()});
		if (book != null && book.getAuthors() != null && book.getAuthors().size() > 0)
			for (Author a: book.getAuthors())
				template.update("insert into tbl_book_authors (authorId, bookId) values (?,?)", new Object[] {a.getAuthorId(), book.getBookId()});
	}


	public void linkGenres(Book book) throws ClassNotFoundException, SQLException {
		template.update("delete from tbl_book_genres where bookId = ?", new Object[] {book.getBookId()});
		if (book != null && book.getGenres() != null && book.getGenres().size() > 0)
			for (Genre g: book.getGenres())
				template.update("insert into tbl_book_genres (genre_id, bookId) values (?,?)", new Object[] {g.getGenreId(), book.getBookId()});
	}
}
