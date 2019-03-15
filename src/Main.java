import java.io.File;
import java.io.Writer;
import java.net.URL;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileNotFoundException;

public class Main {

	public static void main (String[] args) {
		String webText = args[0];
		//run the line below in your terminal to run the server
		//java -Xmx2500m -Xms2500m -XX:NewRatio=3 -jar MADAMIRA-release-20170403-2.1.jar -s -msaonly
		
		//this is for testing, there is a test.txt file included in which you can put arabic text and then change the argument for start to webText
		//String fileName = "C:\\Users\\maste\\eclipse-workspace\\RAFT\\test.txt";
		//String webText = Processor.readFile(fileName);
		//webText = Processor.readFile(fileName);
		//int score = Start(webText);
		Start(webText);
		//return score;
	}

	public static int Start(String webText) {
		//*
		Processor processor = new Processor(webText);
		String featureData = processor.getResult();
		Weka weka = new Weka(featureData);
		return weka.score;
		//*/	
		//return weka;
	}

}
