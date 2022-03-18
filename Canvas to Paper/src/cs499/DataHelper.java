package cs499;

import io.github.cdimascio.dotenv.Dotenv;

public class DataHelper {
	
	public static Dotenv ENV = Dotenv.configure().ignoreIfMissing().ignoreIfMalformed().load();

}
