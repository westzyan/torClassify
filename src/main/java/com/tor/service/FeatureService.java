package com.tor.service;


import com.tor.classify.AlgorithmUtil;
import com.tor.classify.ArffUtil;
import com.tor.classify.ClassifyFeatures;
import com.tor.pojo.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeatureService {
    ClassifyFeatures classifyFeatures = new ClassifyFeatures();
    AlgorithmUtil algorithmUtil = new AlgorithmUtil();
    ArffUtil arffUtil = new ArffUtil();
    @Autowired
    private ModelService modelService;

    public void getFeature(Feature feature) throws Exception {
        String trainFilePath = feature.getTrainPath();
        String traindeletePath = feature.getArffFilePath();
        String featureTxtPath = feature.getFeatureTxtPath();
        String featureSelectAlgorithm = feature.getFeatureAlgorithm();
        showFeature(trainFilePath, traindeletePath, featureTxtPath, featureSelectAlgorithm);
    }

    /**
     * 读取保存的模型信息
     *
     * @param trainFilePath:作为训练集的数据路径
     * @param trainDelete:保存剩余特征的.arff文件路径
     * @param featureTxtPath:保存剩余特征的.txt文件路径，以便随后进行展示
     * @param featureSelectAlgorithm：前端选择的提取特征的算法
     * @throws Exception
     */
    public void showFeature(String trainFilePath, String trainDelete, String featureTxtPath, String featureSelectAlgorithm) throws Exception {
        String selectFeatures;
        try {
            //下面是调用算法得到一个训练集的剩下的特征。 字符串。
            selectFeatures = classifyFeatures.getClassifyFeature(trainFilePath, featureSelectAlgorithm);
            //将选择的特征存入文本文件.txt：一个训练集对应一个自己的文件名+Feature.txt文件。存在quic/model中。
            algorithmUtil.saveFeatures(selectFeatures, featureTxtPath);
            //对csv训练集保留选择的特征，删除多余的特征，并存储为.arff文件。一个训练集对应一个自己的文件名+Feature.arff文件。
            arffUtil.delete(trainFilePath, selectFeatures, trainDelete);
            System.out.println("特征提取成功结束！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void training(Train train) throws Exception {
//        TrainingData traingData = new TrainingData();
//        //开始机器学习算法：
//        traingData.show_Model(train);
//        System.out.println("调用机器学习算法！！");//选择的得到的为 1 or 2;
//        //训练得到一个模型。model_name model_path result_path feature_path;
//        Model model_new=traingData.getModel();
//        modelService.insert_model(model_new);
//        System.out.println("成功将模型信息插入数据库！！！");
//    }


}
