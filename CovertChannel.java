import java.util.Scanner;
import java.io.File;
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
		SecureSystem cc = new CovertChannel();

		// LOW and HIGH are constants defined in the SecurityLevel 
        	// class, such that HIGH dominates LOW.
		SecurityLevel low  = SecurityLevel.LOW;
		SecurityLevel high = SecurityLevel.HIGH;

		// We add two subjects, one high and one low.
		cc.createSubject("Lyle", low);
		cc.createSubject("Hal", high);

		// We add two objects, one high and one low.
		// sys.getReferenceMonitor().createNewObject("LObj", low);
		// sys.getReferenceMonitor().createNewObject("HObj", high);

		System.out.printf("Reading from file: %s\n\n", args[0]);

		FileInputStream inputstream = new FileInputStream(new File(args[0]));
		int inputByte = 0;
		//print filename here! ***
		while (inputstream.available() > 0){
			inputByte = inputstream.read();
			if (data.length > 0){
				for (int i = 7; i > -1; --i){
					int bit = (inputByte >> i) & 1;
					if (bit == 0)
						sys.getReferenceMonitor().createNewObject("Hal", "obj");

					//run Lyle stuffz
					//maybe make methods for these instructions?
				}
			}
		}
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


	public void createSubject(String subject_name, SecurityLevel sl){
		Subject subj = new Subject(subject_name);
		rm.createSubject(subject_name, sl);
	}

	public ReferenceMonitor getReferenceMonitor(){
		return this.rm;
	}

	public static void printState(SecureSystem cc){
		System.out.println("The current state is:");
		cc.getReferenceMonitor().printObjectValues();
		cc.getReferenceMonitor().printSubjectValues();
		System.out.println();
	}
}