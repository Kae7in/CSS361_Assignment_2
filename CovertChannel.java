import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;



public class CovertChannel {
	private ReferenceMonitor rm = new ReferenceMonitor();
	public static void main(String[] args) throws IOException {
		CovertChannel cc = new CovertChannel();
		boolean verbose = false; //used to determine whether we will create a log file

		// LOW and HIGH are constants defined in the SecurityLevel 
        // class, such that HIGH dominates LOW.
		SecurityLevel low  = SecurityLevel.LOW;
		SecurityLevel high = SecurityLevel.HIGH;

		//get file name as a string to pass with subject creation
		String filename = "";
		if (args[0].equals("v")){
			filename = args[1];
			verbose = true;
		}else{
			filename = args[0];
		}

		// We add two subjects, one high and one low.
		cc.createSubject("hal", high, filename);
		cc.createSubject("lyle", low, filename); // lyle deletes hal's .out file, order is important



		FileInputStream inputstream = new FileInputStream(new File(filename));
		File log = null;
		FileWriter writer = null;
		if (verbose){
			log = new File("log");
			if (log.exists()){
				log.delete();
				log = new File("log");
			}
			writer = new FileWriter(log);
		}

		int inputByte = 0;
		int totalBits = 0;

		// long startTime = System.nanoTime();
		while (inputstream.available() > 0){
			inputByte = inputstream.read();
				for (int i = 7; i > -1; --i){

					int bit = (inputByte >> i) & 1;
					if (bit == 0) {
						cc.create("hal", "obj");

						//write out to log
						if (verbose)
							writer.write("CREATE HAL OBJ\n");
					}

					cc.create("lyle", "obj");
					cc.write("lyle", "obj", 1);
					cc.read("lyle", "obj");
					cc.destroy("obj"); //should pass subject to do a check
					cc.run();

					//write out to log
					if (verbose){
						writer.write("CREATE LYLE OBJ\n");
						writer.write("WRITE LYLE OBJ 1\n");
						writer.write("READ LYLE OBJ\n");
						writer.write("DESTROY LYLE OBJ\n");
						writer.write("RUN LYLE\n\n");
					}
					++totalBits;
				}
		}
		// long estimatedTime = System.nanoTime() - startTime;
		// System.out.println("Estimated Average Bandwidth (bits/ms): " + (totalBits / (estimatedTime / 1000000)));
		if (verbose)
			writer.close();
	}

	public void run() throws IOException {
		getReferenceMonitor().executeRun();
	}

	public void destroy(String objName) {
		getReferenceMonitor().destroy(objName);
	}

	public void create(String subjName, String objName) {
		getReferenceMonitor().createNewObject(subjName, objName);
	}

	public void read(String subject_name, String object_name){
		getReferenceMonitor().executeRead(subject_name, object_name);
	}

	public void write(String subject_name, String object_name, int value){
		getReferenceMonitor().executeWrite(subject_name, object_name, value);
	}

	public static String[] validateCommand(String line){
		String[] data = line.split(" ");
		for (int i = 0; i < data.length; i++){	
			if (!data[i].matches("[A-Za-z0-9]+"))
				return (new String[]{});
		}
		
		if (data.length < 3 || data.length > 4 || (!data[0].equals("read") && !data[0].equals("write")) || (data[0].equals("write") && data.length < 4))
			return (new String[]{});
	
		return data;
	}


	public void createSubject(String subject_name, SecurityLevel sl, String filename) throws IOException {
		rm.createSubject(subject_name, sl, filename);
	}

	public ReferenceMonitor getReferenceMonitor(){
		return this.rm;
	}

	public static void printState(CovertChannel cc){
		System.out.println("The current state is:");
		cc.getReferenceMonitor().printObjectValues();
		cc.getReferenceMonitor().printSubjectValues();
		System.out.println();
	}
}
