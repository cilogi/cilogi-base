// Copyright (c) 2011 Tim Niblett All Rights Reserved.
//
// File:        JSONUtil.java  (07-Oct-2011)
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

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JSONUtil {
    static final Logger LOG = LoggerFactory.getLogger(JSONUtil.class);

    private JSONUtil() {

    }

    @SuppressWarnings({"unchecked"})
    public static Map<String,Pair<String,String>> stringMap(String jsonString) throws JSONException {
        JSONObject json = new JSONObject(jsonString);
        Map<String,Pair<String,String>> out = Maps.newHashMap();
        for (Iterator<String> it = (Iterator<String>)json.keys(); it.hasNext();) {
            String key = it.next();
            JSONArray pair = json.getJSONArray(key);
            String blobKey = pair.getString(0);
            String serveKey = pair.getString(1);
            out.put(key, Pair.of(blobKey, serveKey));
        }
        return out;
    }

    @SuppressWarnings({"unchecked"})
    public static Map<String,String> asStringMap(JSONObject obj) throws JSONException {
        Map<String,String> out = Maps.newHashMap();
        for (Iterator<String> it = obj.keys(); it.hasNext();) {
            String key = it.next();
            String val = obj.getString(key);
            out.put(key, val);
        }
        return out;
    }

    public static String stringListToJSON(List<String> list) {
        JSONArray out = new JSONArray();
        for (String string : list) {
            out.put(string);
        }
        return out.toString();
    }

    public JSONObject strings2json(String... args) {
        Preconditions.checkArgument(args.length % 2 == 0, "Even number of arguments, not " + args.length);
        JSONObject out = new JSONObject();
        try {
            for (int i = 0; i < args.length; i += 2) {
                    out.put(args[i], args[i+1]);
            }
            return out;
        }  catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Escape quotes, \, /, \r, \n, \b, \f, \t and other control characters (U+0000 through U+001F).
     * @param s the string to escape
     * @return escaped string
     */
    public static String escape(String s){
        if(s==null)
            return null;
        StringBuffer sb = new StringBuffer();
        escape(s, sb);
        return sb.toString();
    }

    /**
     * @param s - Must not be null.
     * @param sb
     */
    static void escape(String s, StringBuffer sb) {
        for(int i=0;i<s.length();i++){
            char ch=s.charAt(i);
            switch(ch){
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                default:
                    //Reference: http://www.unicode.org/versions/Unicode5.1.0/
                    if((ch>='\u0000' && ch<='\u001F') || (ch>='\u007F' && ch<='\u009F') || (ch>='\u2000' && ch<='\u20FF')){
                        String ss= Integer.toHexString(ch);
                        sb.append("\\u");
                        for(int k=0;k<4-ss.length();k++){
                            sb.append('0');
                        }
                        sb.append(ss.toUpperCase());
                    }
                    else{
                        sb.append(ch);
                    }
            }
        }//for
    }
}
