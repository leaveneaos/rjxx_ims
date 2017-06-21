package com.rjxx.taxeasy.utils;

/**
 * Created by Administrator on 2017-06-21.
 */
public class UrlUtils {

    public static String convertPdfUrlDomain(String targetDomain, String pdfurl) {
        if (!pdfurl.contains(targetDomain) && (pdfurl.startsWith("http://") || pdfurl.startsWith("https://")) && pdfurl.length() > 10) {
            int pos = pdfurl.indexOf("/", 10);
            String currentDomain = pdfurl.substring(0, pos + 1);
            pdfurl = pdfurl.replace(currentDomain, targetDomain);
        }
        return pdfurl;
    }


}
