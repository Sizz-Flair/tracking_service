package com.giant.tracking.util;

/**
 * packageName    : com.giant.tracking.util
 * fileName       : Fn
 * author         : akfur
 * date           : 2024-04-04
 * description    :
 * ======================================================================
 * DATE             AUTHOR            NOTE
 * ----------------------------------------------------------------------
 * 2024-04-04         akfur
 */
public class Fn {
    public static boolean excelTypeCheck(String fileName) {
        return fileName.contains("xlsx") | fileName.contains("xls");
    }
}
