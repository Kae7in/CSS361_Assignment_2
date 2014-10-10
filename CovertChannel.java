import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;



// if (data.length == 3){
// 	//read command
// 	iobj = new InstructionObject(data[0], data[1], data[2]);
// 	sys.getReferenceMonitor().executeRead(iobj);
// 	iobj.printData();
// 	printState(sys);
// }else{
// 	//write command
// 	iobj = new InstructionObject(data[0], data[1], data[2], Integer.parseInt(data[3]));
// 	sys.getReferenceMonitor().executeWrite(iobj);
// 	iobj.printData();
// 	printState(sys);
// }



public class CovertChannel {
	private ReferenceMonitor rm = new ReferenceMonitor();
	public static void main(String[] args) throws IOException {
		CovertChannel cc = new CovertChannel();

		// LOW and HIGH are constants defined in the SecurityLevel 
        	// class, such that HIGH dominates LOW.
		SecurityLevel low  = SecurityLevel.LOW;
		SecurityLevel high = SecurityLevel.HIGH;

		//get file name as a string to pass with subject creation
		String filename = "";
		if (args[0].equals("v"))
			filename = args[1];
		else
			filename = args[0];

		// We add two subjects, one high and one low.
		cc.createSubject("hal", high, filename);
		cc.createSubject("lyle", low, filename); // lyle deltes hal's .out file, order is important

		// We add two objects, one high and one low.
		// sys.getReferenceMonitor().createNewObject("LObj", low);
		// sys.getReferenceMonitor().createNewObject("HObj", high);

		System.out.printf("Reading from file: %s\n\n", filename);

		FileInputStream inputstream = new FileInputStream(new File(filename));
		int inputByte = 0;
		//print filename here! ***
		while (inputstream.available() > 0){
			inputByte = inputstream.read();
			// if (data.length > 0){
				for (int i = 7; i > -1; --i){

					int bit = (inputByte >> i) & 1;
					// System.out.println(bit);
					if (bit == 0) {
						// cc.getReferenceMonitor().createNewObject("Hal", "obj");
						cc.create("hal", "obj");
					}

					cc.create("lyle", "obj");
					cc.write("lyle", "obj", 1);
					cc.read("lyle", "obj");
					cc.destroy("obj");
					cc.run();


					//run Lyle stuffz
					//maybe make methods for these instructions?
				}
			// }creat
		}
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
		//success
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
