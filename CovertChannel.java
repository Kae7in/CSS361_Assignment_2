import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;


public class CovertChannel {
	private ReferenceMonitor rm = new ReferenceMonitor();
	public static void main(String[] args) throws IOException {
		SecureSystem sys = new SecureSystem();

		// LOW and HIGH are constants defined in the SecurityLevel 
        	// class, such that HIGH dominates LOW.
		SecurityLevel low  = SecurityLevel.LOW;
		SecurityLevel high = SecurityLevel.HIGH;

		// We add two subjects, one high and one low.
		sys.createSubject("Lyle", low);
		sys.createSubject("Hal", high);

		// We add two objects, one high and one low.
		sys.getReferenceMonitor().createNewObject("LObj", low);
		sys.getReferenceMonitor().createNewObject("HObj", high);

		System.out.printf("Reading from file: %s\n\n", args[0]);

		Scanner sc = new Scanner(new File(args[0]));
		String line = "";
		//print filename here! ***
		InstructionObject iobj;
		while (sc.hasNextLine()){
			line = sc.nextLine();
			String[] data = validateCommand(line);
			if (data.length > 0){
				if (data.length == 3){
					//read command
					iobj = new InstructionObject(data[0], data[1], data[2]);
					sys.getReferenceMonitor().executeRead(iobj);
					iobj.printData();
					printState(sys);
				}else{
					//write command
					iobj = new InstructionObject(data[0], data[1], data[2], Integer.parseInt(data[3]));
					sys.getReferenceMonitor().executeWrite(iobj);
					iobj.printData();
					printState(sys);
				}
			}else{
				//print bad instructions
				System.out.println("Bad Instruction");
				printState(sys);
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

	public static void printState(SecureSystem sys){
		System.out.println("The current state is:");
		sys.getReferenceMonitor().printObjectValues();
		sys.getReferenceMonitor().printSubjectValues();
		System.out.println();
	}
}