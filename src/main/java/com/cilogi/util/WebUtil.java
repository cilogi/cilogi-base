// Copyright (c) 2014 Cilogi. All Rights Reserved.
//
// File:        WebUtil.java  (03/03/14)
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

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class WebUtil {
    static final Logger LOG = LoggerFactory.getLogger(WebUtil.class);

    private WebUtil() {}

    public static String postURL(URL url) throws IOException {
        return postURL(url, null);
    }

    public static String postURL(URL url, String contents) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-type", "application/json");

        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        if (contents != null) {
            writer.write(contents);
        }
        writer.close();  // have to close in order to read response

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream is = connection.getInputStream();
            byte[] data = ByteStreams.toByteArray(is);
            is.close();
            return new String(data, Charsets.UTF_8);
        } else {
            return null;
        }
    }

    public static String getURL(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream is = connection.getInputStream();
            byte[] data = ByteStreams.toByteArray(is);
            is.close();
            return new String(data, Charsets.UTF_8);
        } else {
            return null;
        }
    }
}
