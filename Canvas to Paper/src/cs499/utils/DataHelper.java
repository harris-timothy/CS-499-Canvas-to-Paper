package cs499.utils;

import io.github.cdimascio.dotenv.Dotenv;

public class DataHelper {
	
	public static Dotenv ENV = Dotenv.configure().ignoreIfMissing().ignoreIfMalformed().load();
	
	public static int boolToInt(boolean b) {
		return b ? 1 : 0;
	}
	
	public static boolean intToBool(int i) {
		return i == 1;
	}
	
	public static String numToString(float num) {
		if (num % 1.0 != 0)
		    return String.format("%s", num);
		else
		    return String.format("%.0f", num);
	}
}
