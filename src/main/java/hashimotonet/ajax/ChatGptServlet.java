/**
 * 
 */
package hashimotonet.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hashimotonet.domain.dao.PhotoDao;
import hashimotonet.python.ExecPythonProcessor;

/**
 * Ajaxリクエストを参照し、OpenAI接続を行うPythonモジュールを呼び出す。
 * その結果である戻り値をクライアントに出力するクラス。
 */
@WebServlet("/ChatGptServlet")
public class ChatGptServlet extends HttpServlet {
	
	Logger log = LogManager.getLogger(getClass());
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		log.info("ChatGPT - doPost()");
		
		String message = "";

		// IDを取得する。
		String url = request.getParameter("url");
		int start = url.lastIndexOf("/") + 1;
		int end = url.lastIndexOf(".") - 1;
		String id = url.substring(start, end);
		
		boolean chat = false;
		
		// Daoをコールし、Photo表を検索
		try {
			chat = chatExisits(id);
		} catch (ClassNotFoundException | IOException | URISyntaxException | SQLException e) {
			log.catching(e);
		}
		
		PrintWriter out = response.getWriter();

		if (chat == false) {
			// GPT接続履歴がなければ接続を行い、メッセージ取得
			try {
				
				// GPT接続実行
				message = ExecPythonProcessor.imageAnalysis(url);
				
				// ChatGPTからの画像評価メッセージをクライアントへ出力
				out.println(message);
				
				// メッセージをPhoto表にストアする
				createChatMessage(message, id);
				
			} catch (IOException | ClassNotFoundException | SQLException | URISyntaxException e) {
				log.catching(e);
			}
		} else {
		
			// ChatGPT接続履歴が存在する場合、Photo表より取得を行い、メッセージをクライアントへ出力する。
			try {
				message = getChatMessageHistory(id);
			} catch (ClassNotFoundException | SQLException | IOException | URISyntaxException e) {
				log.catching(e);
			}
			
			out.println(message);
		}
		
		out.close();
		
		return;

	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		log.info("ChatGPT - doGet()");
	}
	
	/**
	 * GPT-Chatの応答メッセージをPhoto表にストアする
	 * 
	 * @param message
	 * @param id
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private boolean createChatMessage(String message, String id) throws ClassNotFoundException, SQLException, IOException, URISyntaxException {
		boolean result = false;
		
		new PhotoDao().updateChatMessage(message, id);
		
		return result;
	}
	
	/**
	 * Photo表を検索し、ChatGPT接続履歴の存在を確認する。
	 * 
	 * @param id
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws SQLException
	 */
	private boolean chatExisits(String id) throws ClassNotFoundException, IOException, URISyntaxException, SQLException {
		boolean result = false;
		PhotoDao photoDao = new PhotoDao();
		result = photoDao.chatExists(id);
		return result;
	}
	
	private String getChatMessageHistory(String id) throws ClassNotFoundException, SQLException, IOException, URISyntaxException {
		String message = "";
		message = new PhotoDao().getChatMessage(id);
		return message;
	}

}
