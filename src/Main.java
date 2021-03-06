import java.io.File;
import java.io.Writer;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;

public class Main {

	public static void main (String[] args) throws IOException, FileNotFoundException, ClassNotFoundException, UnsupportedEncodingException, InterruptedException, Exception {
		String webText = args[0];
		//run the line below in your terminal to run the server
		//java -Xmx2500m -Xms2500m -XX:NewRatio=3 -jar MADAMIRA-release-20170403-2.1.jar -s -msaonly
		
		//this is for testing, there is a test.txt file included in which you can put arabic text and then change the argument for start to webText
		//String fileName = "C:\\Users\\maste\\eclipse-workspace\\RAFT\\test.txt";
		//String webText = Processor.ReadFile(fileName);
		//System.out.println(webText);
		
		int score = ScoreText(webText);
		System.out.println(score);
		//BuildModel();
		//TestModel();
		//return score;
	}

	public static int ScoreText(String webText) throws IOException, FileNotFoundException, ClassNotFoundException, UnsupportedEncodingException, InterruptedException, Exception {
		Processor processor = new Processor(webText);
		String featureData = processor.getResult() + "1.0";
		Weka weka = new Weka("model.arff");
		return weka.ScoreFeatures(featureData);
	}
	
	public static void TestModel( ) {
		Weka weka = new Weka("model.arff");
		weka.TestModel();
	}
	
	public static void BuildModel() throws IOException, FileNotFoundException, ClassNotFoundException, UnsupportedEncodingException, InterruptedException, Exception {
		String corpusDir = "C:\\Users\\maste\\Desktop\\RAFT\\arabicScraperOutput";
		String modelDir = "C:\\Users\\maste\\Desktop\\RAFT";
		File dir = new File(corpusDir);
		File[] directoryListing = dir.listFiles();
		String corpusItemText = "";
		String difficulty = "";
		String body = "";
		String features = "";
		String trainingDataString = "";
		String writeLine = "";
		trainingDataString += (Weka.GetArffHeader());
		Processor processor;
		int count = 0;
		if (directoryListing != null) {
			for (File child : directoryListing) {
				features = "";
				difficulty = "";
				body = "";
				corpusItemText = Processor.ReadFileContents(child);
				difficulty = Processor.GetTagContents(corpusItemText, "DIFFICULTY");
				body = Processor.GetTagContents(corpusItemText, "BODY");
				if (body != "" && difficulty != "" && difficulty !="?") {
					difficulty = difficulty.replaceAll("/+", "");
					processor = new Processor(body);
					features = processor.getResult();
					if (features.length() > 0) {
	//					System.out.println(difficulty);
	//					System.out.println(body);
						System.out.println(child.getName());
						writeLine = features + difficulty + ".0";
						System.out.println(writeLine);
						trainingDataString += (writeLine + "\r\n");
					}
				}
				count++;
				// Do something with child
				if (count > 0) {
					//break;
				}
			}
		}
		System.out.println(trainingDataString);
		String modelFileName = modelDir + "\\model.arff";
		File modelFile = new File(modelFileName);
		try {
			Writer writer = new BufferedWriter(new OutputStreamWriter
					(new FileOutputStream(modelFile), "UTF8"));
			writer.write(trainingDataString);
			writer.close();
		}
		catch(UnsupportedEncodingException e) {
			System.out.println("UNSUPPORTED ENCODING");
			e.printStackTrace();
		}
		catch(IOException e) {
			System.out.println("COULD NOT WRITE TO FILE ");
			e.printStackTrace();
		}
	}

}
