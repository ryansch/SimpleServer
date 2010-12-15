/*******************************************************************************
 * Open Source Initiative OSI - The MIT License:Licensing
 * The MIT License
 * Copyright (c) 2010 Charles Wagner Jr. (spiegalpwns@gmail.com)
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ******************************************************************************/
package simpleserver.config;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class IPBanList extends PropertiesConfig {
  public IPBanList() {
    super("ip-ban-list.txt");
  }

  public void addBan(String ipAddress) {
    setProperty(ipAddress, "");

    save();
  }

  public boolean removeBan(String ipAddress) {
    if (removeProperty(ipAddress) != null) {
      save();

      return true;
    }

    return false;
  }

  public boolean isBanned(String ipAddress) {
    String network = "";
    String[] octets = ipAddress.split("\\.");

    for (String octet : octets) {
      network += octet;

      if (getProperty(network) != null) {
        return true;
      }

      network += ".";
    }

    return false;
  }

  @Override
  public void load() {
    super.load();

    Pattern trailingDot = Pattern.compile("\\.$");
    List<String> networks = new LinkedList<String>();
    Set<Object> addresses = keySet();
    
    for (Object address : addresses) {
      addresses.remove(address);

      networks.add(trailingDot.matcher((String) address).replaceFirst(""));
    }
    
    for (String network : networks) {
      setProperty(network, "");
    }
  }
}
