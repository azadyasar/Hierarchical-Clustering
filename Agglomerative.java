package project;


import java.util.*;

public class Agglomerative {

    /* Contains every single document as a cluster, then they will be merged */
    private ArrayList<Cluster> clusterList_;
    private int desiredClusterNumber_;
    /* After merging and clustering is done, and desited cluster number is reached */
    private ArrayList<Cluster> outputClusterList_;
    
    private ArrayList<ArrayList<Double>> distanceMatrix_;
    
    public Agglomerative() {
        desiredClusterNumber_ = 1;
        clusterList_ = new ArrayList<>();
        outputClusterList_ = new ArrayList<>();
        distanceMatrix_ = new ArrayList<>();
    }
    
    public Agglomerative(int desiredClusterNumber) {
        desiredClusterNumber_ = desiredClusterNumber;
        clusterList_ = new ArrayList<>();
        outputClusterList_ = new ArrayList<>();
        distanceMatrix_ = new ArrayList<>();
    }
    
    public void clusterDocuments() {
        calculateInitialDistances();
        int counter = 0;
        do {
            double distMin = 1.1;
            Cluster doc1 = new Cluster();
            Cluster doc2 = new Cluster();
            for (int i = 0; i < distanceMatrix_.size(); i++) {
                for (int j = i+1; j < distanceMatrix_.get(0).size(); j++) {
                    if (distanceMatrix_.get(i).get(j) < distMin) {
                        distMin = distanceMatrix_.get(i).get(j);
                        doc1 = clusterList_.get(i);
                        doc2 = clusterList_.get(j);
                    }
                }
            }
            System.out.printf("New min. distance found to be %.4f w/ docs ", distMin);
            Cluster merged_cluster = new Cluster();
            merged_cluster.setTextDocList_(doc1.getTextDocList_());
            merged_cluster.addDocumentList(doc2.getTextDocList_());
            clusterList_.remove(doc1);
            clusterList_.remove(doc2);
            clusterList_.add(merged_cluster);
            ArrayList<TextDoc> temp = merged_cluster.getTextDocList_();
            for (int i = 0; i < temp.size(); i++) {
                System.out.println(temp.get(i).getWordVector_().toString());
                Set<String > key_set = temp.get(i).getWordVector_().keySet();
                /*for (String word : key_set)
                    System.out.printf("%s: %d, ", word, temp.get(i).get(word));*/
            }
//            System.out.printf("New cluster: ");
//            System.out.println(merged_cluster.getCentroid_().toString());
            calculateNewDistances();
            counter++;
        } while (desiredClusterNumber_ < outputClusterList_.size());
    }

    /* Fills the distanceMatrix by distances, produces (cluster_size-1)x(cluster_size) distance matrix */
    private void calculateInitialDistances() {
        System.out.printf("Calculating Initial Distances...Cluster size: ");
        int cluster_size = clusterList_.size();
        System.out.println(cluster_size);
        for (int i = 0; i < cluster_size; i++) {
            ArrayList<Double> distances = new ArrayList<>(Collections.nCopies(cluster_size, 1.0));
            for (int j = 0; j < cluster_size; j++) {
                double distance;
//                System.out.printf("Distance between %s-%s\n", clusterList_.get(i).getCentroid_().toString(),
//                    clusterList_.get(j).getCentroid_().toString());
                if (i == j)
                    distance = 1.0;
                else {
                    /*distance = Algebra.jaccardDistance(clusterList_.get(i).getCentroid_(),
                    clusterList_.get(j).getCentroid_());*/
                    distance = Algebra.cosineDistance(clusterList_.get(i).getCentroid_(),
                        clusterList_.get(j).getCentroid_());
                }
//                System.out.printf("%.8f\n", distance);
                distances.set(j, distance);
            }
            distanceMatrix_.add(distances);
        }
/*//        System.out.printf("Done! size: %d Result:\n", distanceMatrix_.size());
        for (int i = 0; i < distanceMatrix_.size(); i++) {
            for (int j = 0; j < cluster_size; j++) {
                System.out.printf("%.8f ", distanceMatrix_.get(i).get(j));
            }
            System.out.println();
        }*/
    }

    /* After merging two clusters, this method will be called and the new distances will be calculated */
    private void calculateNewDistances() {
        System.out.printf("Calculating new distances... Cluster size: ");
        int cluster_size = clusterList_.size();
        System.out.printf("%d\n", cluster_size);
        distanceMatrix_ = new ArrayList<>(cluster_size);
        for (int i = 0; i < cluster_size; i++) {
            ArrayList<Double> distances = new ArrayList<>(Collections.nCopies(cluster_size, 1.0));
            for (int j = 0; j < cluster_size; j++) {
                double distance;
                /*System.out.printf("Distance between %s-%s: ", clusterList_.get(i).getCentroid_().toString(),
                    clusterList_.get(j).getCentroid_().toString());*/
                if (i == j)
                    distance = 1.0;
                else {
                    /*distance = Algebra.jaccardDistance(clusterList_.get(i).getCentroid_(),
                    clusterList_.get(j).getCentroid_());*/
                    distance = Algebra.cosineDistance(clusterList_.get(i).getCentroid_(),
                        clusterList_.get(j).getCentroid_());
                }
                distances.set(j, distance);
//                System.out.printf("%.8f\n", distance);
            }
            distanceMatrix_.add(distances);
        }

       /* System.out.printf("Done! size: %d Result:\n", distanceMatrix_.size());
        for (int i = 0; i < distanceMatrix_.size(); i++) {
            for (int j = 0; j < cluster_size; j++) {
                System.out.printf("%.8f ", distanceMatrix_.get(i).get(j));
            }
            System.out.println();
        }*/
    }
            
    /* Getters and Setters */

    public ArrayList<Cluster> getClusterList_() {
        return clusterList_;
    }

    /* This setter should be called before calling clusterDocuments() method */
    public void setClusterList_(ArrayList<Cluster> clusterList_) {
        this.clusterList_ = clusterList_;
        this.outputClusterList_ = clusterList_;
        this.distanceMatrix_ = new ArrayList<>(clusterList_.size());
    }

    public int getDesiredClusterNumber_() {
        return desiredClusterNumber_;
    }

    public void setDesiredClusterNumber_(int desiredClusterNumber_) {
        this.desiredClusterNumber_ = desiredClusterNumber_;
    }
    
    public ArrayList<Cluster> getOutputClusterList() {
        return this.outputClusterList_;
    }
}
