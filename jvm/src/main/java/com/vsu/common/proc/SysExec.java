package com.vsu.common.proc;

/**
 * @author Victor Su
 */
public class SysExec {
    public static int runCommand(String cmd) {
        try {
            Runtime rt = Runtime.getRuntime();

            Process proc = rt.exec(cmd);

            // any error message?
            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");

            // any output?
            StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT");

            // kick them off
            errorGobbler.start();
            outputGobbler.start();

            // any error???
            int exitVal = proc.waitFor();
            System.out.println("Exit value: " + exitVal);

            return exitVal;

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return -1;
    }

    public static int startScript(String script) {
        return runCommand(script + " start");
    }

    public static int stopScript(String script) {
        return runCommand(script + " stop");
    }
}
