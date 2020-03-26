package lift;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class FileWrite {
	public static void tofile(String str){
		Charset charset = Charset.forName("US-ASCII");

		try  {
			FileOutputStream out=new FileOutputStream("result.txt",true);
			str=str+"\r\n";
		    out.write(str.getBytes(charset));
		    out.close();
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}

	}
}
