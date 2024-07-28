/**
 * 
 */
package hashimotonet.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hashimotonet.python.ExecPythonProcessor;

/**
 * Ajaxリクエストを参照し、OpenAI接続を行うPythonモジュールを呼び出す。
 * その結果である戻り値をクライアントに出力するクラス。
 */
@WebServlet("/ChatGptServlet")
public class ChatGptServlet extends HttpServlet {
	
	Logger log = LogManager.getLogger(getClass());
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		
		log.info("ChatGPT - doPost()");
		
		// Daoをコールし、Photo表を検索
		String message = "";
		
		String url = request.getParameter("url");
		try {
			PrintWriter out = response.getWriter();
			message = ExecPythonProcessor.imageAnalysis(url);
			out.println(message);
			out.close();
		} catch (IOException e) {
			log.catching(e);
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		log.info("ChatGPT - doGet()");
	}

}
