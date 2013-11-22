// Copyright (c) 2011 Tim Niblett All Rights Reserved.
//
// File:        IOUtil.java  (06-Apr-2011)
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

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

public class IOUtil {
    static final Logger LOG = LoggerFactory.getLogger(IOUtil.class);

    private static final int BUFSZ =  16384;

    private IOUtil() {

    }

    public static String loadStringUTF8(URL url) throws IOException {
        return new String(loadBytes(url), Charset.forName("utf-8"));
    }

    public static byte[] loadBytes(File file) throws IOException {
        //return loadBytes(file.toURI().toURL());
        try (final FileChannel channel = new FileInputStream(file).getChannel()) {
            final long size = channel.size();
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
            byte[] out = new byte[(int)size];
            buffer.get(out);
            return out;
        }
    }

    public static List<String> loadLines(File file) throws IOException {
        LineNumberReader reader = new LineNumberReader(new BufferedReader(new FileReader(file)));
        List<String> out = Lists.newArrayList();
        String line;
        while ((line = reader.readLine()) != null) {
            out.add(line.trim());
        }
        return Collections.unmodifiableList(out);
    }

    

    public static byte[] loadBytes(URL url) throws IOException {
        InputStream is = url.openStream();
        try {
            return copyStream(is);
        } finally {
            is.close();
        }
    }

    public static byte[] gzip(byte[] data) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            GZIPOutputStream gos = new GZIPOutputStream(os);
            gos.write(data, 0, data.length);
            gos.finish();
            gos.close();
            return os.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] gunzip(byte[] data) {
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(data);
            GZIPInputStream gis = new GZIPInputStream(is);
            byte[] out = IOUtil.copyStream(gis);
            gis.close();
            return out;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] deflate(byte[] data) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            DeflaterOutputStream gos = new DeflaterOutputStream(os);
            gos.write(data, 0, data.length);
            gos.finish();
            gos.close();
            return os.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] inflate(byte[] data) {
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(data);
            InflaterInputStream gis = new InflaterInputStream(is);
            byte[] out = IOUtil.copyStream(gis);
            gis.close();
            return out;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static byte[] copyStream(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(BUFSZ * 20);
        byte[] buf = new byte[BUFSZ];
        int nRead;
        while ((nRead = in.read(buf)) != -1) {
            out.write(buf, 0, nRead);
        }
        out.flush();
        byte[] ret = out.toByteArray();
        out.close();
        return ret;
    }

    public static void storeString(String string, File file) throws IOException {
        storeBytes(string.getBytes(Charset.forName("utf-8")), file);
    }

    public static void storeBytes(byte[] bytes, File file) throws IOException {
        FileChannel dstChannel = new FileOutputStream(file).getChannel();
        try {
            ByteBuffer buf = ByteBuffer.wrap(bytes);
            while (buf.hasRemaining()) {
                dstChannel.write(buf);
            }
        }  finally {
            dstChannel.close();
        }
    }

    private static void writeData(byte data[], final int sz, OutputStream out)
            throws IOException {
        int nLeft = sz;
        int nWritten = 0;
        while (nLeft > 0) {
            int thisGo = (nLeft > BUFSZ) ? BUFSZ : nLeft;
            out.write(data, nWritten, thisGo);
            nLeft -= thisGo;
            nWritten += thisGo;
        }
    }

    public static List<String> loadLines(String text) {
        LineNumberReader rdr = new LineNumberReader(new StringReader(text));
        List<String> out = Lists.newArrayList();
        try {
            String line;
            while ((line = rdr.readLine()) != null) {
                out.add(line);
            }
            rdr.close();
        } catch (IOException e) {
            // ok
        }
        return out;
    }

    public static String join(List<String> strings, String sep) {
        StringBuilder out = new StringBuilder();
        for (String string : strings) {
            out.append(string);
            if (sep != null) {
                out.append(sep);
            }
        }
        return out.toString();
    }


    public static Properties loadProperties(URL resource) {
        InputStream is = null;
        try {
            is = resource.openStream();
            Properties p = new Properties();
            p.load(is);
            is.close();
            return p;
        } catch (IOException e) {
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

}
