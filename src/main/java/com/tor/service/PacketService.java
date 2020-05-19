package com.tor.service;

import com.tor.dao.PacketDao;
import com.tor.pojo.Packet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacketService {
    @Autowired
    private PacketDao packetDao;

    public int insertPacket(Packet packet) {
        return packetDao.insertPacket(packet);
    }

    public List<Packet> findAllPacket() {
        return packetDao.findAllPacket();
    }

    public List<Packet> findAllPacketDesc() {
        return packetDao.findAllPacketDesc();
    }

    public int deletePacket(Integer id) {
        return packetDao.deletePacket(id);
    }

    public List<Packet> findPacketByName(String packetName) {
        return packetDao.findPacketByName(packetName);
    }

    public Packet findExactPacketByName(String packetName) {
        return packetDao.findExactPacketByName(packetName);
    }

    public List<Packet> findPacketByType(String type) {
        return packetDao.findPacketByType(type);
    }
}