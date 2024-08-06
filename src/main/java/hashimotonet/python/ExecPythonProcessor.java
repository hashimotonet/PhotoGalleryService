/**
 *
 */
package hashimotonet.python;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

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
    public static synchronized String imageAnalysisProcess(String url) {

        final String SEP = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder();

        try {
            // ProcessBuilderを使用して環境変数を設定
            ProcessBuilder processBuilder = new ProcessBuilder("python3.9", "/opt/PhotoGallery/python3.9.18/image_analysis.py", url);
            Map<String, String> env = processBuilder.environment();
            // OPENAI_API_KEY は秘匿情報のため、push 時に削除
            env.put("キー名", "キー値");

            // プロセスを開始
            Process process = processBuilder.start();
            log.info("GPT process started.");

            // プロセスの出力を読み取る
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append(SEP);
                log.info(line);
            }

            // プロセスの終了を待つ
            process.waitFor();
        } catch (Exception e) {
            log.catching(e);
        }

        return sb.toString();

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
