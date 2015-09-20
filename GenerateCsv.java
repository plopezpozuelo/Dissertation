/**
* <dl>
* <dt> File Name:
* <dd> GenerateCsv.java
*
* <dt> Description:
* <dd> This file consists on a genetic algorithm that can be used 
* to solve optimization problems.
* </dl>
*
* @author Paula Lopez Pozuelo
*/

import java.io.FileWriter;
 
public class GenerateCsv {
	
	public static void generateCsvFile(String sFileName, String content)
	{
		try {
			FileWriter writer = new FileWriter(sFileName);
			writer.append(content);
			writer.flush();
			writer.close();
		} catch(Exception e) {} 
	}
	
}
