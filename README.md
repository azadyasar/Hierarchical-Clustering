# Hierarchical-Clustering

This project demonstrates the bare-bones version of Hierarchical Clustering. It is particularly focused on analysing and clustering of Text Documents.

## Cluster.java

Cluster represents a collection of documents that fall into the same topic(hopefully).

## Aggloremative.java

Aggloremative is the bottom-up approach of implementing Hierarchical Clustering. It first regards every document as single cluster then it merges two most similar documents into one new cluster, and so on. For similarity measurements refer to Algebra.java

## TextAnalyser.java

TextAnalyser is mainly used for removing stopwords from a document e.g., a, the, he, etc. It also finds the frequency of words from a text document as a bag of words model.

## Algebra.java

Contains similarity and distance measurement methods. Jaccard, Simple Matching, Cosine similarities are implemented.
