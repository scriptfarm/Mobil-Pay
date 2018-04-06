package com.mkrworld.biometric

import com.mkrworld.biometric.data.PIDBean
import org.w3c.dom.Document
import org.w3c.dom.NamedNodeMap
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import java.io.StringReader
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.collections.HashMap


/**
 * Created by mkr on 6/4/18.
 */
class e{
    private fun b(paramHashMap : HashMap<String, String>) : String {
        val str1 = paramHashMap["dpId"] as String
        val str2 = paramHashMap["mi"] as String
        val str3 = paramHashMap["srno"] as String
        val localStringBuilder = StringBuilder()
        if (str1 != null) {
            if (str1.length >= 5) {
                localStringBuilder.append(str1.substring(0, 5))
            } else {
                localStringBuilder.append(str1)
            }
        }
        if (str2 != null) {
            if (str2.length >= 5) {
                localStringBuilder.append(str2.substring(0, 5))
            } else {
                localStringBuilder.append(str2)
            }
        }
        if (str3 != null) {
            if (str3.length >= 10) {
                localStringBuilder.append(str3.substring(0, 10))
            } else {
                localStringBuilder.append(str3)
            }
        }
        return if (localStringBuilder == null || localStringBuilder.toString() == "") {
            "STARFM220787878989"
        } else localStringBuilder.toString()
    }

    fun a(paramString : String) : HashMap<String, String> {
        val localHashMap : HashMap<String, String> = HashMap()
        try {
            val localDocument = c(paramString) ?: return localHashMap
            var localNode = localDocument.getElementsByTagName("PidData").item(0)
            val localNodeList1 = localNode.getChildNodes()
            for (i in 0 until localNodeList1.getLength()) {
                localNode = localNodeList1.item(i)
                val str1 = localNodeList1.item(i).getNodeName()
                var localNamedNodeMap1 : NamedNodeMap
                var j : Int
                var localObject : Any
                if (str1.equals("Resp", ignoreCase = true)) {
                    localNamedNodeMap1 = localNode.getAttributes()
                    j = 0
                    while (j < localNamedNodeMap1.getLength()) {
                        localObject = localNamedNodeMap1.item(j).getNodeName()
                        if ((localObject as String).equals("errCode", ignoreCase = true)) {
                            localNamedNodeMap1.item(j).getNodeValue().toString()
                            localHashMap.put("errCode", localNamedNodeMap1.item(j).getNodeValue().toString())
                        } else if (localObject.equals("errInfo", ignoreCase = true)) {
                            localNamedNodeMap1.item(j).getNodeValue().toString()
                            localHashMap.put("errInfo", localNamedNodeMap1.item(j).getNodeValue().toString())
                        }
                        j ++
                    }
                }
                if (str1.equals("DeviceInfo", ignoreCase = true)) {
                    localNamedNodeMap1 = localNode.getAttributes()
                    j = 0
                    while (j < localNamedNodeMap1.getLength()) {
                        localObject = localNamedNodeMap1.item(j).getNodeName()
                        if ((localObject as String).equals("dc", ignoreCase = true)) {
                            localHashMap.put("dc", localNamedNodeMap1.item(j).getNodeValue().toString())
                        } else if (localObject.equals("dpId", ignoreCase = true)) {
                            localHashMap.put("dpId", localNamedNodeMap1.item(j).getNodeValue().toString())
                        } else if (localObject.equals("rdsId", ignoreCase = true)) {
                            localHashMap.put("rdsId", localNamedNodeMap1.item(j).getNodeValue().toString())
                        } else if (localObject.equals("rdsVer", ignoreCase = true)) {
                            localHashMap.put("rdsVer", localNamedNodeMap1.item(j).getNodeValue().toString())
                        } else if (localObject.equals("mi", ignoreCase = true)) {
                            localHashMap.put("mi", localNamedNodeMap1.item(j).getNodeValue().toString())
                        } else if (localObject.equals("mc", ignoreCase = true)) {
                            localHashMap.put("mc", localNamedNodeMap1.item(j).getNodeValue().toString())
                        }
                        j ++
                    }
                    localObject = localNodeList1.item(i).getChildNodes()
                    j = 0
                    while (j < (localObject as NodeList).getLength()) {
                        val str2 = (localObject as NodeList).item(j).getNodeName()
                        if (str2.equals("additional_info", ignoreCase = true)) {
                            val localNodeList2 = (localObject as NodeList).item(j).getChildNodes()
                            for (k in 0 until localNodeList2.getLength()) {
                                if (localNodeList2.item(k).getNodeName().equals("Param")) {
                                    val localNamedNodeMap2 = localNodeList2.item(k).getAttributes()
                                    if (localNamedNodeMap2.getNamedItem("name").getNodeValue().equals("srno")) {
                                        localHashMap.put("srno", localNamedNodeMap2.getNamedItem("value").getNodeValue())
                                    }
                                }
                            }
                        }
                        j ++
                    }
                } else if (str1.equals("Skey", ignoreCase = true)) {
                    localHashMap.put("Skey", localNode.getTextContent())
                    localHashMap.put("ci", localNode.getAttributes().getNamedItem("ci").getNodeValue().toUpperCase())
                } else if (str1.equals("Hmac", ignoreCase = true)) {
                    localHashMap.put("Hmac", localNode.getTextContent())
                } else if (str1.equals("Data", ignoreCase = true)) {
                    localHashMap.put("Data", localNode.getTextContent())
                }
            }
        } catch (localException : Exception) {
            return localHashMap
        }

        return localHashMap
    }

