package com.tor.dao;

import com.tor.pojo.Packet;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
@Component("PacketDao")
public interface PacketDao {

    @Insert("insert into packet (packetName,packetPath,type) values(#{packetName},#{packetPath},#{type})")
    int insertPacket(Packet packet);

    @Select("select * from packet")
    List<Packet> findAllPacket();

    @Delete("delete from packet where id=#{id}")
    int deletePacket(Integer id);

    @Select("SELECT * FROM packet WHERE packetName like '%${value}%' OR type like '%${value}%'")
    List<Packet> findPacketByName(String packetName);

    @Select("SELECT * FROM packet WHERE packetName=#{packetName}")
    Packet findExactPacketByName(String packetName);

    @Select("SELECT * FROM packet WHERE type=#{type}")
    List<Packet> findPacketByType(String type);
}

