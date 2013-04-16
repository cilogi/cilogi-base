// Copyright (c) 2012 Tim Niblett. All Rights Reserved.
//
// File:        FileUtil.java  (11/05/12)
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;

public class FileUtil {
    static final Logger LOG = LoggerFactory.getLogger(FileUtil.class);

    private FileUtil() {}

    public static void deleteDirectory(File dirFile, final boolean isDeleteDirectory) throws IOException {
        final Path dir = dirFile.toPath();
        
        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path file,
                                             BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dirFile,
                                                      IOException exc) throws IOException {
                if (exc == null) {
                    if (!dirFile.equals(dir) && isDeleteDirectory) {
                        Files.delete(dirFile);
                    }
                    return CONTINUE;
                } else {
                    throw exc;
                }
            }

        });
    }    
}
