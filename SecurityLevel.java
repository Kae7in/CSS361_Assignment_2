import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

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
