package writer;

import java.io.IOException;

public class LetsWrite {

	public static void main(String[] args){
		try {
			String filePath = "C:\\Users\\IoanaAlexandra\\Desktop\\Library\\Database Related\\Scripts + textFiles\\GoodCsv2.csv";
			Writer writer = new Writer(new MyFileWriter(filePath),filePath);
			writer.writeCSV();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
