package project;


import java.util.HashMap;

public class TextDoc {

    private HashMap<String, Integer> wordVector_;
    private String fileName_;

    public TextDoc(String fileName) {
        wordVector_ = new HashMap<>();
        this.fileName_ = fileName;
    }


    public HashMap<String, Integer> getWordVector_() {
        return wordVector_;
    }

    public void setWordVector_(HashMap<String, Integer> wordVector_) {
        this.wordVector_ = wordVector_;
    }

    public String getFileName_() {
        return fileName_;
    }

    public void setFileName_(String fileName_) {
        this.fileName_ = fileName_;
    }
}
