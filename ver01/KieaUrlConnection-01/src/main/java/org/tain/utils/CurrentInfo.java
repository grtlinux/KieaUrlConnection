package org.tain.utils;

public class CurrentInfo {

	/*
	 *    log.info("KANG-20210405 >>>>> {} {}", CurrentInfo.get(), LocalDateTime.now());
	 *    log.info("KANG-20210405 >>>>> {}", CurrentInfo.get());
	 */
	public static String get() {
		StringBuffer sb = new StringBuffer();
		StackTraceElement element = Thread.currentThread().getStackTrace()[2];
		sb.append(element.getClassName()).append('.').append(element.getMethodName()).append("()");
		//sb.append(" of ");
		//sb.append(element.getFileName());
		sb.append("#").append(element.getLineNumber());
		return sb.toString();
	}

}
