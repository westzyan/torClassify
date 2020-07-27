package com.tor.util;

import iscx.cs.unb.ca.ifm.ISCXFlowMeter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test {
    public static void main(String[] args) {
        String fullFile = "/home/zyan/AW/show/data/pcap/localPcap_2020-07-27+18-34-10.pcap";
        if (ISCXFlowMeter.singlePcap(fullFile, PropertiesUtil.getPcapCsvPath())) {
            log.info("grabPackets：数据包转换成功");
        } else {
            log.info("grabPackets：数据包转换失败");
        }
    }
}
