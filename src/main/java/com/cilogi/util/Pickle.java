// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        Pickle.java  (09/03/15)
// Author:      tim
//
// Copyright in the whole and every part of this source file belongs to
// Cilogi (the Author) and may not be used, sold, licenced, 
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

import java.io.*;

@SuppressWarnings({"unused"})
public class Pickle {
    static final Logger LOG = LoggerFactory.getLogger(Pickle.class);

    private Pickle() {

    }

    public static  byte[] pickle(Object obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        oos.close();
        return baos.toByteArray();
    }

    public static byte[] pickleClean(Object obj) {
        try {
            return pickle(obj);
        } catch (IOException e) {
            throw new PickleException(e);
        }
    }

    public static Object unpickle(byte[] b) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return ois.readObject();
    }

    public static Object unpickleClean(byte[] b) {
        try {
            return unpickle(b);
        } catch (IOException | ClassNotFoundException e) {
            throw new PickleException(e);
        }
    }

    public static class PickleException extends RuntimeException {
        PickleException(Exception e) {
            super(e);
        }
    }
}
