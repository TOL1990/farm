package com.test.util;

import com.aad.myutil.logger.MYLoggerFactory;
import one.nio.serial.Json;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.nio.charset.Charset;
import java.util.Base64;

/**
 * Created by sT3ls.
 */
public class JSONHelper
{
    public static Object objectToJSON(Object obj)
    {
        try
        {
            String jsonStr = Json.toJson(obj);
            JSONParser parser = new JSONParser();
//            JSONObject jsonObj = (JSONObject) parser.parse(jsonStr);
//            return jsonObj;
            return parser.parse(jsonStr);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            MYLoggerFactory.get().error(e.getMessage(), e);
            return new JSONObject();
        }

    }

    public static JSONObject bytesToJSON(byte[] bytes)
    {
        JSONObject json = null;
        try
        {
            String jsonStr = new String(Base64.getDecoder().decode(new String(bytes, Charset.forName("UTF8"))), Charset.forName("UTF8"));
            JSONParser parser = new JSONParser();
            json = (JSONObject) parser.parse(jsonStr);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            MYLoggerFactory.get().error(e.getMessage(), e);
        }

        return json;
    }

    public static byte[] JSONToBytes(JSONObject json)
    {
        try
        {
            return Base64.getEncoder().encode(json.toJSONString().getBytes("UTF8"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            MYLoggerFactory.get().error(e.getMessage(), e);
            return new byte[0];
        }
    }

    public static int getInt(Object json)
    {
        int i = -1;
        try
        {
            i = Integer.valueOf(json.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            MYLoggerFactory.get().error(e.getMessage(), e);
        }
        return i;
    }

    public static long getLong(Object json)
    {
        long l = -1;
        try
        {
            l = Long.valueOf(json.toString());
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
            MYLoggerFactory.get().error(e.getMessage(), e);
        }
        return l;
    }

    public static boolean getBoolean(Object json)
    {
        boolean b = false;
        try
        {
            b = Boolean.valueOf(json.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            MYLoggerFactory.get().error(e.getMessage(), e);
        }
        return b;
    }

    public static String getString(Object json)
    {
        String s = "";
        try
        {
            s = json.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            MYLoggerFactory.get().error(e.getMessage(), e);
        }
        return s;
    }
}
