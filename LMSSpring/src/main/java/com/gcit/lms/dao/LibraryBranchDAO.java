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

import com.gcit.lms.entity.BookCopy;
import com.gcit.lms.entity.BookLoan;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.entity.Publisher;

/**
 * @author Meirbek
 *
 */
public class LibraryBranchDAO extends BaseDAO implements ResultSetExtractor<List<LibraryBranch>> {

	public void addLibraryBranch(LibraryBranch libraryBranch) throws ClassNotFoundException, SQLException {
		template.update("insert into tbl_library_branch (branchName, branchAddress) values (?,?)", new Object[] {libraryBranch.getBranchName(), libraryBranch.getBranchAddress()});
	}
	
	public Integer addLibraryBranchWithID(LibraryBranch libraryBranch) throws ClassNotFoundException, SQLException {
		
		final String libraryBranchName = libraryBranch.getBranchName();
		final String libraryBranchAddress = libraryBranch.getBranchAddress();
		final String INSERT_SQL = "insert into tbl_library_branch (branchName, branchAddress) values (?,?)";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection)	throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
				ps.setString(1, libraryBranchName);
				ps.setString(2, libraryBranchAddress);
				ps.setInt(3, 3);
				return ps;
			}
		}, keyHolder);
		
		int branchId = keyHolder.getKey().intValue();

		return branchId;
		
	}
	
	public void updateLibraryBranch(LibraryBranch libraryBranch) throws ClassNotFoundException, SQLException {
		template.update("update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?", new Object[] {libraryBranch.getBranchName(), libraryBranch.getBranchAddress(), libraryBranch.getBranchId()});
	}
	
	public void deleteLibraryBranch(LibraryBranch libraryBranch) throws ClassNotFoundException, SQLException {
		template.update("delete from tbl_library_branch where branchName = ? and branchAddress = ?", new Object[] {libraryBranch.getBranchName(), libraryBranch.getBranchAddress()});
	}
	
	public List<LibraryBranch> readAllLibraryBranches(Integer pageNo, Integer pageSize) throws ClassNotFoundException, SQLException {
		setPageNo(pageNo);
		setPageSize(pageSize);
		return (List<LibraryBranch>) template.query("select * from tbl_library_branch", this);
	}

	public List<LibraryBranch> readLibraryBranchesByName(String name) throws ClassNotFoundException, SQLException {
		return (List<LibraryBranch>) template.query("select * from tbl_library_branch where branchName like ?", new Object[] {name}, this);
	}

	public List<LibraryBranch> readLibraryBranchesByAddress(String address) throws ClassNotFoundException, SQLException {
		return (List<LibraryBranch>) template.query("select * from tbl_library_branch where branchAddress like ?", new Object[] {address}, this);
	}
	
	public LibraryBranch readLibraryBranchByID(Integer branchId) throws ClassNotFoundException, SQLException {
		List<LibraryBranch> libBranches = (List<LibraryBranch>) template.query("select * from tbl_library_branch where branchId = ?", new Object[] {branchId}, this); 
		if (libBranches != null && libBranches.size() > 0)
			return libBranches.get(0);
		else return null;
	}

	/*public LibraryBranch readLibraryBranchByBookCopy(BookCopy bookCopy) throws ClassNotFoundException, SQLException {
		return ((List<LibraryBranch>) readAll("select * from tbl_library_branch where branchId IN "
										+	" (select branchId from tbl_book_copies where branchId = ?)", new Object[] {bookCopy.getLibraryBranch().getBranchName()})).get(0);
	}*/
	
	/*public LibraryBranch readLibraryBranchByBookLoan(BookLoan bookLoan) throws ClassNotFoundException, SQLException {
		return ((List<LibraryBranch>) readAll("select * from tbl_library_branch where branchId IN "
										+	" (select branchId from tbl_book_loans where branchId = ?)", new Object[] {bookLoan.getLibraryBranch().getBranchName()})).get(0);
	}*/
	
	public Integer getCount() throws ClassNotFoundException, SQLException{
		return template.query("select * from tbl_library_branch", this).size();
	}
	
	public List<LibraryBranch> extractData(ResultSet rs) throws SQLException {
		List<LibraryBranch> libBranches = new ArrayList<LibraryBranch>();
		
		while (rs.next()) {
			LibraryBranch libBranch = new LibraryBranch();
			libBranch.setBranchName(rs.getString("branchName"));
			libBranch.setBranchAddress(rs.getString("branchAddress"));
			libBranch.setBranchId(rs.getInt("branchId"));
			
			libBranches.add(libBranch);
		}
		if (libBranches.size() > 0)
			return libBranches;
		else return null;
	}

}
