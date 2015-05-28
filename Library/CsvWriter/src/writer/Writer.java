package writer;

import java.io.IOException;
import java.util.Random;

public class Writer {

	MyFileWriter writer;
	String filePath;

	public Writer() {
		super();
	}

	public Writer(MyFileWriter writer, String filePath) {
		super();
		this.writer = writer;
		this.filePath = filePath;
	}

	public void initialize() throws IOException {
		writer = new MyFileWriter(filePath);
	}

	public void createCsv() throws IOException {
		// Loans
/*		writer.append("Loan_ID");
		writer.append(',');
		writer.append("Member_ID");
		writer.append(',');
		writer.append("Book_ID");
		writer.append(',');
		writer.append("Request_Date");
		writer.append(',');
		writer.append("Return_Date");
		writer.append(',');
		writer.append("Other_Details");
		writer.append('\n');*/

		// Null other details
		String loanDates [] = {"01-JAN-2015", "23-FEB-2015", "11-MAY-2015","30-JUN-2015","07-JUL-2015","03-MAR-2015","28-SEP-2015","15-AUG-2015"};
		String returnDates [] = {"01-JAN-2016", "23-FEB-2016", "11-MAY-2016","30-JUN-2016","07-JUL-2016","03-MAR-2016","28-SEP-2016","15-AUG-2016"};
		int book = 1;
		int member = 1;
		for (int i = 1; i < 500001; i++) {
			if(book == 1000){
				book = 1;
			}
			if(member == 5000){
				member = 1;
			}
			String one = String.valueOf(i);
			String two = String.valueOf(member);
			String three = String.valueOf(book);
			String four = loanDates[new Random().nextInt(7-0+1)+0];
			String five = returnDates[new Random().nextInt(7-0+1)+0];
			String six = "DETAILS";
			writer.append(one);
			writer.append(',');
			writer.append(two);
			writer.append(',');
			writer.append(three);
			writer.append(',');
			writer.append(four);
			writer.append(',');
			writer.append(five);
			writer.append(',');
			writer.append(six);
			writer.append('\n');
			book++;
			member++;
			
		}
		// Not null other details
	/*	for (int j = 100; j < 200; j++) {
			String uno = String.valueOf(j);
			String due = String.valueOf(j);
			String tre = String.valueOf(j);
			String quatro = "25-MAY-2015";
			String cinque = "01-JUNE-2015";
			String sei = "other";
			writer.append(uno);
			writer.append(',');
			writer.append(due);
			writer.append(',');
			writer.append(tre);
			writer.append(',');
			writer.append(quatro);
			writer.append(',');
			writer.append(cinque);
			writer.append(',');
			writer.append(sei);
			writer.append('\n');
		}*/

		writer.flush();
		writer.close();
	}

	public void writeCSV() {
		try {
			initialize();
			createCsv();
		} catch (IOException e) {
			System.err.println("Ups there has been an error");
			e.printStackTrace();
		}

	}

}
