/**
 * 
 */
package hashimotonet.controller.base;

/**
 * 
 */
public class ControllerBaseImpl implements ControllerBase {

	/**
	 * 
	 */
	public ControllerBaseImpl() {
		super();
	}


    public DEVICE_TYPE getDeviceType(String userAgent) {
        //
        //スマートフォン系
        //
 
        //iPhone / iPod
        if(userAgent.indexOf("iPhone") != -1) {
            return DEVICE_TYPE.SMART_PHONE;
        }
 
        //Android
        if(userAgent.indexOf("Android") != -1 && userAgent.indexOf("Mobile") != -1) {
            return DEVICE_TYPE.SMART_PHONE;
        }
 
        //
        //タブレット系
        //
 
        //iPad
        if(userAgent.indexOf("iPad") != -1) {
            return DEVICE_TYPE.TABLET;
        }
 
        //Android
        if(userAgent.indexOf("Android") != -1) {
            return DEVICE_TYPE.TABLET;
        }
 
        //その他なので、とりあえずPC扱い
        return DEVICE_TYPE.PC;
    }
}
