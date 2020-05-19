package com.tor.classify;

import weka.attributeSelection.*;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.File;
import java.util.Arrays;

public class ClassifyFeatures {
    ArffUtil arffUtil = new ArffUtil();

    public static void main(String[] args) {
        ClassifyFeatures c = new ClassifyFeatures();
        System.out.println(c.getClassifyFeature("/Users/dramatic/downloads/data/pcap_csv/ISCX_tor3.pcap.csv", "CfsSubsetEval+BestFirst"));
    }

    /**
     * @param trainFilePath:训练集路径
     * @param featureSelectAlgorithm：特征选择算法（1:CfsSubsetEval+BestFirst/2:Infogain+Ranker）
     * @return： 特征选择得到的结果
     */
    public String getClassifyFeature(String trainFilePath, String featureSelectAlgorithm) {
        File file = new File(trainFilePath);
        String convertFile = file.getParent() + File.separator + "trainFilecsvToArff.arff";//所有的训练集都用一个.arff文件过渡。
        arffUtil.csvToArff(trainFilePath, convertFile);//将所选的训练集转化为.arff文件，存在convertFile文件中，所有的训练集公用一个convertFile文件。
        int flag = 0;
        if (featureSelectAlgorithm.equals("CfsSubsetEval+BestFirst")) {
            flag = 1;
        } else if (featureSelectAlgorithm.equals("Infogain+Ranker")) {
            flag = 2;
        }
        ConverterUtils.DataSource sourceTrain = null;
        try {
            sourceTrain = new ConverterUtils.DataSource(convertFile);//读入写有全部特征的.arff文件
            Instances train = sourceTrain.getDataSet();//读入.arff数据(24个属性)。
            if (train.classIndex() == -1)//判断是否为tor notor 用于二分类的一列。
            {
                train.setClassIndex(train.numAttributes() - 1);//将最后一列的下标记为属性总数-1.因为第一个属性的下标为0.
            }
            //1.CfsSubsetEval+BestFirst
            //todo 有问题
            if (flag == 1) {
                AttributeSelection attsel = new AttributeSelection();
                // attribute evaluator:CfsSubsetEval
                CfsSubsetEval eval = new CfsSubsetEval();
                // search method:BestFirst
                BestFirst search = new BestFirst();
                attsel.setEvaluator(eval);
                attsel.setSearch(search);
                attsel.SelectAttributes(train);
                // 显示特征选择的结果
//                int[] attrIndex = search.search(eval, train);
                int[] attrIndex = attsel.selectedAttributes();
                System.out.println(Arrays.toString(attrIndex));

                StringBuilder attrCfsBestInfo = new StringBuilder();
                for (int i = 0; i < attrIndex.length; i++) {
                    attrCfsBestInfo.append((train.attribute(attrIndex[i]).name()));
                    attrCfsBestInfo.append(",");
                }
                if (attrCfsBestInfo.toString().length() == 0) {
                    attrCfsBestInfo.append("no feature!");
                } else {
                    attrCfsBestInfo.deleteCharAt(attrCfsBestInfo.toString().length() - 1);
                    attrCfsBestInfo.append(",label");
                }

                // 显示特征选择的结果
//			System.out.println(attrCfsBestInfo.toString());
                return attrCfsBestInfo.toString();
            }
            // 2:Infogain+Ranker
            else if (flag == 2) {
                // 特征选择
                AttributeSelection attsel = new AttributeSelection();
                // attribute evaluator:Infogain
                InfoGainAttributeEval eval = new InfoGainAttributeEval();
                // search method:Ranker
                Ranker search = new Ranker();
                attsel.setEvaluator(eval);
                attsel.setSearch(search);
                attsel.SelectAttributes(train);
                // 特征选择：从0开始计数
                int[] attrIndex = search.search(eval, train);
                StringBuilder attrInfoGainInfo = new StringBuilder();
                for (int i = 0; i < attrIndex.length; i++) {
//					System.out.println(eval.evaluateAttribute(attrIndex[i]));
                    //删除对分类作用较小的特征
                    if (i > 1) {
                        if (Math.abs(eval.evaluateAttribute(attrIndex[i]) - eval.evaluateAttribute(attrIndex[i - 1])) + 0.01 > Math.abs(eval.evaluateAttribute(attrIndex[i - 1]) - eval.evaluateAttribute(attrIndex[i - 2]))) {
                            break;
                        }
                    }
                    attrInfoGainInfo.append((train.attribute(attrIndex[i]).name()));
                    attrInfoGainInfo.append(",");
                }
                if (attrInfoGainInfo.toString().length() == 0) {
                    attrInfoGainInfo.append("no feature!");
                } else {
                    attrInfoGainInfo.deleteCharAt(attrInfoGainInfo.toString().length() - 1);
                    attrInfoGainInfo.append(",label");
                }
                // 显示特征选择的结果
//			System.out.println(attrInfoGainInfo.toString());
                return attrInfoGainInfo.toString();
            } else {
                return "false";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}