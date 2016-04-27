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
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopy;
import com.gcit.lms.entity.BookLoan;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.entity.Publisher;

/**
 * @author Meirbek
 *
 */
public class AdministratorService {

	@Autowired
	AuthorDAO aDAO;
	
	@Autowired
	BookDAO bDAO;
	
	@Autowired
	PublisherDAO pDAO;
	
	@Autowired
	GenreDAO gDAO;
	
	@Autowired
	LibraryBranchDAO lbDAO;
	
	@Autowired
	BorrowerDAO borDAO;
	
	@Autowired
	BookLoanDAO blDAO;
	
	@Autowired
	BookCopyDAO bcDAO;
	
	public List<BookCopy> getAllNonExistingBookCopies() throws ClassNotFoundException, SQLException{
		return bcDAO.readNonExistingBookCopies();	
	}
	
	@Transactional
	public void createAuthor(Author author) throws Exception {
		if (author.getAuthorName() != null) {
			if (author.getAuthorName().length() > 45 ) 
				throw new Exception("Author name cannot be more than 45 characters.");
			else
				aDAO.addAuthor(author);
		}
	}
	
	@Transactional
	public Integer createAuthorWithID(Author author) throws ClassNotFoundException, SQLException{
		Integer authorId = null;
		authorId = aDAO.addAuthorWithID(author);
		return authorId;
	}
	
	public List<Author> getAllAuthors(Integer pageNo, Integer pageSize) throws ClassNotFoundException, SQLException{
			List<Author> authors = aDAO.readAllAuthors(pageNo, pageSize);
			for (Author a: authors) {
				List<Book> books = bDAO.readBookByAuthorID(a.getAuthorId());
				a.setBooks(books);
			}
		return authors;				
	}
	
	@Transactional
	public void updateAuthor(Author author) throws Exception {
		if (author.getAuthorName() != null) {
			if (author.getAuthorName().length() > 45 ) 
				throw new Exception("Author name cannot be more than 45 characters.");
			else
				aDAO.updateAuthor(author);
		}
	}
	
	@Transactional
	public void deleteAuthor(Author author) throws ClassNotFoundException, SQLException {
		aDAO.deleteAuthor(author);
	}
	
	public Integer getAuthorCount() throws ClassNotFoundException, SQLException{
		return aDAO.getCount();
	}
	
	@Transactional
	public void createBook(Book book) throws ClassNotFoundException, SQLException {
		bDAO.addBook(book);
	}
	
	@Transactional
	public Integer createBookWithID(Book book) throws ClassNotFoundException, SQLException {
		return bDAO.addBookWithID(book);
	}
	
	public List<Book> getAllBooks(Integer pageNo, Integer pageSize) throws ClassNotFoundException, SQLException{
		return bDAO.readAllBooks(pageNo, pageSize);
	}

	@Transactional
	public void updateBook(Book book) throws ClassNotFoundException, SQLException {
		bDAO.updateBook(book);
	}
	
	@Transactional
	public void deleteBook(Book book) throws ClassNotFoundException, SQLException {
		bDAO.deleteBook(book);
	}
	
	public Integer getBookCount() throws ClassNotFoundException, SQLException{
		return bDAO.getCount();
	}
	
