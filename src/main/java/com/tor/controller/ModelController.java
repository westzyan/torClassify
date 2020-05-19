package com.tor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tor.pojo.Model;
import com.tor.result.Const;
import com.tor.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/model")
public class ModelController {

    @Autowired
    private ModelService modelService;

    @RequestMapping(value = "/findModelByName", method = RequestMethod.POST)
    public String findModelByName(@RequestParam("modelName") String modelName, ModelMap map) {
        List<Model> resList = modelService.findModelByName(modelName);
        if (resList == null) {
            return Const.MODEL_PAGE;
        } else {
            PageInfo<Model> pageList = new PageInfo<>(resList);
            map.addAttribute("data", resList);
            map.addAttribute("page", pageList);
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


    //两个更新函数一起，完成了根据模型名字更新模型其他信息的任务。点击提交后最终返回database页面。
    //更新文件
    @RequestMapping(value = "/updateModel", method = RequestMethod.POST)
    public String updateModel(@ModelAttribute Model model) {
        modelService.updateModel(model);
        return Const.MODEL_PAGE;
    }

    //根据文件名更新文件
    @RequestMapping(value = "/updateModel/{modelName}", method = RequestMethod.GET)
    public String getByMp(@PathVariable String modelName, ModelMap map) {
        List<Model> modelList = modelService.findModelByName(modelName);
        if (modelList == null) {
            return Const.MODEL_PAGE;
            //传值未成功或者数据库中无此条信息
        } else {
            map.addAttribute("model", modelList);//根据名字找到的model对象值 放在model中。
            map.addAttribute("action", "updateModel");//调用update_model函数 将更新的feature_path写入其中。
            return "DataForm";
        }
    }

    //新增文件信息
    @RequestMapping(value = "/addModel", method = RequestMethod.GET)
    public String createModel(ModelMap map) {
        map.addAttribute("model", new Model());
        map.addAttribute("action", "addModel");
        return "DataForm";
    }

    @RequestMapping(value = "/addModel", method = RequestMethod.POST)
    public String insertModel(@ModelAttribute Model model, ModelMap map) {
        String modelName = model.getModelName();
        String modelPath = model.getModelPath();
        if (modelName == null || modelPath == null) {
            System.out.println("新增模型名字和路径不能为空");
            return Const.MODEL_PAGE;
        } else {
            if (modelService.findExactModelByName(modelName) != null) {
                //如果已经存在此模型名字，则无法新建模型信息；
                return Const.MODEL_PAGE;
            } else {
                List<Model> modelList = modelService.findAllModelDesc();
                PageInfo<Model> pageList = new PageInfo<>(modelList);
                map.addAttribute("data", modelList);
                map.addAttribute("page", pageList);
                return Const.MODEL_PAGE;
            }
        }
    }
}
