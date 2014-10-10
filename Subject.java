import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.io.FileOutputStream;

class Subject {
	private String name;
	private int readValue = 0;
	private int covertValue = 0;
	private int bitsReceived = 0;
	private File covertFile;
	private FileOutputStream outputstream;

	public Subject(String name, String filename) throws IOException {
		this.name = name;
		covertFile = new File(filename + ".out");
		if(covertFile.exists()) {
			covertFile.delete();
			covertFile = new File(filename + ".out");
		}
    	// covertFile.createNewFile(); 
		outputstream = new FileOutputStream(covertFile, true);
	}

	public String getName() {
		return this.name;
	}

	public void updateValue(int readValue) {
		//System.out.println("Subject.updateValue(" + readValue + ")");
		this.readValue = readValue;
	}

	public int getValue() {
		return this.readValue;
	}

	public void run() throws IOException {
		// System.out.println(readValue);
		covertValue <<= 1;
		covertValue = (covertValue | readValue);
		bitsReceived++;

		if (bitsReceived == 8){
			System.out.print(covertValue);
			outputstream.write(covertValue);
			covertValue = 0;
			bitsReceived = 0;
		}
			//write out
			// maybe a final file object that's visible to all subjects
			// stored here in the subject class
			// write to it from here. No one else needs to use the .out file
			// the .out file is fileNameGiven.out so we need to see that somehow
			// so maybe we need to pass the file name aaaallll the way to here
	}
}