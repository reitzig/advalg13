package DataWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;


public class CSVWriter {

	//Delimiter used in CSV file
	private static final String COMMA_DELIMITER = "\t";
	private static final String NEW_LINE_SEPARATOR = "\n";
	
	//CSV file header
//	private static final String FILE_HEADER = "numberOfGraphs\tgraphSize\tedgeProbability\tVCDBS-Time (in ms)\tHow many VCs?\tParameter k";
//	private static final String FILE_HEADER = "numberOfGraphs\tgraphSize\tedgeProbability\tVCDBS-Time (in ms)\tVCDBSwithBounding-Time (in ms)\tHow many VCs?\tParameter k";
	private static final String FILE_HEADER = "numberOfGraphs\tgraphSize\tedgeProbability\tVCDBS-Time (in ms)\tVCDBS2-Time (in ms)\tVCDBSwithBounding-Time (in ms)\tHow many VCs?\tParameter k";

	public static void writeCsvFile(String fileName, LinkedList<String[]> data) {
					
		FileWriter fileWriter = null;
				
		try {
			fileWriter = new FileWriter(fileName + ".csv");

			//Write the CSV file header
			fileWriter.append(FILE_HEADER.toString());
			
			//Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			//Write object list to the CSV file
			for(String[] row: data){
				for(int i = 0; i < row.length; i++){
					fileWriter.append(row[i]);
					if(i < row.length - 1){
						fileWriter.append(COMMA_DELIMITER);
					}
				}
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			
		} catch (IOException e) {
			System.out.println("Error in IO");
			e.printStackTrace();
		} finally {
			
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter");
                e.printStackTrace();
			}
			
		}
	}
}
