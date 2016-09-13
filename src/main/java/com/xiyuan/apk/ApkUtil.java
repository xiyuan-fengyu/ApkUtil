package com.xiyuan.apk;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by xiyuan_fengyu on 2016/9/13.
 */
public class ApkUtil {

    private static final Namespace NS = Namespace.getNamespace("http://schemas.android.com/apk/res/android");

    public static ApkInfo get(String apkPath){
        ApkInfo.Builder apkInfoBuilder = new ApkInfo.Builder();
        try {
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(getXmlInputStream(apkPath));
            Element root = document.getRootElement();

            apkInfoBuilder.setVersionName(root.getAttributeValue("versionName", NS));
            apkInfoBuilder.setVersionCode(Integer.parseInt(root.getAttributeValue("versionCode", NS)));
            apkInfoBuilder.setPackageName(root.getAttributeValue("package"));

            Element elemUseSdk = root.getChild("uses-sdk");
            apkInfoBuilder.setMinSdk(elemUseSdk.getAttributeValue("minSdkVersion", NS));
            List<Element> listPermission = root.getChildren("uses-permission");
            for(Element element : listPermission){
                String permission = element.getAttributeValue("name", NS);
                apkInfoBuilder.addPermissions(permission);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return apkInfoBuilder.build();
    }

    private static InputStream getXmlInputStream(String apkPath) {
        InputStream inputStream = null;
        InputStream xmlInputStream = null;
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(apkPath);
            ZipEntry zipEntry = new ZipEntry("AndroidManifest.xml");
            inputStream = zipFile.getInputStream(zipEntry);
            MyAXMLPrinter xmlPrinter = new MyAXMLPrinter();
            xmlPrinter.startPrinf(inputStream);
            xmlInputStream = new ByteArrayInputStream(xmlPrinter.getBuf().toString().getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (zipFile != null) {
                    zipFile.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return xmlInputStream;
    }
}
