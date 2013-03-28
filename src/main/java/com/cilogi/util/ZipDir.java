// Copyright (c) 2011 Tim Niblett All Rights Reserved.
//
// File:        ZipDir.java  (01-Oct-2011)
// Author:      tim
// $Id$
//
// Copyright in the whole and every part of this source file belongs to
// Tim Niblett (the Author) and may not be used,
// sold, licenced, transferred, copied or reproduced in whole or in
// part in any manner or form or in or on any media to any person
// other than in accordance with the terms of The Author's agreement
// or otherwise without the prior written consent of The Author.  All
// information contained in this source file is confidential information
// belonging to The Author and as such may not be disclosed other
// than in accordance with the terms of The Author's agreement, or
// otherwise, without the prior written consent of The Author.  As
// confidential information this source file must be kept fully and
// effectively secure at all times.
//


package com.cilogi.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.*;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class ZipDir {
    static final Logger LOG = Logger.getLogger(ZipDir.class);

    private static final int BUFSZ = 4096;

    private final File sourceDirectory;
    private final File zipFile;

    public ZipDir(File sourceDirectory, File zipFile) {
        this.sourceDirectory = sourceDirectory;
        this.zipFile = zipFile;
    }

    public void run() {
        try {
            OutputStream fout = new BufferedOutputStream(new FileOutputStream(zipFile));
            ZipOutputStream zout = new ZipOutputStream(fout);
            File dir = sourceDirectory;
            String path = dir.getName();
            addFiles(dir, path, zout);
            zout.close();
            LOG.debug("Zip file has been created!");
        }
        catch (IOException ioe) {
            LOG.error("IOException :" + ioe.getMessage(), ioe);
        }

    }

    void addFiles(File dir, String path, ZipOutputStream zout) throws IOException {
        for (File file : dir.listFiles()) {
            String name = file.getName();
            if (file.isDirectory()) {
                addFiles(file, path + "/" + name, zout);
            } else {
                byte[] buffer = new byte[BUFSZ];
                String filePath = path + "/" + file.getName();
                LOG.debug("Adding " + filePath);
                InputStream fin = new BufferedInputStream(new FileInputStream(file));
                zout.putNextEntry(new ZipEntry(filePath));
                int length;
                while ((length = fin.read(buffer)) > 0) {
                    zout.write(buffer, 0, length);
                }
                zout.closeEntry();
                fin.close();
            }
        }
    }

    public static void main(String[] args) {
        PropertyConfigurator.configure(ZipDir.class.getResource("debuglog.cfg"));
        try {
            String tag = DateUtil.formatIso8601Date(new Date()).replaceAll(":", "_");
            File src = new File("C:\\wamp\\www\\botanics");
            File dst = new File("G:\\savedsites\\botanics\\botanics-"+tag+".zip");
            ZipDir zip = new ZipDir(src, dst);
            zip.run();
        }  catch (Exception e) {
            LOG.error("oops", e);
        }
    }
}
