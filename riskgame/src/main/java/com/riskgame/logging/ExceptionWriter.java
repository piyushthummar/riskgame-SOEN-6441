
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package com.riskgame.logging;

import java.io.PrintWriter;
import java.io.Writer;

/**
 * Handles writing exceptions to the Logger Tab and and utility methods needed
 * to facilitate logging of exceptions
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 */
public class ExceptionWriter extends PrintWriter {
    /**
     * This is a constructor of this class to initialize Writer
     * @param writer
     */
    public ExceptionWriter(Writer writer) {
        super(writer);
    }

    /**
     * This method will wrapAroundWithNewlines
     * @param stringWithoutNewlines
     * @return new string
     */
    private String wrapAroundWithNewlines(String stringWithoutNewlines) {
        return ("\n" + stringWithoutNewlines + "\n");
    }

    /**
     * Convert a stacktrace into a string
     */
    public String getExceptionAsString(Throwable throwable) {
        throwable.printStackTrace(this);

        String exception = super.out.toString();

        return (wrapAroundWithNewlines(exception));
    }
}

