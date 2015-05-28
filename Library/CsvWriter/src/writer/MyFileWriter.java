package writer;

import java.io.FileWriter;
import java.io.IOException;

public class MyFileWriter extends FileWriter{

	public MyFileWriter(String filePath) throws IOException {
		super(filePath);
	}

}
