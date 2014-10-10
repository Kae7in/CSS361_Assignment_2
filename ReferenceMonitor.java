import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

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

	public void createNewObject(String subject_name, String object_name){
		SecurityLevel sl = subjectToLevel.get(subject_name);
		this.objectToLevel.put(object_name.toLowerCase(), sl);
		this.objManager.createObject(object_name, sl);
	}

	//execute read
	public void executeRead(String subjName, String objName){
		SecurityLevel subjectSL = subjectToLevel.get(subjName.toLowerCase());
		SecurityLevel objectSL = objectToLevel.get(objName.toLowerCase());
		
		if (subjectSL == null)
			return;
	
		if (objectSL != null && subjectSL.dominates(objectSL)) {	
			subjects.get(subjName.toLowerCase()).updateValue(objManager.read(objName.toLowerCase()));
		} else {
			subjects.get(subjName.toLowerCase()).updateValue(0);
		}

	}

	//execute write
	public void executeWrite(String subjName, String objName, int value){
		SecurityLevel subjectSL = subjectToLevel.get(subjName.toLowerCase());
        SecurityLevel objectSL = objectToLevel.get(objName.toLowerCase());
		
		if (subjectSL == null)
			return;
		
		if (objectSL != null && objectSL.dominates(subjectSL))
			objManager.write(objName.toLowerCase(), value);
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