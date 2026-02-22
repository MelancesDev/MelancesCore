package com.melances.core.ui;

public final class UiResult {

    public final boolean success;
    public final String where;
    public final String reasonCode;
    public final String reasonHuman;
    public final Throwable error;

    private UiResult(boolean success, String where, String reasonCode, String reasonHuman, Throwable error) {
        this.success = success;
        this.where = where;
        this.reasonCode = reasonCode;
        this.reasonHuman = reasonHuman;
        this.error = error;
    }

    public static UiResult ok(String where) {
        return new UiResult(true, where, "OK", "OK", null);
    }

    public static UiResult fail(String where, String reasonCode, String reasonHuman, Throwable error) {
        return new UiResult(false, where, reasonCode, reasonHuman, error);
    }
}
