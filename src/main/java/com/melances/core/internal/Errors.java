package com.melances.core.internal;

public final class Errors {

    private Errors() {}

    public static String shortReason(Throwable t) {
        if (t == null) return "Bilinmeyen hata";
        if (t instanceof ClassNotFoundException) return "Sınıf bulunamadı";
        if (t instanceof NoClassDefFoundError) return "Bağımlılık eksik";
        if (t instanceof IllegalArgumentException) return "Geçersiz argüman";
        if (t instanceof SecurityException) return "İzin hatası";
        String n = t.getClass().getSimpleName();
        return n == null || n.isBlank() ? "Beklenmeyen hata" : n;
    }

    public static String sanitize(String s, int maxLen) {
        if (s == null) return "";
        s = s.replace("\r", " ").replace("\n", " ").trim();
        if (s.length() > maxLen) s = s.substring(0, maxLen);
        return s;
    }
}
