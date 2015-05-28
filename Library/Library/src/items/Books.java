package items;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Struct;
import java.sql.Timestamp;

import logics.MyConnection;

public class Books implements Item {

	private Statement statement = null;
	private ResultSet resultSet = null;

	private int bookID;
	private String title;
	private Timestamp publicationDate;
	private int numberOfBooks;
	private int numberOfAvailableBooks;
	private int numberOfBorrowedBooks;
	private Array booksArray;

	public Books() {
	}

	public Books(int bookID, String title, Timestamp publicationDate,
			int numberOfBooks, int numberOfAvailableBooks,
			int numberOfBorrowedBooks, boolean available) {
		super();
		this.bookID = bookID;
		this.title = title;
		this.publicationDate = publicationDate;
		this.numberOfBooks = numberOfBooks;
		this.numberOfAvailableBooks = numberOfAvailableBooks;
		this.numberOfBorrowedBooks = numberOfBorrowedBooks;
	}

	public int getBookID() {
		return bookID;
	}

	public void setBookID(int bookID) {
		this.bookID = bookID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String newTitle) {
		this.title = newTitle;
	}

	public Timestamp getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Timestamp publicationDate) {
		this.publicationDate = publicationDate;
	}

	public int getNumberOfBooks() {
		return numberOfBooks;
	}

	public void setNumberOfBooks(int numberOfBooks) {
		this.numberOfBooks = numberOfBooks;
	}

	public int getNumberOfAvailableBooks() {
		return numberOfAvailableBooks;
	}

	public void setNumberOfAvailableBooks(int numberOfAvailableBooks) {
		this.numberOfAvailableBooks = numberOfAvailableBooks;
	}

	public int getNumberOfBorrowedBooks() {
		return numberOfBorrowedBooks;
	}

	public void setNumberOfBorrowedBooks(int numberOfBorrowedBooks) {
		this.numberOfBorrowedBooks = numberOfBorrowedBooks;
	}

	public Array getBooksArray() {
		return booksArray;
	}

	public void setBooksArray(Array booksArray) {
		this.booksArray = booksArray;
	}

	public void select(String query) throws SQLException {
		statement = MyConnection.getConnection().createStatement();
		resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			bookID = resultSet.getInt(1);
			numberOfBooks = resultSet.getInt(2);
			numberOfAvailableBooks = resultSet.getInt(3);
			numberOfBorrowedBooks = resultSet.getInt(4);
			booksArray = resultSet.getArray(5);
			Object[] myBooks = (Object[]) booksArray.getArray();
			for (int i = 0; i < myBooks.length; i++) {
				Struct book = (Struct) myBooks[i];
				Object[] attributes = book.getAttributes();
				title = (String) attributes[0];
				publicationDate = (Timestamp) attributes[1];
			}
		}
		resultSet.close();
		statement.close();
	}

	public void execute(String query) throws SQLException {
		statement = MyConnection.getConnection().createStatement();
		statement.executeQuery(query);
		statement.close();
	}

	public void update(String query) throws SQLException {
		statement = MyConnection.getConnection().createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(bookID);
		builder.append(',');
		builder.append(title);
		builder.append(',');
		builder.append(publicationDate);
		builder.append(',');
		builder.append(numberOfBooks);
		builder.append(',');
		builder.append(numberOfAvailableBooks);
		builder.append(',');
		builder.append(numberOfBorrowedBooks);
		return builder.toString();
	}

}