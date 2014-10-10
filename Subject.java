import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

class Subject {
	private String name;
	private int value = 0;
	private int covertValue = 0;

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

	public void run() {
		
	}
}