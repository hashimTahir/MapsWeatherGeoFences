package com.hashim.mapswithgeofencing.Helper;

import java.io.UnsupportedEncodingException;

public class EnCodeDeCodeByteArray {

    private static String hTag = LogToastSnackHelper.hMakeTag(EnCodeDeCodeByteArray.class);

    public static String hDecodeToString(byte[] hByte) {
        String hDecoded = null;
        try {
            hDecoded = new String(hByte, "ISO-8859-1");
            return hDecoded;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return hDecoded;


    }

}
