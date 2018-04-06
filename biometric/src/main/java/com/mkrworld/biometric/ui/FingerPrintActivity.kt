package com.mkrworld.biometric.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.view.ContextThemeWrapper
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import java.io.StringWriter
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import javax.xml.transform.TransformerConfigurationException
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import com.mkrworld.biometric.controller.FingerCapture
import com.mkrworld.biometric.data.PIDBean
import com.mkrworld.biometric.b
import com.mkrworld.biometric.e


/**
 * Created by mkr on 6/4/18.
 */

class FingerPrintActivity : Activity() {

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        startScanningProcess()
    }

    override fun onNewIntent(intent : Intent?) {
        super.onNewIntent(intent)
        startScanningProcess()
    }

    private fun startScanningProcess() {
        Toast.makeText(this, "startScanningProcess 1 : ", Toast.LENGTH_SHORT).show()
        val localIntent = Intent("in.gov.uidai.rdservice.fp.CAPTURE")
        localIntent.flags = 67108864 !!
        val localList = packageManager.queryIntentActivities(localIntent, 0)
        Toast.makeText(this, "startScanningProcess 1 : Size : "+localList.size, Toast.LENGTH_SHORT).show()
        if (localList.size > 0) {
            try {
                val str = b()
                if (b.b) {
                    Log.d("fingerlibrary", str)
                }
                Toast.makeText(this, "startScanningProcess 2 : PID_OPTION"+str, Toast.LENGTH_SHORT).show()
                localIntent.putExtra("PID_OPTIONS", str)
            } catch (localException : Exception) {
                localException.printStackTrace()
                Toast.makeText(this, "startScanningProcess 2 : ERROR : "+localException.message, Toast.LENGTH_SHORT).show()
            }
            startActivityForResult(localIntent, 1001)
        } else {
            d()
        }
    }

    @SuppressLint("RestrictedApi")
    private fun d() {
        val localContextThemeWrapper : ContextThemeWrapper = ContextThemeWrapper(this, 16973941 !!)
        AlertDialog.Builder(localContextThemeWrapper).setTitle("Download Required").setMessage("Biometric Device application not installed. \n\nKindly download from Play Store to proceed.").setPositiveButton("Download", DialogInterface.OnClickListener { paramAnonymousDialogInterface, paramAnonymousInt ->
            paramAnonymousDialogInterface.dismiss()
            val localUri = Uri.parse("market://details?id=" + c())
            val localIntent = Intent("android.intent.action.VIEW", localUri)
            localIntent.addFlags(1208483840 !!)
            try {
                startActivity(localIntent)
            } catch (localActivityNotFoundException : ActivityNotFoundException) {
                startActivity(Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())))
            }

            finish()
        }).setNegativeButton("Cancel", DialogInterface.OnClickListener { paramAnonymousDialogInterface, paramAnonymousInt ->
            paramAnonymousDialogInterface.dismiss()
            finish()
        }).setCancelable(true).show()
    }

    fun c() : String {
        return "com.mantra.rdservice";
    }

    fun b() : String {
        try {
            val localDocumentBuilderFactory = DocumentBuilderFactory.newInstance()
            localDocumentBuilderFactory.setNamespaceAware(true)
            val localDocumentBuilder = localDocumentBuilderFactory.newDocumentBuilder()
            val localDocument = localDocumentBuilder.newDocument()
            localDocument.setXmlStandalone(true)
            val localElement1 = localDocument.createElement("PidOptions")
            localDocument.appendChild(localElement1)
            var localAttr = localDocument.createAttribute("ver")
            localAttr.setValue("1.0")
            localElement1.setAttributeNode(localAttr)
            val localElement2 = localDocument.createElement("Opts")
            localElement1.appendChild(localElement2)
            localAttr = localDocument.createAttribute("fCount")
            localAttr.setValue(1.toString())
            localElement2.setAttributeNode(localAttr)
            localAttr = localDocument.createAttribute("format")
            localAttr.setValue("0")
            localElement2.setAttributeNode(localAttr)
            localAttr = localDocument.createAttribute("pidVer")
            localAttr.setValue(FingerCapture.getInstance().version)
            localElement2.setAttributeNode(localAttr)
            localAttr = localDocument.createAttribute("timeout")
            localAttr.setValue("15000")
            localElement2.setAttributeNode(localAttr)
            localAttr = localDocument.createAttribute("env")
            localAttr.setValue(FingerCapture.getInstance().environment)
            localElement2.setAttributeNode(localAttr)
            if (FingerCapture.getInstance().isKYC) {
                localAttr = localDocument.createAttribute("wadh")
                localAttr.setValue(FingerCapture.getInstance().wadh)
                localElement2.setAttributeNode(localAttr)
            }
            val localElement3 = localDocument.createElement("Demo")
            localElement3.setTextContent("")
            localElement1.appendChild(localElement3)
            val localElement4 = localDocument.createElement("CustOpts")
            localElement4.setTextContent("")
            localElement1.appendChild(localElement4)
            val localTransformerFactory = TransformerFactory.newInstance()
            val localTransformer = localTransformerFactory.newTransformer()
            localTransformer.setOutputProperty("omit-xml-declaration", "yes")
            val localDOMSource = DOMSource(localDocument)
            val localStringWriter = StringWriter()
            val localStreamResult = StreamResult(localStringWriter)
            localTransformer.transform(localDOMSource, localStreamResult)
            var str = localStringWriter.getBuffer().toString().replace("\n|\r".toRegex(), "")
            str = str.replace("&lt;".toRegex(), "<").replace("&gt;".toRegex(), ">")
            return str
        } catch (localParserConfigurationException : ParserConfigurationException) {
            Toast.makeText(this, "b ParserConfigurationException : "+localParserConfigurationException, Toast.LENGTH_LONG).show()
            return "ERROR :- " + localParserConfigurationException.message
        } catch (localTransformerConfigurationException : TransformerConfigurationException) {
            Toast.makeText(this, "b TransformerConfigurationException : "+localTransformerConfigurationException, Toast.LENGTH_LONG).show()
            return "ERROR :- " + localTransformerConfigurationException.message
        } catch (localTransformerException : TransformerException) {
            Toast.makeText(this, "b TransformerException : "+localTransformerException, Toast.LENGTH_LONG).show()
            return "ERROR :- " + localTransformerException.message
        }
    }

    override fun onActivityResult(paramInt1 : Int, paramInt2 : Int, paramIntent : Intent?) {
        if (paramInt2 != - 1) {
            a(null, 3, "Please capture again.")
            return
        }
        if (paramInt1 == 1001 && paramIntent != null) {
            val localBundle = paramIntent.extras
            if (localBundle != null) {
                val str2 = localBundle.getString("DNR", "")
                if (! TextUtils.isEmpty(str2)) {
                    val localIntent = Intent("android.intent.action.SCL_RDSERVICE_OTP_RECIEVER")
                    localIntent.putExtra("OTP", "SCL-1234-1234-123")
                    localIntent.`package` = "com.scl.rdservice"
                    try {
                        sendBroadcast(localIntent)
                    } catch (localException : Exception) {
                    }
                }
                val str1 = localBundle.getString("PID_DATA")
                if (b.b) {
//                    d.a("fingerlibrary", str1, this.a)
                }
                a(e().a(str1))
            } else {
                a(null, 3, "Please capture again.")
                return
            }
        } else {
            a(null, 3, "Please capture again.")
            return
        }
    }

    fun a(paramHashMap : HashMap<String, String>?) {
        if (paramHashMap == null || paramHashMap.size === 0) {
            a(null, 3, "Please capture again.")
        } else if (paramHashMap["errCode"] as String == "0") {
            a(e().a(paramHashMap), 0, "100")
        } else {
            a(null, 3, paramHashMap["errCode"] as String + " : " + paramHashMap["errInfo"] as String)
        }
    }

    fun a(paramPIDBean : PIDBean?, paramInt : Int, paramString : String) {
        runOnUiThread {
            if (FingerCapture.getInstance().mCallBackInterface != null) {
                if (paramPIDBean != null) {
//                    d.a("fingerlibrary", paramPIDBean.toString(), true)
                }
                FingerCapture.getInstance().mCallBackInterface!!.pidData(paramPIDBean!!, paramInt, "Device : "/* + c.a.name() */+ "\n" + paramString)
            }
        }
        finish()
    }
}
