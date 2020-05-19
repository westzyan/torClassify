package com.tor.utils;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    private static Properties props;

    static {
        try {
            InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("filepath.properties");
            props = new Properties();
            props.load(in);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getPcapPath() {
        return props.getProperty("raw_pcap_path");
    }

    public static String getPcapCsvPath() {
        return props.getProperty("pcap_csv_path");
    }

    public static String getPython() {
        return props.getProperty("python");
    }

    public static String getMakeLabelPy() {
        return props.getProperty("makeLabelPy");
    }

    public static String getArff() {
        return props.getProperty("arff_path");
    }

    public static String getFeature() {
        return props.getProperty("feature_path");
    }
}
