package com.everythingbiig.keyper.bip39;

import java.util.Arrays;

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
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.phrase.length; i++) {
            sb.append(String.format("%02d: %s", (i+1), phrase[i]));
            sb.append("\n");
        }
        return sb.toString();
    }

    public String toJson() {
        return new Gson().toJson(this.phrase);
    }

    public void fromJson(String jsonPhrase) {
        this.phrase = new Gson().fromJson(jsonPhrase, new String[0].getClass());
    }

    @Override
    public boolean equals(Object mnemonicPhrase) {
        if ( !(mnemonicPhrase instanceof MnemonicPhrase)) {
            return false;
        }
        return Arrays.equals(this.phrase, ((MnemonicPhrase)mnemonicPhrase).getPhrase());
    }
}