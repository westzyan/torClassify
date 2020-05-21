package com.tor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tor.pojo.Flow;
import com.tor.pojo.Model;
import com.tor.pojo.Packet;
import com.tor.result.CodeMsg;
import com.tor.result.Const;
import com.tor.result.Result;
import com.tor.service.ModelService;
import com.tor.service.PacketService;
import com.tor.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    TestService testService;
    @Autowired
    private PacketService packetService;
    private Packet packet = new Packet();
    @Autowired
    private ModelService modelService;
    private Model model = new Model();

    @RequestMapping(method = RequestMethod.GET)
    public String findAll(ModelMap map, @RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn, @RequestParam(required = false, defaultValue = "1", value = "pn1") Integer pn1) {
        List<Model> modelList = new LinkedList<>();
        List<Packet> packetList = new LinkedList<>();

        PageHelper.startPage(pn1, 6);
        modelList = modelService.findAllModel();
        map.addAttribute("modelList", modelList);
        PageInfo<Model> modelPage = new PageInfo<>(modelList);
        map.addAttribute("modelPage", modelPage);

        PageHelper.startPage(pn, 6);
        packetList = packetService.findAllPacket();
        map.addAttribute("packetList", packetList);
        PageInfo<Packet> packetPage = new PageInfo<>(packetList);
        map.addAttribute("packetPage", packetPage);
        return Const.TEST_PAGE;
    }

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public String test(@RequestParam("testFile") String testFileName, @RequestParam("modelName") String modelname, ModelMap modelMap) throws Exception {
        packet = packetService.findExactPacketByName(testFileName);
        model = modelService.findExactModelByName(modelname);
        if (model == null || packet == null) {
            modelMap.addAttribute("result", Result.error(CodeMsg.NULL_DATA));
            return Const.TEST_PAGE;
        } else {
            String testPath = packet.getPacketPath().replace(".pcap", ".csv");//.csv
            String testname = packet.getPacketName();
            String modelPath = model.getModelPath();//.model
            String featurePath = model.getFeaturePath();//Feature.txt
            //调用测试算法，得到一个表，表示测试结果。
            List<Flow> resultList = testService.getModelClassifyList(testname, testPath, modelPath, featurePath);
            modelMap.addAttribute("resultList", resultList);
            return Const.TEST_RESULT_PAGE;
        }
    }

}
