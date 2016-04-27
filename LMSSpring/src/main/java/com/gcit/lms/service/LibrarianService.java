/**
 * 
 */
package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

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
import com.gcit.lms.entity.LibraryBranch;

/**
 * @author Meirbek
 *
 */
public class LibrarianService {
	
	@Autowired
	BookDAO bDAO;
	
	@Autowired
	LibraryBranchDAO lbDAO;
	
	@Autowired
	BookCopyDAO bcDAO;
	
	public List<LibraryBranch> getAllLibraryBranches(Integer pageNo, Integer pageSize) throws ClassNotFoundException, SQLException{
		return lbDAO.readAllLibraryBranches(pageNo, pageSize);
	}
	
	public void updateLibraryBranch(LibraryBranch libBranch) throws ClassNotFoundException, SQLException {
		lbDAO.updateLibraryBranch(libBranch);
	}
	
	public void addBookCopies(BookCopy bookCopy) throws ClassNotFoundException, SQLException {
		bcDAO.addBookCopy(bookCopy);
	}
	
	public List<Book> getAllBooks(Integer pageNo, Integer pageSize) throws ClassNotFoundException, SQLException{
		return bDAO.readAllBooks(pageNo, pageSize);
	}

}
