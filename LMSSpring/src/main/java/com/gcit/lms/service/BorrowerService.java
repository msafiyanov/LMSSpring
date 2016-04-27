/**
 * 
 */
package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookCopyDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoanDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopy;
import com.gcit.lms.entity.BookLoan;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.LibraryBranch;

/**
 * @author Meirbek
 *
 */
public class BorrowerService {
	
	@Autowired
	LibraryBranchDAO lbDAO;
	
	@Autowired
	BookLoanDAO blDAO;
	
	@Autowired
	BookCopyDAO bcDAO;
	
	public List<LibraryBranch> getAllLibraryBranches(Integer pageNo, Integer pageSize) throws ClassNotFoundException, SQLException{
		return lbDAO.readAllLibraryBranches(pageNo, pageSize);
	}
	
	public List<BookCopy> getExistingBooks() throws ClassNotFoundException, SQLException{
		return bcDAO.readExistingBookCopies();
	}

	@Transactional
	public void addBookLoan(BookLoan bL) throws ClassNotFoundException, SQLException {
		blDAO.deleteBookLoan(bL);
	}
	
	@Transactional
	public void returnBookLoan(BookLoan bL) throws ClassNotFoundException, SQLException {
		blDAO.deleteBookLoan(bL);
	}
	
	public List<BookLoan> getBookLoansByBorrower(Borrower bor) throws ClassNotFoundException, SQLException {
		return blDAO.readBookLoansByBorrower(bor);
	}
	
	@Transactional
	public void updateBookCopies(BookCopy bC) throws ClassNotFoundException, SQLException {
			bcDAO.updateBookCopy(bC);
	}

	public BookLoan getBookLoanByAll(Integer bookId,
			Integer cardNo, Integer branchId) throws ClassNotFoundException, SQLException {
		return blDAO.readBookLoanByAll(bookId, cardNo, branchId);
	}
	
	public BookCopy getBookCopyByAll(Integer bookId, Integer branchId) throws ClassNotFoundException, SQLException {
		return bcDAO.getBookCopy(bookId, branchId);
	}
		
	public static java.sql.Date getCurrentDate() {		
	    return java.sql.Date.valueOf(java.time.LocalDate.now());
	}
	
	public static java.sql.Date getWeekFromDate() {
		return java.sql.Date.valueOf(java.time.LocalDate.now().plusDays(7));	
	}

	public int getNoOfCopies(Book book, LibraryBranch libraryBranch) throws ClassNotFoundException, SQLException {
		return bcDAO.getNoOfCopies(book, libraryBranch);
	}
}
