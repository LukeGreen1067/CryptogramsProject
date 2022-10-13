import java.io.*;
import java.util.ArrayList;

public class ScannerWriter {
        private BufferedReader myReader;
        private BufferedWriter myWriter;
        private File readFile;
        private File writeFile;

        public ScannerWriter(String readFileaddress, String writeFileaddress)
        {
            readFile = new File(readFileaddress);
            writeFile = new File(writeFileaddress);
        }

        public ArrayList<String> readLines()
        {
            ArrayList<String> lines = new ArrayList<>();
            try {
                myReader = new BufferedReader(new FileReader(readFile));
                int i = 0;
                lines.add(myReader.readLine());
                while (lines.get(i) != null)
                {
                    lines.add(myReader.readLine());
                    i++;
                }
            }catch (Exception e)
            {
                System.out.println(e);
            }
            return lines;
        }

        public void writeFile(String content)
        {
            try {
                FileWriter fw = new FileWriter(writeFile.getAbsoluteFile());
                myWriter = new BufferedWriter(fw);
                myWriter.write(content);
                myWriter.close();
            }catch (Exception e)
            {
                System.out.println(e);
            }
        }
}
