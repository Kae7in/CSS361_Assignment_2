import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

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