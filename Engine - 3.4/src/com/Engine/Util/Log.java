package com.Engine.Util;

public class Log {
	@SuppressWarnings("unchecked")
	public static <T extends Throwable> void sneekyThrow(Throwable exception) throws T { throw (T) exception; }
}
