
public class Main {

	public static void main (String[] args) {
		String webText = "C:\\Users\\maste\\eclipse-workspace\\RAFT\\test.txt";
		Start(webText);
	}

	public static void Start(String webText) {
		Processor processor = new Processor(webText);
		System.out.println(webText);
		String featureData = processor.getResult();
		Weka weka = new Weka(featureData);
	}

}
