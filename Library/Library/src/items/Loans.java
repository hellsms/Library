package items;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.sql.Statement;

import logics.MyConnection;

public class Loans implements Item {
	/***************************************************************************
	 *** declaration of the private variables used in the program ***
	 ***************************************************************************/

	private Statement statement = null;
	private ResultSet resultSet = null;

	private int loanID;
	private int bookID;
	private int memberID;
	private Timestamp dayOfBorrowed;
	private Timestamp dayOfReturn;
	private String otherDetails;

	public Loans() {
	}

	public int getLoanID() {
		return loanID;
	}

	public void setLoanID(int loanID) {
		this.loanID = loanID;
	}

	public int getBookID() {
		return bookID;
	}

	public void setBookID(int bookID) {
		this.bookID = bookID;
	}

	public int getMemberID() {
		return memberID;
	}

	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}

	public Timestamp getDayOfBorrowed() {
		return dayOfBorrowed;
	}

	public void setDayOfBorrowed(Timestamp dayOfBorrowed) {
		this.dayOfBorrowed = dayOfBorrowed;
	}

	public Timestamp getDayOfReturn() {
		return dayOfReturn;
	}

	public void setDayOfReturn(Timestamp dayOfReturn) {
		this.dayOfReturn = dayOfReturn;
	}

	public String getOtherDetails() {
		return otherDetails;
	}

	public void setOtherDetails(String otherDetails) {
		this.otherDetails = otherDetails;
	}

	public void select(String query) throws SQLException{
			statement = MyConnection.getConnection().createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				System.out.println("resultSet");
				loanID = resultSet.getInt(1);
				bookID = resultSet.getInt(2);
				memberID = resultSet.getInt(3);
				dayOfBorrowed = resultSet.getTimestamp(4);
				dayOfReturn = resultSet.getTimestamp(5);
				otherDetails = resultSet.getString(6);
			}
			resultSet.close();
			statement.close();
	}

	public void execute(String query) throws SQLException{
			statement = MyConnection.getConnection().createStatement();
			statement.executeQuery(query);
			statement.close();
	}

	public void update(String query) throws SQLException{
			statement = MyConnection.getConnection().createStatement();
			statement.executeUpdate(query);
			statement.close();
	}
}