package com.tor.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tor.pojo.Packet;
import com.tor.result.Const;
import com.tor.service.PacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/packet")
public class PacketController {
    @Autowired
    private PacketService packetService;

    @RequestMapping(value = "/findAllPacket", method = RequestMethod.POST)
    public String findAllPacket(ModelMap map) {
        List<Packet> res = packetService.findAllPacket();
        if (res == null) {
            return Const.PACKET_PAGE;
        } else {
            List<Packet> packetList = packetService.findAllPacket();
            PageInfo<Packet> pageList = new PageInfo<>(packetList);
            map.addAttribute("data", packetList);
            map.addAttribute("page", pageList);
            return Const.PACKET_PAGE;
        }
    }

    //根据名字对数据包进行模糊查询
    @RequestMapping(value = "/findPacketByName", method = RequestMethod.POST)
    public String findPacketByName(@RequestParam("packetName") String packetName, ModelMap map) {
        List<Packet> res = packetService.findPacketByName(packetName);
        if (res == null) {
            return Const.PACKET_PAGE;
        } else {
            List<Packet> packetList = res;
            PageInfo<Packet> pageList = new PageInfo<>(packetList);
            map.addAttribute("data", packetList);
            map.addAttribute("page", pageList);
            return Const.PACKET_PAGE;
        }
    }

    //根据类型对数据包进行模糊查询
    @RequestMapping(value = "/findPacketByType", method = RequestMethod.POST)
    public String findPacketByType(@RequestParam("type") String type, ModelMap map) {
        List<Packet> res = packetService.findPacketByType(type);
        if (res == null) {
            return Const.PACKET_PAGE;
        } else {
            List<Packet> packetList = res;
            PageInfo<Packet> pageList = new PageInfo<>(packetList);
            map.addAttribute("data", packetList);
            map.addAttribute("page", pageList);
            return Const.PACKET_PAGE;
        }
    }

    //删除文件
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deletePacket(@PathVariable Integer id, ModelMap map) {
        packetService.deletePacket(id);
        List<Packet> resList = packetService.findAllPacket();
        PageInfo<Packet> pageList = new PageInfo<>(resList);
        map.addAttribute("data", resList);
        map.addAttribute("page", pageList);
        return Const.PACKET_PAGE;
    }

    //分页对数据包进行查询
    @RequestMapping(method = RequestMethod.GET)
    public String getUserList(ModelMap map, @RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn) {
        PageHelper.startPage(pn, 6);
        List<Packet> packetList = packetService.findAllPacket();
        PageInfo<Packet> pageList = new PageInfo<>(packetList);
        map.addAttribute("data", packetList);
        map.addAttribute("page", pageList);
        return Const.PACKET_PAGE;
    }


//    //更新文件
//    @RequestMapping(value = "/update", method = RequestMethod.POST)
//    public String update(@ModelAttribute User user) {
//        packetService.();
//        return REDIRECT_TO_USER_LIST;
//    }
//
//    //根据文件名更新文件
//    @RequestMapping(value = "/update/{filename}", method = RequestMethod.GET)
//    public String getByFn(@PathVariable String filename, ModelMap map) {
//        if (userService.selectByFT(filename) == null) {
//            return REDIRECT_TO_USER_LIST;
//            //传值未成功或者数据库中无此条信息
//        } else {
//            map.addAttribute("home", userService.selectByFT(filename));
//            map.addAttribute("action", "update");
//            return USER_FORM;
//        }
//    }

//    //新增文件信息
//    @RequestMapping(value = "/insertPacket", method = RequestMethod.GET)
//    public String insertPacket(ModelMap map) {
//        map.addAttribute("home", new User());
//        map.addAttribute("action", "adduser");
//        return "UserForm2";
//    }

//    @RequestMapping(value = "/insertPacket", method = RequestMethod.POST)
//    public String postFileInfor(@ModelAttribute Packet packet) {
//        String packetName = packet.getPacketName();
//        String packetPath = packet.getPacketPath();
//        Integer type = packet.getType();
//        String csvPath = packet.getCsvPath();
//        if (packetName == null || packetPath == null || type == null || csvPath.contains("/") || n == "" || p == "") {
//            System.out.println("创建信息时不能为空或者文件名不能包含字符“/”");
//            return REDIRECT_TO_USER_LIST;
//        } else {
//            if (packetService.findExactPacketByName(packetName) != null) {
//                //如果已经存在此文件名，则无法新建文件信息
//                return REDIRECT_TO_USER_LIST;
//            } else {
//                packetService.insertPacket(packet);
//                return REDIRECT_TO_USER_LIST;
//            }
//        }
//    }
}
