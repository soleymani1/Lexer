import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class Files {
	private List<String> lines;
	public List<String> fileToList(String fileName) throws IOException{
		FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        return lines;
	}
	public void makeFileFromList(List<Boolean> out) throws UnsupportedEncodingException, FileNotFoundException, IOException{
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream("output.txt"), "utf-8"))) {
			for (int i=0;i<out.size();i++){
				if (out.get(i)){
					writer.write("yes");
					writer.newLine();
				}else {
					writer.write("no");
					writer.newLine();
				}
			}
		}
	}
}
