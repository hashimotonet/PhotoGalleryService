package hashimotonet.controller.base;

public interface ControllerBase {

	/**
     * メールアドレス
     */
    public static final String ADDRESS = "mailAddress";

    /**
     * アカウント名
     */
    public static final String ACCOUNT_ID = "accountId";

    /**
     * パスワード
     */
    public static final String PASSWORD = "password";
    
    public enum DEVICE_TYPE {
        SMART_PHONE,
        TABLET,
        PC
    };


}
