import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import weka.core.Instances;
import weka.classifiers.trees.RandomForest;

public class Weka {
	
	public Weka(String featureData) {
		this.featureData = featureData;
		
		try {
			start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	String featureData;
	String WekaInputName = "unlabeled.arff";

	public void start() throws IOException, FileNotFoundException, ClassNotFoundException, UnsupportedEncodingException, InterruptedException, Exception {
        RandomForest rf = buildRandomForestModel();
        
        //I can't figure out how to use a string instead of a file, you might see something I don't. This is what the string would look like though with the data at the end
        //String featureDataEx = "40.0,7.0,5.7142857142857135,2671.0,2363.0,1021.2647058823527,1337.0,7.05,0.6764705882352942,0.0,0.29411764705882354,0.0,0.0,0.0,0.029411764705882356,";
        
        //String
        
        //writes input .arff input file to path WekaInputName
        writeInputFile();
        
        //import the data to predict, this can include multiple sets of data
        Instances unlabeled = new Instances(new BufferedReader(new FileReader(WekaInputName)));  
        
        //figure out which index we're trying to predict (the last one for us)
        unlabeled.setClassIndex(unlabeled.numAttributes() - 1);
        Instances labeled = new Instances(unlabeled);
        
        //go through each set of data in the file (currently just one) and predict it
        double prediction = 0.0;
        for (int i = 0; i < unlabeled.numInstances(); i++) {
        	prediction = rf.classifyInstance(unlabeled.instance(i));
        	labeled.instance(i).setClassValue(prediction);
        	System.out.println(labeled.classAttribute().value((int) prediction));
        }
	}
	
	private RandomForest buildRandomForestModel()  throws IOException, FileNotFoundException, ClassNotFoundException, UnsupportedEncodingException, InterruptedException, Exception {
		//import the training data
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream input = classLoader.getResourceAsStream("trainingData.arff");

		BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF8"));
		
        Instances trainData = new Instances(reader);
        trainData.setClassIndex(trainData.numAttributes() - 1);
        reader.close();
        
        //train a Random Forest model on the data and return it
		RandomForest rf = new RandomForest();
        rf.setNumTrees(100);
        rf.buildClassifier(trainData);
		return rf;
	}
	
	private void writeInputFile() throws IOException {
		String wekaTop = readFile("weka_input_top.txt");
		File fWekaInput = new File(WekaInputName);
		Writer writer = new BufferedWriter(new OutputStreamWriter
				(new FileOutputStream(fWekaInput)));
		writer.write(wekaTop + featureData);
		writer.close();
	}	
	
	private String readFile(String fileName) throws IOException {
		StringBuilder sb = new StringBuilder();
		
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream input = classLoader.getResourceAsStream(fileName);

		BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF8"));
    
		String str;
		while((str = reader.readLine()) != null) {
			sb.append(str + "\n");
		}
	
		reader.close();
		return sb.toString();
	}
}