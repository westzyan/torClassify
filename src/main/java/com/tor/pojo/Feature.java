package com.tor.pojo;

public class Feature {
    //提取特征的算法
    private String featureAlgorithm;
    private String trainName;
    private String trainPath;
    //arff文件路径
    private String arffFilePath;
    //
    private String featureTxtPath;

    public String getFeatureAlgorithm() {
        return featureAlgorithm;
    }

    public void setFeatureAlgorithm(String featureAlgorithm) {
        this.featureAlgorithm = featureAlgorithm;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTrainPath() {
        return trainPath;
    }

    public void setTrainPath(String trainPath) {
        this.trainPath = trainPath;
    }

    public String getArffFilePath() {
        return arffFilePath;
    }

    public void setArffFilePath(String arffFilePath) {
        this.arffFilePath = arffFilePath;
    }

    public String getFeatureTxtPath() {
        return featureTxtPath;
    }

    public void setFeatureTxtPath(String featureTxtPath) {
        this.featureTxtPath = featureTxtPath;
    }

    @Override
    public String toString() {
        return "Features{" +
                "feature='" + featureAlgorithm + '\'' +
                ", trainName='" + trainName + '\'' +
                ", trainPath='" + trainPath + '\'' +
                ", featureArffPath='" + arffFilePath + '\'' +
                ", featureTxtPath='" + featureTxtPath + '\'' +
                '}';
    }
}
