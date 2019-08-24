package com.everythingbiig.keyper.bip39;

public class MnemonicPhrase {

    public static final int PHRASE_SIZE_DEFAULT = 24;

    private String[] phrase = null;

    public MnemonicPhrase() {
        phrase = new String[PHRASE_SIZE_DEFAULT];
    }

    public void setPhrase(String[] phrase) {

    }

    public String[] getPhrase() {
        return phrase;
    }

    public String toText() {
        return "";
    }
}