	@Transactional
	public void createPublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		pDAO.addPublisher(publisher);
	}
	
	@Transactional
	public Integer createPublisherWithID(Publisher publisher) throws ClassNotFoundException, SQLException {
		Integer publisherId = null;
		publisherId = pDAO.addPublisherWithID(publisher);
		return publisherId;
	}
	
	public List<Publisher> getAllPublishers(Integer pageNo, Integer pageSize) throws ClassNotFoundException, SQLException{
		return pDAO.readAllPublishers(pageNo, pageSize);
	}
	
	@Transactional
	public void updatePublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		pDAO.updatePublisher(publisher);
	}
	
	@Transactional
	public void deletePublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		pDAO.deletePublisher(publisher);
	}
	
	public Integer getPublisherCount() throws ClassNotFoundException, SQLException{
		return pDAO.getCount();
	}
	
	@Transactional
	public void createBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
		borDAO.addBorrower(borrower);
	}
	
	@Transactional
	public Integer createBorrowerWithID(Borrower borrower) throws ClassNotFoundException, SQLException {
		return borDAO.addBorrowerWithID(borrower);
	}
	
	public List<Borrower> getAllBorrowers(Integer pageNo, Integer pageSize) throws ClassNotFoundException, SQLException{
		return borDAO.readAllBorrowers(pageNo, pageSize);
	}

	public Borrower getBorrowerByID(Integer cardNo) throws ClassNotFoundException, SQLException {
		return borDAO.readBorrowerByID(cardNo);
	}
	
	@Transactional
	public void updateBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
		borDAO.updateBorrower(borrower);
	}
	
	@Transactional
	public void deleteBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
		borDAO.deleteBorrower(borrower);
	}
	
	public Integer getBorrowerCount() throws ClassNotFoundException, SQLException{
		return borDAO.getCount();
	}
	
	@Transactional
	public void createLibraryBranch(LibraryBranch libBranch) throws ClassNotFoundException, SQLException {
		lbDAO.addLibraryBranch(libBranch);
	}
	
	@Transactional
	public Integer createLibraryBranchWithID(LibraryBranch libBranch) throws ClassNotFoundException, SQLException {
		return lbDAO.addLibraryBranchWithID(libBranch);
	}
	
	public List<LibraryBranch> getAllLibraryBranches(Integer pageNo, Integer pageSize) throws ClassNotFoundException, SQLException{
		return lbDAO.readAllLibraryBranches(pageNo, pageSize);
	}
	
	public LibraryBranch getLibraryBranchByID(Integer branchId) throws ClassNotFoundException, SQLException {
		return lbDAO.readLibraryBranchByID(branchId);
	}
	
	@Transactional
	public void updateLibraryBranch(LibraryBranch libBranch) throws ClassNotFoundException, SQLException {
		lbDAO.updateLibraryBranch(libBranch);
	}
	
	@Transactional
	public void deleteLibraryBranch(LibraryBranch libBranch) throws ClassNotFoundException, SQLException {
		lbDAO.deleteLibraryBranch(libBranch);
	}
	
	
	public Integer getLibraryBranchCount() throws ClassNotFoundException, SQLException{
		return lbDAO.getCount();
	}
	
	public List<BookLoan> getAllBookLoans() throws ClassNotFoundException, SQLException{
		return blDAO.readAllBookLoans();
	}
	
	@Transactional
	public void updateBookLoanDueDate(BookLoan bL) throws ClassNotFoundException, SQLException {
		blDAO.updateBookLoanDueDate(bL);
	}

	public Author getAuthorByID(Integer authorId) throws ClassNotFoundException, SQLException {
		return aDAO.readAuthorByID(authorId);
	}
	
	public Book getBookByID(Integer bookId) throws ClassNotFoundException, SQLException {
		return bDAO.readBookByID(bookId);
	}
	
	@Transactional
	public void linkAuthorWithBooks(Author author) throws ClassNotFoundException, SQLException {
			aDAO.linkBooks(author);
	}
	
	@Transactional
	public void linkBookWithAuthors(Book book) throws ClassNotFoundException, SQLException {
			bDAO.linkAuthors(book);
	}

	@Transactional
	public void createGenre(Genre genre) throws ClassNotFoundException, SQLException {
		gDAO.addGenre(genre);
	}

	@Transactional
	public Integer createGenreWithID(Genre genre) throws ClassNotFoundException, SQLException{
		Integer genreId = null;
		genreId = gDAO.addGenreWithID(genre);
		return genreId;
	}

	public List<Genre> getAllGenres() throws ClassNotFoundException, SQLException{
		return gDAO.readAllGenres();
	}

	@Transactional
	public void updateGenre(Genre genre) throws ClassNotFoundException, SQLException {
		gDAO.updateGenre(genre);
	}

	@Transactional
	public void deleteGenre(Genre genre) throws ClassNotFoundException, SQLException {
		gDAO.deleteGenre(genre);
	}
	
	public Integer getGenreCount() throws ClassNotFoundException, SQLException{
		return gDAO.getCount();
	}

	public Genre getGenreByID(Integer genreId) throws ClassNotFoundException, SQLException {
		return gDAO.readGenreByID(genreId);
	}

	@Transactional
	public void linkBookWithGenres(Book book) throws ClassNotFoundException, SQLException {
		bDAO.linkGenres(book);
	}

	@Transactional
	public void linkPublisherWithBooks(Publisher publisher) throws ClassNotFoundException, SQLException {
		pDAO.linkBooks(publisher);
	}

	public Publisher getPublisherByID(Integer publisherId) throws ClassNotFoundException, SQLException {
		return pDAO.readPublisherByID(publisherId);
	}

	public BookLoan getBookLoanByAll(Integer bookId, Integer cardNo, Integer branchId) throws ClassNotFoundException, SQLException {
		return blDAO.readBookLoanByAll(bookId, cardNo, branchId);
	}

	public List<Author> getAuthorsBySearch(String searchString, String searchType, Integer pageNo, Integer pageSize) throws ClassNotFoundException, SQLException {
			return aDAO.readAuthorsBySearch(searchString, searchType, pageNo, pageSize);
	}

}
