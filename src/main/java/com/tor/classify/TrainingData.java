package com.tor.classify;


public class TrainingData {
//    Model model = new Model();
//    Train train = new Train();
//    AlgorithmUtil algorithmUtil = new AlgorithmUtil();
//    public Model getModel() {
//        return this.model;
//    }
//    /**
//     * 创建模型,保存模型
//     * @param train：存储着模型信息的对象。
//     * @return  加入标签的测试集合
//     */
//    public void show_Model(Train train) throws Exception {
//        String feature_arffpath = train.getFeaturepath_arff();
//        String algorithm = train.getMethod();
//        int flag=0;
//        ConverterUtils.DataSource sourceTrain = null;
//        sourceTrain = new ConverterUtils.DataSource(feature_arffpath);
//        Instances train1 = sourceTrain.getDataSet();//train1的内容就是从feature.arff中读到。
//        if (train1.classIndex() == -1)
//        {
//            train1.setClassIndex(train1.numAttributes() - 1);
//        }
//
//        if(algorithm.equals("C4.5")){
//            flag=1;
//        }
//        else if(algorithm.equals("RandomForest")) {
//            flag=2;
//        }
//        // 1.C4.5（J48）
//        if (flag == 1)
//        {
//            FilteredClassifier fc = new FilteredClassifier();
//            J48 j48 = new J48();
//            fc.setClassifier(j48);
//            fc.buildClassifier(train1);
//            //保存模型
//            algorithmUtil.saveModel(fc, train);
//            //保存模型十折交叉验证信息
//            algorithmUtil.saveModelInfo(fc, train1, train);
//            model = algorithmUtil.getModel();
//        }
//        // 2.RandomForest
//        else if (flag == 2) {
//            FilteredClassifier fc = new FilteredClassifier();
//            RandomForest rf = new RandomForest();
//            fc.setClassifier(rf);
//            fc.buildClassifier(train1);
//            //保存模型,flag指示模型类别
//            //AlgorithmUtil b = new AlgorithmUtil();
//            algorithmUtil.saveModel(fc, train);
//            //保存模型十折交叉验证信息
//            algorithmUtil.saveModelInfo(fc, train1, train);
//            model = algorithmUtil.getModel();
//        }
//
//    }

}
