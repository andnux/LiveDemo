package top.andnux.liblive.jni;

public interface LiveStateChangeListener {

	/**
	 * 发送错误
	 * @param code
	 */
	void onError(int code);
	
}
