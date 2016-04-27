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

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.LibraryBranch;

/**
 * @author Meirbek
 *
 */
public class GenreDAO extends BaseDAO implements ResultSetExtractor<List<Genre>> {

	public void addGenre(Genre genre) throws ClassNotFoundException, SQLException {
		template.update("insert into tbl_genre (genre_name) values (?)", new Object[] {genre.getGenreName()});
	}
	
	public Integer addGenreWithID(Genre genre) throws ClassNotFoundException, SQLException {
		final String genreName = genre.getGenreName();
		final String INSERT_SQL = "insert into tbl_genre (genre_name) values(?)";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection)	throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setString(1, genreName);
				ps.setInt(2, 2);
				return ps;
			}
		}, keyHolder);
		
		int genreId = keyHolder.getKey().intValue();
		
		for (Book b: genre.getBooks()) {
			template.update("insert into tbl_book_genres (genre_id, bookId) values (?,?)", new Object[] {genreId, b.getBookId()});
		}
		return genreId;
	}
	
	public void updateGenre(Genre genre) throws ClassNotFoundException, SQLException {
		template.update("update tbl_genre set genre_name = ? where genre_id = ?", new Object[] {genre.getGenreName(), genre.getGenreId()});
	}
	
	public void deleteGenre(Genre genre) throws ClassNotFoundException, SQLException {
		template.update("delete from tbl_genre where genre_id = ?", new Object[] {genre.getGenreId()});
	}
	
	public List<Genre> readAllGenres() throws ClassNotFoundException, SQLException {
		return (List<Genre>) template.query("select * from tbl_genre", this);
	}
	
	public List<Genre> readGenresByName(String name) throws ClassNotFoundException, SQLException {
		return (List<Genre>) template.query("select * from tbl_genre where genre_name like ?", new Object[] {name}, this);
	}
	
	public Integer getCount() throws ClassNotFoundException, SQLException{
		return template.query("select * from tbl_genre", this).size();
	}
	
	public List<Genre> extractData(ResultSet rs) throws SQLException {
		List<Genre> genres = new ArrayList<Genre>();
		
		while (rs.next()) {
			Genre genre = new Genre();
			genre.setGenreId(rs.getInt("genre_id"));
			genre.setGenreName(rs.getString("genre_name"));
			genres.add(genre);
		}
		
		if (genres.size() > 0)
			return genres;
		else return null;
	}

	public Genre readGenreByID(Integer genreId) throws ClassNotFoundException, SQLException {
		List<Genre> genres = (List<Genre>) template.query("select * from tbl_genre where genre_id = ?", new Object[] {genreId}, this);
		if (genres != null && genres.size() > 0)
			return genres.get(0);
		else return null;
	}	
}
