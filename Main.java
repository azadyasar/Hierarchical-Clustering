package project;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int request;
        String requestMsg = "1: Give a path for removing stopwords\n2: Enter cluster size:\n3: Give a path that contains text files to cluster" +
            "\n0: Exit\n??";

        String fileName;
        FileReaderEx fileReader = new FileReaderEx();
        File folder;
        TextAnalyser textAnalyser = new TextAnalyser();
        Agglomerative agglomerative = new Agglomerative();
        int desiredClusterSize = -1;
        do {
            System.out.println(requestMsg);
            request = scanner.nextInt();
            scanner.nextLine();

            switch (request) {
                case 1:
                    System.out.printf("Folder path: ");
                    fileName = scanner.nextLine();
                    folder = new File(fileName);
                    if (!folder.isFile() && folder.canRead()) {
                        fileReader.acquireStopwords(new File(STOPWORDS_FILEPATH));
                        fileReader.cutOffStopWordsFromFolder(folder);
                    } else
                        System.out.printf("Couldn't get the file try again!\n");
                    System.out.printf("Done!\n");
                    break;
                case 2:
                    System.out.printf("Cluster size: ");
                    desiredClusterSize = scanner.nextInt();
                    scanner.nextLine();
                    agglomerative.setDesiredClusterNumber_(desiredClusterSize);
                    break;
                case 3:
                    if (desiredClusterSize <= 0) {
                        System.out.printf("Set desired cluster size first..\n");
                        break;
                    } else {
                        fileName = scanner.nextLine();
                        folder = new File(fileName);
                        if (folder.isDirectory()) {
                            File files[] = folder.listFiles();
                            ArrayList<Cluster> clusterArrayList = new ArrayList<>();
                            for (File file : files) {
                                if (file.isFile() && file.getName().endsWith(".txt") && !file.getName().contains("DS")) {
                                    System.out.printf("Reading file %s\n", file.getName());
                                    HashMap<String,  Integer> wordVector = textAnalyser.wordToNewVector(fileReader.readTextFromFile(file));
                                    Cluster cluster = new Cluster();
                                    TextDoc textDoc = new TextDoc(file.getName());
                                    textDoc.setWordVector_(wordVector);
                                    cluster.addDocument(textDoc);
                                    clusterArrayList.add(cluster);
                                }
                            }
                            agglomerative.setClusterList_(clusterArrayList);
                            agglomerative.clusterDocuments();
                            ArrayList<Cluster> result = agglomerative.getClusterList_();
                            File output_folder = new File(fileName +  File.separator + "/output");
                            if (output_folder.exists())
                                fileReader.cleanFolder(output_folder);
                            else
                                output_folder.mkdir();
                            String paths[] = getPathNames(desiredClusterSize, output_folder.getPath());
                            for (int i = 0; i < result.size(); i++) {
                                ArrayList<TextDoc> cluster = result.get(i).getTextDocList_();
                                File clusterFolder = new File(paths[i]);
                                clusterFolder.mkdir();
                                for (int j = 0; j < cluster.size(); j++) {
                                    TextDoc doc_sample = cluster.get(j);
                                    File doc_out = new File(paths[i] + "/" + doc_sample.getFileName_());
                                    fileReader.writeToFile(doc_out, doc_sample);
                                }
                            }
                        } else
                            System.out.printf("Give a folder path!\n");
                    }
                default:
                    System.out.printf("Enter a legitimate number!\n");
                    break;

            }
        } while (request != 0);



        /*System.out.println("Enter folder path: ");
        String folderPath = scanner.nextLine();

        File folder = new File(folderPath);
        File[] fileList = new File[10];
        FileReaderEx fileReader = new FileReaderEx(folder, "UTF-8");
        if (!folder.isFile()) {
            fileList = folder.listFiles();
            for (File file : fileList) {
                if (file.getName().endsWith(".txt") && file.isFile()) {
                    System.out.println("Reading " + file.getName() + "...");
                }
            }
        } else
            System.err.println("Give folder path");*/

        /*TextAnalyser textAnalyser = new TextAnalyser();
        System.out.printf("Enter file path\n");
        String folderPath = scanner.nextLine();

        File folder = new File(folderPath);
        File[] fileList = new File[10];
        FileReaderEx fileReader = new FileReaderEx(folder, "UTF-8");
        String text;
        if (folder.isFile())
            text = fileReader.readText(folder);
        else {
            fileList = folder.listFiles();
            for (File file : fileList) {
                HashMap<String, Integer> word_vector = new HashMap<>();
                if (file.getName().endsWith(".txt") && file.isFile()) {
                    System.out.println("Reading " + file.getName() + "...");
                    text = fileReader.readText(file);
                    System.out.println("Text:\n" + text);
                    textAnalyser.wordToVector(text, word_vector);
                    Set<String> key_set = word_vector.keySet();
                    System.out.printf("Word\tFrequency\n");
                    for (String word : key_set)
                        System.out.printf("%s\t%d\n", word, word_vector.get(word));
                }
            }
        }*/


    }

    private static String[] getPathNames(int clusterNumber, String pathName) {
        String paths[] = new String[clusterNumber];
        for (int i = 0; i < clusterNumber; i++)
            paths[i] = new String(pathName + "/cluster_" + (i+1));
        return paths;
    }

    public static final String STOPWORDS_FILEPATH = "/Users/ay/Documents/YTU 2016-2017 Spring/Search Engines/datasets/removalWords.txt";

}