    fun b(paramString : String) : Boolean {
        try {
            val localDocument = c(paramString) ?: return false
            val localNode = localDocument.getElementsByTagName("DeviceInfo").item(0)
            val localNodeList1 = localNode.getChildNodes()
            for (i in 0 until localNodeList1.getLength()) {
                val str = localNodeList1.item(i).getNodeName()
                if (str.equals("additional_info", ignoreCase = true)) {
                    val localNodeList2 = localNodeList1.item(i).getChildNodes()
                    for (j in 0 until localNodeList2.getLength()) {
                        if (localNodeList2.item(j).getNodeName().equals("Param")) {
                            val localNamedNodeMap = localNodeList2.item(j).getAttributes()
                            if (localNamedNodeMap.getNamedItem("name").getNodeValue().equals("pass")) {
                                if (localNamedNodeMap.getNamedItem("value").getNodeValue().equals("Y",true)) {
//                                    d.a("fingerlibrary", "password protection yes", true)
                                    return true
                                }
//                                d.a("fingerlibrary", "password protection no", true)
                                return false
                            }
                        }
                    }
                }
            }
        } catch (localException : Exception) {
            return false
        }

        return false
    }

    fun a(paramHashMap : HashMap<String, String>) : PIDBean {
        val localPIDBean = PIDBean()
        localPIDBean.serviceType = "2"
        localPIDBean.hmac = paramHashMap["Hmac"] as String
        localPIDBean.skey = paramHashMap["Skey"] as String
        localPIDBean.pid = paramHashMap["Data"] as String
        localPIDBean.udc = b(paramHashMap)
        localPIDBean.ci = paramHashMap["ci"] as String
        localPIDBean.registeredDeviceModelID = paramHashMap["mi"] as String
        localPIDBean.registeredDeviceProviderCode = paramHashMap["dpId"] as String
        localPIDBean.registeredDevicePublicKeyCertificate = paramHashMap["mc"] as String
        localPIDBean.registeredDeviceServiceID = paramHashMap["rdsId"] as String
        localPIDBean.registeredDeviceServiceVersion = paramHashMap["rdsVer"] as String
        localPIDBean.registeredDeviceCode = paramHashMap["dc"] as String
        val localSimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val l = System.currentTimeMillis()
        val localDate = Date(l)
        localPIDBean.pidTs = localSimpleDateFormat.format(localDate).replace(" ", "T")
        return localPIDBean
    }

    private fun c(paramString : String) : Document? {
        try {
            val localDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            val localInputSource = InputSource()
            localInputSource.setCharacterStream(StringReader(paramString))
            return localDocumentBuilder.parse(localInputSource)
        } catch (localException : Exception) {

        }

        return null
    }
}