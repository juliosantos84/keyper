package com.everythingbiig.keyper.bip39;

import com.google.gson.Gson;

public class MnemonicPhrase {

    public static final int PHRASE_SIZE_DEFAULT = 24;

    private String[] phrase = null;

    public MnemonicPhrase() {
        this.phrase = new String[PHRASE_SIZE_DEFAULT];
    }

    public MnemonicPhrase(String[] phrase) {
        this.phrase = phrase;
    }

    public void setPhrase(String[] phrase) {
        this.phrase = phrase;
    }

    public String[] getPhrase() {
        return phrase;
    }

    public String toText() {
        return new Gson().toJson(this.phrase);
    }
}