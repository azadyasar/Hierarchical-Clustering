package project;

import java.util.ArrayList;
import java.util.HashMap;


public class Cluster {

    /* List contains all the text documents included in this cluster */
    private ArrayList<TextDoc> textDocList_;
    /* Stores the mean(centroid) of all the documents */
    private HashMap<String, Integer> centroid_;



    public Cluster() {
        textDocList_ = new ArrayList<>();
        centroid_ = new HashMap<>();
    }


    public void addDocument(TextDoc new_doc) {
        textDocList_.add(new_doc);
        calculateCentroid(new_doc.getWordVector_());
    }

    public void addDocumentList(ArrayList<TextDoc> new_doc_list) {
        textDocList_.addAll(new_doc_list);
        for (int i = 0; i < new_doc_list.size(); i++) {
            calculateCentroid(new_doc_list.get(i).getWordVector_());
        }
    }

    private void calculateCentroid(HashMap<String, Integer> new_doc) {
        /* Replacing of older key-value pairs with new ones has not been taken care of. It will affect frequency
        accuracy.
         */
        centroid_.putAll(new_doc);
    }


    /* Getters and Setters */
    public ArrayList<TextDoc> getTextDocList_() {
        return textDocList_;
    }

    public void setTextDocList_(ArrayList<TextDoc> textDocList_) {
        this.textDocList_ = textDocList_;
        for (int i = 0; i < textDocList_.size(); i++) {
            calculateCentroid(textDocList_.get(i).getWordVector_());
        }
    }

    public HashMap<String, Integer> getCentroid_() {
        return centroid_;
    }

    public void setCentroid_(HashMap<String, Integer> centroid_) {
        this.centroid_ = centroid_;
    }


}
