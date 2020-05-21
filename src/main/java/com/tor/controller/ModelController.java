package com.tor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tor.pojo.Model;
import com.tor.result.CodeMsg;
import com.tor.result.Const;
import com.tor.result.Result;
import com.tor.service.ModelService;
import com.tor.utils.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Controller
@Slf4j
@RequestMapping(value = "/model")
public class ModelController {

    @Autowired
    private ModelService modelService;

    @RequestMapping(value = "/findModelByName", method = RequestMethod.POST)
    public String findModelByName(@RequestParam("modelName") String modelName, ModelMap modelMap) {
        List<Model> resList = modelService.findModelByName(modelName);
        if (resList == null) {
            modelMap.addAttribute("result", Result.error(CodeMsg.NULL_DATA));
            return Const.MODEL_PAGE;
        } else {
            PageInfo<Model> pageList = new PageInfo<>(resList);
            modelMap.addAttribute("data", resList);
            modelMap.addAttribute("page", pageList);
            return Const.MODEL_PAGE;
        }
    }

    //删除模型
    @RequestMapping(value = "/deleteModel/{id}", method = RequestMethod.GET)
    public String deleteModel(@PathVariable Integer id, ModelMap map) {
        modelService.deleteModel(id);
        List<Model> resList = modelService.findAllModel();
        PageInfo<Model> pageList = new PageInfo<>(resList);
        map.addAttribute("data", resList);
        map.addAttribute("page", pageList);
        return Const.MODEL_PAGE;
    }

    //分页查询模型
    @RequestMapping(method = RequestMethod.GET)
    public String findAllModel(ModelMap map, @RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn) {
        PageHelper.startPage(pn, 6);
        List<Model> modelList = modelService.findAllModel();
        PageInfo<Model> pageList = new PageInfo<>(modelList);
        map.addAttribute("data", modelList);
        map.addAttribute("page", pageList);
        return Const.MODEL_PAGE;
    }


    @RequestMapping(value = "/addModel")
    public String addModel(ModelMap modelMap, @RequestParam("file") MultipartFile file) throws Exception {
        try {
            if (file.isEmpty()) {
                modelMap.addAttribute("result", Result.error(CodeMsg.NULL_DATA));
                return Const.MODEL_PAGE;
            }
            String fileModelName = file.getOriginalFilename();
            String suffixName = fileModelName.substring(fileModelName.lastIndexOf("."));
            if (!".model".equals(suffixName)) {
                modelMap.addAttribute("result", Result.error(CodeMsg.INVIVAD_FILE));
                return Const.MODEL_PAGE;
            }
            //path为要保存的model地址拼接原始fileName
            String fullModelName = PropertiesUtil.getModel() + fileModelName;
            File fullModelFile = new File(fullModelName);
            //检测是否存在目标
            if (!fullModelFile.getParentFile().exists()) {
                fullModelFile.getParentFile().mkdirs();
            }

            if (!fullModelFile.exists()) {
                Model model = new Model();
                model.setModelName(fileModelName);
                model.setModelPath(fullModelName);
                model.setModelInfo(PropertiesUtil.getModelInfo() + fileModelName);
                modelService.insertModel(model);
                file.transferTo(fullModelFile);
            } else {

            }

        } catch (Exception e) {
            log.error(e.toString());
        }
        //加入模型之后，显示现有模型
        List<Model> modelList = modelService.findAllModelDesc();
        PageInfo<Model> pageList = new PageInfo<>(modelList);
        modelMap.addAttribute("data", modelList);
        modelMap.addAttribute("page", pageList);
        return Const.MODEL_PAGE;
    }
}
