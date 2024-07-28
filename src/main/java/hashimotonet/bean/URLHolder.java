/**
 *
 */
package hashimotonet.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Osamu Hashimoto
 *
 */
@Getter
@Setter
public class URLHolder {
	
	private String id;

    /**
     * 画像のURL
     */
	private String url;

    /**
     * ユーザによる撮影時の入力テキスト
     */
	private String alt;
    
	/**
	 * URL画像に対するChat-GPTからのメッセージ
	 */
    private String chat;

	/**
	 * 画像サムネイル
	 * （現在未使用）
	 */
	@Deprecated
    private String thumbnail;
    
    /**
     * デフォルトコンストラクタ
     */
    public URLHolder() {
        super();
    }

}
