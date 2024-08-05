package hashimotonet.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class TextFileIO {
	
	static Logger log = LogManager.getLogger(TextFileIO.class);

    static final String filePath = "/tmp/ChatGPT.data";

    public static void output2File(String message) {

        boolean autoFlush = true;

        // ファイルパス文字列から、Pathオブジェクト作成
        Path path = Paths.get(filePath);
        // Filesを使ってBufferedWriterの取得
        try (BufferedWriter bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
                PrintWriter pw = new PrintWriter(bw, autoFlush)) {
            pw.println(message);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String outputFromFile() {
    	StringBuilder sb = new StringBuilder();
    	Path path = Paths.get(filePath);
    	try {
    	    List<String> fileContents = Files.readAllLines(path, StandardCharsets.UTF_8);
    	    for (String line : fileContents) {
    	    	sb.append(line);
    	        log.debug(line);
    	    }
    	} catch (IOException ioe) {
    	    throw new RuntimeException(ioe);
    	}
    	return sb.toString();
    }
}
