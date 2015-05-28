package items;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Struct;

import logics.MyConnection;

public class Members implements Item {
	/***************************************************************************
	 *** declaration of the private variables used in the program ***
	 ***************************************************************************/

	private Statement statement = null;
	private ResultSet resultSet = null;

	private int memberID;
	private String address;
	private String firstName;
	private String lastName;
	private String email;
	private BigDecimal phoneNo;
	private String otherDetails;
	private Array membersArray;

	public Members() {
	}

	public int getMemberID() {
		return memberID;
	}

	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(BigDecimal phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getOtherDetails() {
		return otherDetails;
	}

	public void setOtherDetails(String otherDetails) {
		this.otherDetails = otherDetails;
	}

	public Array getMembersArray() {
		return membersArray;
	}

	public void setMembersArray(Array membersArray) {
		this.membersArray = membersArray;
	}

	public void select(String query) throws SQLException {
			statement = MyConnection.getConnection().createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				memberID = resultSet.getInt(1);
				membersArray = resultSet.getArray(2);
				Object[] myMembers = (Object[]) membersArray.getArray();
				for (int i = 0; i < myMembers.length; i++) {
					Struct book = (Struct) myMembers[i];
					Object[] attributes = book.getAttributes();
					address = (String) attributes[0];
					firstName = (String) attributes[1];
					lastName = (String) attributes[2];
					phoneNo = (BigDecimal) attributes[3];
					email = (String) attributes[4];
					otherDetails = (String) attributes[5];
				}
			}
			resultSet.close();
			statement.close();
	}

	public void execute(String query) throws SQLException{
			statement = MyConnection.getConnection().createStatement();
			statement.execute(query);
			statement.close();
	}

	public void update(String query) throws SQLException{
			statement = MyConnection.getConnection().createStatement();
			statement.executeUpdate(query);
			statement.close();
	}

}