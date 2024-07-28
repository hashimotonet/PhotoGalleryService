/**
 * 
 */
package hashimotonet.python;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ChatGPTへ画像解析の問い合わせを行うクラス
 */
public class ExecPythonProcessor {
	
	static Logger log = LogManager.getLogger(ExecPythonProcessor.class);
	
	/**
	 * ChatGPT問い合わせメソッド。
	 * 
	 * @param url 解析対象画像URL
	 * @return
	 * @throws IOException
	 */
	public static synchronized String imageAnalysis(String url) throws IOException {
		String message = "";
		String command[] = new String[3];
				command[0] = "python3.9";
		        command[1] = "/opt/PhotoGallery/python/image_analysis.py";
		        command[2] = url;
		log.info(String.join(" ",command));
		Process process = Runtime.getRuntime().exec(command);
		message = readFromProcess(process);
		log.info(message);
		return message;
	}
	
	/**
	 * CharGPTからの応答メッセージを取得する。
	 * 
	 * @param p
	 * @return
	 * @throws IOException
	 */
	private static String readFromProcess(Process p) throws IOException {
	    String message = "";
		InputStream in = null;
	    BufferedReader br = null;
		final String SEP = System.getProperty("line.separator");
	    try {
	        in = p.getInputStream();
	        StringBuffer out = new StringBuffer();
	        br = new BufferedReader(new InputStreamReader(in));
	        String line;
	        while ((line = br.readLine()) != null) {
	            out.append(line + SEP);
	        }
	        message = out.toString();
	        br.close();
	        in.close();
	        return message;
	    } finally {
	        if (br != null) {
	            br.close();
	        }
	        if (in != null) {
	            in.close();
	        }
	    }
	 }
}
