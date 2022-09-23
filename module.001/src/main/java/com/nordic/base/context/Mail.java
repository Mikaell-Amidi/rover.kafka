package com.nordic.base.context;

public enum Mail {
    mail("mail");

    private final String message;

    public String value() {
        return this.message;
    }

    Mail(String message) {
        this.message = message;
    }
}
