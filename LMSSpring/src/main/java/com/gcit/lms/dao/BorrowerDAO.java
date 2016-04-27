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
import org.springframework.transaction.annotation.Transactional;

import com.gcit.lms.entity.BookCopy;
import com.gcit.lms.entity.BookLoan;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.LibraryBranch;

/**
 * @author Meirbek
 *
 */
public class BorrowerDAO extends BaseDAO implements ResultSetExtractor<List<Borrower>>{

	public void addBorrower(Borrower bor) throws ClassNotFoundException, SQLException {
		template.update("insert into tbl_borrower (name, address, phone) values (?,?,?)", new Object[] {bor.getBorrowerName(), bor.getBorrowerAddress(), bor.getBorrowerPhone()});
	}
	
	public Integer addBorrowerWithID(Borrower bor) throws ClassNotFoundException, SQLException {
		final String borName = bor.getBorrowerName();
		final String borAddress = bor.getBorrowerAddress();
		final String borPhone = bor.getBorrowerPhone();
		final String INSERT_SQL = "insert into tbl_borrower (name, address, phone) values (?,?,?)";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection)	throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setString(1, borName);
				ps.setString(2, borAddress);
				ps.setString(3, borPhone);
				ps.setInt(4, 4);
				return ps;
			}
		}, keyHolder);
		
		int cardNo = keyHolder.getKey().intValue();

		return cardNo;
	}
	
	public void updateBorrower(Borrower bor) throws ClassNotFoundException, SQLException {
		template.update("update tbl_borrower set name = ?, address = ?, phone = ? where cardNo = ?", new Object[] {bor.getBorrowerName(), bor.getBorrowerAddress(), bor.getBorrowerPhone(), bor.getCardNo()});
	}
	
	public void deleteBorrower(Borrower bor) throws ClassNotFoundException, SQLException {
		template.update("delete from tbl_borrower where cardNo = ?", new Object[] {bor.getCardNo()});
	}
	
	public List<Borrower> readAllBorrowers(Integer pageNo, Integer pageSize) throws ClassNotFoundException, SQLException {
		setPageNo(pageNo);
		setPageSize(pageSize);
		return (List<Borrower>) template.query("select * from tbl_borrower", this);
	}

	public List<Borrower> readBorrowerByName(String name) throws ClassNotFoundException, SQLException {
		return (List<Borrower>) template.query("select * from tbl_borrower where name like ?", new Object[] {name}, this);
	}

	public List<Borrower> readBorrowersByAddress(String address) throws ClassNotFoundException, SQLException {
		return (List<Borrower>) template.query("select * from tbl_borrower where address like ?", new Object[] {address}, this);
	}
	
	public List<Borrower> readBorrowersByPhone(String phone) throws ClassNotFoundException, SQLException {
		return (List<Borrower>) template.query("select * from tbl_borrower where phone like ?", new Object[] {phone}, this);
	}

	public Borrower readBorrowerByID(Integer cardNo) throws ClassNotFoundException, SQLException {
		List<Borrower> borrowers = (List<Borrower>) template.query("select * from tbl_borrower where cardNo = ?", new Object[] {cardNo}, this);
		if (borrowers != null && borrowers.size() > 0)
			return borrowers.get(0);
		else return null;
	}
	
	/*
	public Borrower readBorrowerByBookLoan(BookLoan bookLoan) throws ClassNotFoundException, SQLException {
		return ((List<Borrower>) readAll("select * from tbl_library_branches where branchId IN (select branchId from tbl_book_loans where branchId = ?)", new Object[] {bookLoan.getLibraryBranch().getBranchName()})).get(0);
	}
	*/
	
	public Integer getCount() throws ClassNotFoundException, SQLException{
		return template.query("select * from tbl_borrower", this).size();
	}
	
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
		List<Borrower> borrowers = new ArrayList<Borrower>();
		//List<BookLoan> bookLoans = new ArrayList<BookLoan>();
		
		while (rs.next()) {
			Borrower bor = new Borrower();
			bor.setBorrowerName(rs.getString("name"));
			bor.setBorrowerAddress(rs.getString("address"));
			bor.setBorrowerPhone(rs.getString("phone"));
			bor.setCardNo(rs.getInt("cardNo"));
			
			borrowers.add(bor);
		}
		if (borrowers.size() > 0)
			return borrowers;
		else return null;
	}

}
