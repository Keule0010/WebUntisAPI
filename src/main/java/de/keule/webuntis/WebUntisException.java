package de.keule.webuntis;

import java.io.IOException;

public class WebUntisException extends IOException{
	private static final long serialVersionUID = 1L;

	public WebUntisException(String msg) {
		super(msg);
	}
	
	public WebUntisException(Throwable cause) {
		super(cause);
	}
	
	public WebUntisException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
