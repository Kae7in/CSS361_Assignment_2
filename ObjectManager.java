import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

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

	public void removeObject(String objName) {
		lm.remove(objName);
	}
}