package com.example.changer.model;


import org.tensorflow.Graph;
import org.tensorflow.SavedModelBundle;

public class Model {

    private static final String INPUT_TENSOR_NAME = "ImageTensor:0";
    private static final String OUTPUT_TENSOR_NAME = "SemanticPredictions:0";
    private static final int INPUT_SIZE = 513;
    private static final String FROZEN_GRAPH_NAME = "frozen_inference_graph";

    private Graph graph;

    private void loadModel() {
        SavedModelBundle savedModelBundle = SavedModelBundle.load("file:///assets/frozen_inference_graph.pb");

        if (savedModelBundle == null) {
            throw new RuntimeException("Cannot find inference graph in load");
        }

        graph = new Graph();

    }
}
