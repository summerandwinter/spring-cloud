package io.summer.eurekaproducer.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author summer
 */
public class DeviceUtil {

  public static List<String> getLocalAddress() {
    List<String> ipList = new ArrayList<String>();
    InetAddress ip = null;
    try {
      Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface
          .getNetworkInterfaces();
      while (netInterfaces.hasMoreElements()) {
        NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
        Enumeration<InetAddress> ips = ni.getInetAddresses();
        while (ips.hasMoreElements()) {
          ip = (InetAddress) ips.nextElement();
         if(ip.isSiteLocalAddress()) {
           ipList.add(ip.getHostAddress());
           System.out.println(ip.getHostAddress());
         }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ipList;
  }
  public static void main(String[] args) {
    getLocalAddress();
  }

}
