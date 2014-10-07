import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;



public class SecureSystem {
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



class ReferenceMonitor {
	private LinkedHashMap<String, SecurityLevel> subjectToLevel = new LinkedHashMap<String, SecurityLevel>(); //subject_name -> SecurityLevel
	private LinkedHashMap<String, SecurityLevel> objectToLevel = new LinkedHashMap<String, SecurityLevel>(); //object_name -> SecurityLevel
	private LinkedHashMap<String, Subject> subjects = new LinkedHashMap<String, Subject>(); //subject_name -> Subject
	private ObjectManager objManager = new ObjectManager();	

	public void createSubject(String subject_name, SecurityLevel sl){
		Subject subj = new Subject(subject_name);
		this.subjects.put(subject_name.toLowerCase(), subj);
		this.subjectToLevel.put(subject_name.toLowerCase(), sl);
	}

	public void createNewObject(String object_name, SecurityLevel sl){
		//Object_ obj = new Object_(object_name, sl);
		this.objectToLevel.put(object_name.toLowerCase(), sl);
		this.objManager.createObject(object_name, sl);
	}

	//execute read
	public void executeRead(InstructionObject iobj){
		SecurityLevel subjectSL = subjectToLevel.get(iobj.getSubjectName().toLowerCase());
		SecurityLevel objectSL = objectToLevel.get(iobj.getObjectName().toLowerCase());
		
		if (subjectSL == null)
			return;
	
		if (objectSL != null && subjectSL.dominates(objectSL)) {	
			subjects.get(iobj.getSubjectName().toLowerCase()).updateValue(objManager.read(iobj.getObjectName().toLowerCase()));
		} else {
			subjects.get(iobj.getSubjectName().toLowerCase()).updateValue(0);
		}

	}

	//execute write
	public void executeWrite(InstructionObject iobj){
		SecurityLevel subjectSL = subjectToLevel.get(iobj.getSubjectName().toLowerCase());
                SecurityLevel objectSL = objectToLevel.get(iobj.getObjectName().toLowerCase());
		
		if (subjectSL == null)
			return;
		
		if (objectSL != null && objectSL.dominates(subjectSL))
			objManager.write(iobj.getObjectName().toLowerCase(), iobj.getValue());
	}

	public void printSubjectValues(){
		for (Map.Entry<String, Subject> entry : subjects.entrySet()){
			int value = entry.getValue().getValue();
			System.out.println("   " + entry.getValue().getName() + " has recently read: " + value);
		}
	}

	public void printObjectValues(){
		objManager.printValues();
	}

}



class ObjectManager {
	private LinkedHashMap<String, Object_> lm = new LinkedHashMap<String, Object_>(); //object_name -> Object_
	
	public void createObject(String object_name, SecurityLevel sl){
		lm.put(object_name.toLowerCase(), new Object_(object_name, sl));
	}

	public int read(String object_name){
		return lm.get(object_name).getValue();
	}

	public void write(String object_name, int value){
		lm.get(object_name).updateValue(value);
	}

	public void printValues(){
		for (Map.Entry<String, Object_> entry : lm.entrySet()){
			int value = entry.getValue().getValue();
                        System.out.println("   " + entry.getValue().getName() + " has value: " + entry.getValue().getValue());
                }
	}
}



class Subject {
	private String name;
	private int value = 0;

	public Subject(String name){
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void updateValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}
}



class Object_ {
	private String name;
	private int value = 0;
	SecurityLevel level;

	public Object_(String name, SecurityLevel level){
		this.name = name;
		this.level = level;
	}

	public String getName(){
		return this.name;
	}

	public int getValue(){
		return this.value;
	}

	public SecurityLevel getSecurityLevel(){
		return this.level;
	}

	public void updateValue(int value){
		this.value = value;
		//System.out.println("VALUE: " + this.value);
	}
}



class InstructionObject {
	private String readwrite; //0 for read, 1 for write
	private String subject_name;
	private String object_name;
	private int value = 0;

	//constructor for read object
	public InstructionObject(String readwrite, String subject_name, String object_name){
                this.readwrite = readwrite;
                this.subject_name = subject_name;
                this.object_name = object_name;
        } 

	//contructor for write object
	public InstructionObject(String readwrite, String subject_name, String object_name, int value){
		this.readwrite = readwrite;
		this.subject_name = subject_name;
		this.object_name = object_name;
		this.value = value;
	}

	public String getReadWrite(){
		return this.readwrite;
	}

	public String getSubjectName(){
		return this.subject_name;
	}

	public String getObjectName(){
		return this.object_name;
	}

	public int getValue(){
		return this.value;
	}

	public void updateValue(int value){
		this.value = value;
	}

	public void printData(){
		if (readwrite.equals("read"))
			System.out.println(subject_name.toLowerCase() + " reads " + object_name.toLowerCase());
                else
                        System.out.println(subject_name.toLowerCase() + " writes value " + value + " to " + object_name.toLowerCase());
	}
}



class SecurityLevel {
	public static final SecurityLevel LOW = new SecurityLevel(0);
	public static final SecurityLevel HIGH = new SecurityLevel(1);
	private int level;	

	private SecurityLevel(int level){
		this.level = level;
	}

	public boolean dominates(SecurityLevel sl){
		return (this.level >= sl.level);
	}

	public void printLevel(){
		System.out.println(this.level);
	}
}
