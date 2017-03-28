package project;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class Algebra {


    /* Returns the jaccard distance, calculated as J = (M_10 + M_01) / (M_11 + M_10 + M_01) */
    public static double jaccardDistance(final HashMap<String, Integer> doc1, final HashMap<String, Integer> doc2) {
        double result = 1;
        int m_11, m_10, m_01;
        /* Gathering all the words that are used in either documents */
        Set<String> wordSet = new HashSet<>(doc1.keySet());
        wordSet.addAll(doc2.keySet());

        int occurDoc1 = 0;
        int occurDoc2 = 0;
        m_11 = 0; m_01 = 0; m_10 = 0;
        for (String word : wordSet) {
            if (doc1.get(word) == null)
                occurDoc1 = 0;
            else
                occurDoc1 = doc1.get(word);
            if (doc2.get(word) == null)
                occurDoc2 = 0;
            else
                occurDoc2 = doc2.get(word);
            if ( !(occurDoc1 + occurDoc2 == 0 )) {
                if (occurDoc1 != 0 && occurDoc2 != 0)
                    m_11++;
                else if (occurDoc1 == 0)
                    m_01++;
                else
                    m_10++;
            }
        }
//        System.out.printf("m_11: %d, m_10: %d, m_01: %d\n", m_11, m_10, m_01);
        result = (double) (m_01 + m_10) / (m_01 + m_10 + m_11);
        return result;
    }

    /* Returns the simple matching distance, calculated as SMD = (M_11 + M_00)/ (M_11 + M_01 + M_10 + M_00) */
    public static double simpleMatchingDistance(final HashMap<String, Integer> doc1, final HashMap<String, Integer> doc2) {
        double result = 1;
        int m_11, m_10, m_01, m_00;
        /* Gathering all the words that are used in either documents */
        Set<String> wordSet = new HashSet<>(doc1.keySet());
        wordSet.addAll(doc2.keySet());

        int occurDoc1 = 0;
        int occurDoc2 = 0;
        m_11 = 0; m_01 = 0; m_10 = 0; m_00 = 0;
        for (String word : wordSet) {
            if (doc1.get(word) == null)
                occurDoc1 = 0;
            else
                occurDoc1 = doc1.get(word);
            if (doc2.get(word) == null)
                occurDoc2 = 0;
            else
                occurDoc2 = doc2.get(word);
            if (occurDoc1 == 0 && occurDoc2 == 0)
                m_00++;
            else if (occurDoc1 != 0 && occurDoc2 != 0)
                m_11++;
            else if (occurDoc1 == 0)
                m_01++;
            else
                m_10++;
        }
//        System.out.printf("m_11: %d, m_10: %d, m_01: %d, m_00: %d\n", m_11, m_10, m_01, m_00);
        result = (double) (m_00 + m_11) / (m_00 + m_01 + m_10 + m_11);
        return result;
    }


    /* Returns the jaccard similarity, calculated as J = (M_11) / (M_11 + M_10 + M_01) */
    public static double jaccardSimilarity(final HashMap<String, Integer> doc1, final HashMap<String, Integer> doc2) {
        double result = 1;
        int m_11, m_10, m_01;
        /* Gathering all the words that are used in either documents */
        Set<String> wordSet = new HashSet<>(doc1.keySet());
        wordSet.addAll(doc2.keySet());

        int occurDoc1 = 0;
        int occurDoc2 = 0;
        m_11 = 0; m_01 = 0; m_10 = 0;
        for (String word : wordSet) {
            if (doc1.get(word) == null)
                occurDoc1 = 0;
            else
                occurDoc1 = doc1.get(word);
            if (doc2.get(word) == null)
                occurDoc2 = 0;
            else
                occurDoc2 = doc2.get(word);
            if ( !(occurDoc1 + occurDoc2 == 0 )) {
                if (occurDoc1 != 0 && occurDoc2 != 0)
                    m_11++;
                else if (occurDoc1 == 0)
                    m_01++;
                else
                    m_10++;
            }
        }
        System.out.printf("m_11: %d, m_10: %d, m_01: %d\n", m_11, m_10, m_01);
        result = (double) (m_11) / (m_01 + m_10 + m_11);
        return result;
    }

    public static double cosineDistance(HashMap<String, Integer> doc1, HashMap<String, Integer> doc2) {
        Set<String> wordSet = new HashSet<>(doc1.keySet());
        wordSet.addAll(doc2.keySet());
        int occurDoc1 = 0; int occurDoc2 = 0;
        double numerator = 0;
        for (String word : wordSet) {
            occurDoc1 = doc1.get(word) == null ? 0 : doc1.get(word);
            occurDoc2 = doc2.get(word) == null ? 0 : doc2.get(word);
            numerator += occurDoc1 * occurDoc2;
        }
        return  1 - ( numerator / (normOfTextDoc(doc1) * normOfTextDoc(doc2)) );
    }

    /* Returns the norm(magnitude, length) of text document for cosine distance
        |x| = (x_1*x_1 + x_2*x_2 * ... x_n*x_n)^(1/2)  */
    public static double normOfTextDoc(final HashMap<String, Integer> document) {
        double result = 0;
        Set<String> wordSet = document.keySet();
        for (String word : wordSet)
            result += document.get(word) * document.get(word);
        return Math.sqrt(result);
    }

}
