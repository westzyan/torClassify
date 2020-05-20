package com.tor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tor.pojo.Packet;
import com.tor.pojo.Train;
import com.tor.result.Const;
import com.tor.service.FeatureService;
import com.tor.service.PacketService;
import com.tor.utils.AlgorithmUtil;
import com.tor.utils.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;
import java.util.List;

//选择机器学习算法，选择训练集，进行训练，得到模型，存入数据库。info.txt和.model两个文件进行展示。
@Controller
@Slf4j
@RequestMapping("/train")
public class TrainController {

    AlgorithmUtil algorithmUtil = new AlgorithmUtil();
    @Autowired
    private FeatureService featureService;
    @Autowired
    private PacketService packetService;
    private Train train = new Train();

    @RequestMapping(method = RequestMethod.GET)
    public String findAll(ModelMap map, @RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn) {
        //写一个函数将fileInforList分成两个List，才能完成分页功能
        List<Packet> PacketList = new LinkedList<>();
        PageHelper.startPage(pn, 6);
        PacketList = packetService.findAllPacket();
        map.addAttribute("data", PacketList);
        PageInfo<Packet> page = new PageInfo<>(PacketList);
        map.addAttribute("page", page);
        return Const.TRAIN_PAGE;
    }

    @RequestMapping(value = "/process")
    public String feature(@RequestParam("trainFile") String trainFileName, @RequestParam("algorithm") String algorithm, ModelMap map) throws Exception {
        //使用.csv文件作为训练输入文件
        String trainFileNameReplace = trainFileName.replace(".pcap", ".csv");
        train.setClassifyAlgorithm(algorithm);
        train.setTrainFileName(trainFileName);
        Packet packet = packetService.findExactPacketByName(trainFileName);
        train.setTrainFilePath(packet.getCsvPath());

        String arffFilePath = PropertiesUtil.getArff() + trainFileNameReplace.replace(".csv", "") + ".arff";
        train.setArffFilePath(arffFilePath);

        String modelInfo = PropertiesUtil.getModelInfo() + trainFileNameReplace.replace(".csv", "") + algorithm + "Info" + ".txt";
        train.setModelInfo(modelInfo);

        String modelPath = PropertiesUtil.getModel() + trainFileNameReplace.replace(".csv", "") + algorithm + ".model";
        train.setModelPath(modelPath);

        String modelname = trainFileNameReplace.replace(".csv", "") + algorithm + ".model";
        train.setModelName(modelname);

        System.out.println("trainFileName " + trainFileName);
        System.out.println("algorithm " + algorithm);
        System.out.println("arffFilePath " + arffFilePath);
        System.out.println("modelInfo " + modelInfo);
        System.out.println("modelPath " + modelPath);
        System.out.println("modelname " + modelname);


        /*
            trainFileName ISCX_tor1.pcap
            algorithm RandomForest
            arffFilePath /Users/dramatic/downloads/data/arff/ISCX_tor1.arff
            modelInfo /Users/dramatic/downloads/data/result/model_info/ISCX_tor1RandomForestInfo.txt
            modelPath /Users/dramatic/downloads/data/model/ISCX_tor1RandomForest.model
            modelname ISCX_tor1RandomForest.model
         */
        featureService.training(train);//进行机器学习，训练集训练出模型。
        String info = algorithmUtil.readModelInfo(train.getModelInfo());
        map.addAttribute("info", info);
        return Const.SHOW_RESULT_PAGE;
    }

}
