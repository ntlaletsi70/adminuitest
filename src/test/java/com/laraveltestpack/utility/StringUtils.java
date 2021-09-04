package com.laraveltestpack.utility;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StringUtils {
    public static String exceptionToString(Throwable e) {
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        return writer.toString();
    }
}
