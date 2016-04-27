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
import com.gcit.lms.entity.Publisher;

/**
 * @author Meirbek
 *
 */
public class PublisherDAO  extends BaseDAO implements ResultSetExtractor<List<Publisher>> {

	public void addPublisher(Publisher publisher) throws SQLException, ClassNotFoundException {
		template.update("insert into tbl_publisher (publisherName, publisherAddress, publisherPhone) values (?,?,?) ", new Object[] {publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone()});
	}
	
	public Integer addPublisherWithID(Publisher publisher) throws SQLException, ClassNotFoundException {
		final String publisherName = publisher.getPublisherName();
		final String publisherAddress = publisher.getPublisherAddress();
		final String publisherPhone = publisher.getPublisherPhone();
		final String INSERT_SQL = "insert into tbl_publisher (publisherName, publisherAddress, publisherPhone) values (?,?,?)";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection)	throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setString(1, publisherName);
				ps.setString(2, publisherAddress);
				ps.setString(3, publisherPhone);
				ps.setInt(4, 4);
				return ps;
			}
		}, keyHolder);
		
		int publisherId = keyHolder.getKey().intValue();

		return publisherId;
	}
	
	public void updatePublisher(Publisher publisher) throws SQLException, ClassNotFoundException {
		if (publisher.getPublisherName() != null && publisher.getPublisherAddress() != null && publisher.getPublisherPhone() != null)
			template.update("update tbl_publisher set publisherName = ?, publisherAddress = ?, publisherPhone = ? where publisherId = ? ", new Object[] {publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone(), publisher.getPublisherId()});
		else if (publisher.getPublisherName() != null && publisher.getPublisherAddress() != null)
			template.update("update tbl_publisher set publisherName = ?, publisherAddress = ? where publisherId = ? ", new Object[] {publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherId()});
		else if (publisher.getPublisherName() != null && publisher.getPublisherPhone() != null)
			template.update("update tbl_publisher set publisherName = ?, publisherPhone = ? where publisherId = ? ", new Object[] {publisher.getPublisherName(), publisher.getPublisherPhone(), publisher.getPublisherId()});
		else if (publisher.getPublisherAddress() != null && publisher.getPublisherPhone() != null)
			template.update("update tbl_publisher set publisherAddress = ?, publisherPhone = ? where publisherId = ? ", new Object[] {publisher.getPublisherAddress(), publisher.getPublisherPhone(), publisher.getPublisherId()});
		else if (publisher.getPublisherName() != null)
			template.update("update tbl_publisher set publisherName = ? where publisherId = ? ", new Object[] {publisher.getPublisherName(), publisher.getPublisherId()});
		else if (publisher.getPublisherAddress() != null)
			template.update("update tbl_publisher set publisherAddress = ? where publisherId = ? ", new Object[] {publisher.getPublisherAddress(), publisher.getPublisherId()});
		else
			template.update("update tbl_publisher set publisherPhone = ? where publisherId = ? ", new Object[] {publisher.getPublisherPhone(), publisher.getPublisherId()});
	}
	
	public void deletePublisher(Publisher publisher) throws SQLException, ClassNotFoundException {
		template.update("delete from tbl_publisher where publisherId = ?", new Object[] {publisher.getPublisherId()});
	}
	
	public List<Publisher> readAllPublishers(Integer pageNo, Integer pageSize) throws ClassNotFoundException, SQLException {
		setPageNo(pageNo);
		setPageSize(pageSize);
		return (List<Publisher>) template.query("select * from tbl_publisher", this);
	}
	
	public List<Publisher> readPublishersByName(String name) throws ClassNotFoundException, SQLException {
		return (List<Publisher>) template.query("select * from tbl_publisher where publisherName like ?", new Object[] {name}, this);
	}
	
	public List<Publisher> readPublishersByAddress(String address) throws ClassNotFoundException, SQLException {
		return (List<Publisher>) template.query("select * from tbl_publisher where publisherAddress like ?", new Object[] {address}, this);
	}
	
	public List<Publisher> readPublishersByPhone(String phone) throws ClassNotFoundException, SQLException {
		return (List<Publisher>) template.query("select * from tbl_publisher where publisherPhone like ?", new Object[] {phone}, this);
	}
	
	public Integer getCount() throws ClassNotFoundException, SQLException{
		return template.query("select * from tbl_publisher", this).size();
	}
	
	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException {
		List<Publisher> publishers = new ArrayList<Publisher>();
		
		while (rs.next()) {
			Publisher p = new Publisher();
			p.setPublisherName(rs.getString("publisherName"));
			p.setPublisherAddress(rs.getString("publisherAddress"));
			p.setPublisherPhone(rs.getString("publisherPhone"));
			p.setPublisherId(rs.getInt("publisherId"));
			
			publishers.add(p);
		}
		if (publishers.size() > 0)
			return publishers;
		else return null;
	}


	public void linkBooks(Publisher publisher) throws ClassNotFoundException, SQLException {
		template.update("update tbl_book set pubId = null where pubId = ?", new Object[] {publisher.getPublisherId()});
		if (publisher != null && publisher.getBooks() != null && publisher.getBooks().size() > 0)
			for (Book b: publisher.getBooks())
				template.update("update tbl_book set pubId = ? where bookId = ?", new Object[] {publisher.getPublisherId(), b.getBookId()});
	}

	public Publisher readPublisherByID(Integer publisherId) throws ClassNotFoundException, SQLException {
		List<Publisher> publishers = (List<Publisher>) template.query("select * from tbl_publisher where publisherId = ?", new Object[] {publisherId}, this);
		if (publishers != null && publishers.size() > 0)
			return publishers.get(0);
		else return null;
	}

}
