// Copyright (c) 2012 Tim Niblett. All Rights Reserved.
//
// File:        RunProcess.java  (17/06/12)
// Author:      tim
//
// Copyright in the whole and every part of this source file belongs to
// Tim Niblett (the Author) and may not be used, sold, licenced, 
// transferred, copied or reproduced in whole or in part in 
// any manner or form or in or on any media to any person other than 
// in accordance with the terms of The Author's agreement
// or otherwise without the prior written consent of The Author.  All
// information contained in this source file is confidential information
// belonging to The Author and as such may not be disclosed other
// than in accordance with the terms of The Author's agreement, or
// otherwise, without the prior written consent of The Author.  As
// confidential information this source file must be kept fully and
// effectively secure at all times.
//


package com.cilogi.util;


import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Run an operating system process in a separate thread.
 */
public class RunProcess {
    static final Logger LOG = LoggerFactory.getLogger(RunProcess.class);

    private ProcessBuilder builder;
    private Process process;
    private ProcessUnblocker monitor;

    public RunProcess(String... args) {
        builder = new ProcessBuilder(args);
    }

    public RunProcess(List<String> args) {
        builder = new ProcessBuilder(args);
    }

    public void setDirectory(File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException("Directory " + file + " doesn't exist");
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException("File " + file + " is not a directory");
        }
        if (process != null) {
            throw new IllegalStateException("Process has started, can't change directory");
        }
        builder.directory(file);
    }

    public void addToPath(File file) {
        Preconditions.checkNotNull(file);
        Preconditions.checkArgument(file.exists(), "File " + file + " doesn't exist");
        Preconditions.checkArgument(file.isDirectory(), "File " + file + " isn't a directory");
        Map<String,String> map = builder.environment();
        String path = map.get("Path");
        String newPath = path + ";" + file.getAbsolutePath();
        LOG.debug("setting path to: " + newPath);
        map.put("Path", newPath);
    }

    public void run() throws IOException {
        run(new byte[0]);
    }

    public void run(byte[] input) throws IOException {
        process = builder.start();
        monitor = new ProcessUnblocker(commandString(), process, input);
        Thread monitorThread = new Thread(monitor);
        monitorThread.start();
    }

    private String commandString() {
        List<String> commands = builder.command();
        return Arrays.toString(commands.toArray());
    }

    /**
     * Find the exit status of the process
     *
     * @return the exist status of the process
     * @throws NullPointerException        if the process hasn't started
     * @throws IllegalThreadStateException if the process hasn't finished
     */
    public int exitValue() {
        if (process == null) {
            throw new NullPointerException("Process hasn't been started yet");
        }
        return process.exitValue();
    }

    public int waitFor() throws InterruptedException {
        if (process == null) {
            throw new NullPointerException("Process hasn't been started yet");
        }
        int code = process.waitFor();
        monitor.cleanup();
        return code;
    }

    public byte[] output() {
        return monitor.output();
    }

    public byte[] errorOutput() {
        return monitor.errorOutput();
    }

    static class ProcessUnblocker implements Runnable {
        private String commandString;
        Process process;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        private boolean cleaned = false;
        private byte[] stdin;

        ProcessUnblocker(String commandString, Process process, byte[] stdin) {
            Preconditions.checkNotNull(process, "Process can't be null");
            Preconditions.checkNotNull(stdin, "Data can't be null");
            this.commandString = commandString;
            this.process = process;
            this.stdin = stdin;
        }

        public void run() {
            boolean lastFail = false;
            boolean written = false;
            try {
                while (true) {
                    try {
                        if (lastFail) {
                            Thread.sleep(10);
                            lastFail = false;
                        }
                    } catch (InterruptedException e) {
                        lastFail = false;
                        return;
                    }
                    try {
                        if (!written && stdin.length > 0) {
                            OutputStream is = process.getOutputStream();
                            is.write(stdin, 0, stdin.length);
                            is.close();
                            written = true;
                        }
                    } catch (IOException e) {
                        if (e.getMessage() != null && !e.getMessage().startsWith("The pipe has been ended")) {
                            LOG.warn(commandString + ": IOException running process (input): " + e.getMessage());
                        }
                        return;
                    }
                    try {
                        boolean thisFail = !handle(process.getInputStream(), out);
                        lastFail = lastFail || thisFail;
                    } catch (IOException e) {
                        LOG.warn(commandString + ": IOException running process (output): " + e.getMessage());
                        return;
                    }
                    try {
                        boolean thisFail = !handle(process.getErrorStream(), err);
                        lastFail = lastFail || thisFail;
                    } catch (IOException e) {
                        LOG.warn(commandString + ": IOException running process (error): " + e.getMessage());
                        return;
                    }
                    try {
                        process.exitValue();
                        return;
                    } catch (IllegalThreadStateException e) {
                        // expected
                    }
                }
            } finally {
                cleanup();
            }
        }

        private synchronized void cleanup() {
            //noinspection EmptyCatchBlock
            try {
                handle(process.getInputStream(), out);
                out.close();
            } catch (IOException e) {
            }
            //noinspection EmptyCatchBlock
            try {
                handle(process.getErrorStream(), err);
                err.close();
            } catch (IOException e) {
            }
            cleaned = true;
        }

        synchronized boolean handle(InputStream is, OutputStream os) throws IOException {
            if (!cleaned) {
                int n = is.available();
                if (n > 0) {
                    byte[] b = new byte[n];
                    int count = is.read(b, 0, n);
                    if (count > 0) {
                        //LOG.debug(n + " read <" + new String(b) + ">");
                        os.write(b, 0, count);
                        return true;
                    }
                }
            }
            return false;
        }

        synchronized byte[] output() {
            return out.toByteArray();
        }

        synchronized byte[] errorOutput() {
            return err.toByteArray();
        }
    }
}


