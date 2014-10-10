import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

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
		//System.out.println("At read in object: " + value);
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