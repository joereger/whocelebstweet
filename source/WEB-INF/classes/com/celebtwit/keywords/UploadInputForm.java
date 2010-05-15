package com.celebtwit.keywords;

import com.celebtwit.util.Str;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: joereger
 * Date: May 15, 2010
 * Time: 4:27:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class UploadInputForm {


    public static String getForm(ArrayList<UploadedKeyword> uploadedKeywords){
        StringBuffer out = new StringBuffer();
        int counter = 0;
        for (Iterator<UploadedKeyword> ukIt = uploadedKeywords.iterator(); ukIt.hasNext();) {
            UploadedKeyword uploadedKeyword = ukIt.next();
            counter++;
            String color = "#e6e6e6";
            if (!uploadedKeyword.getIsvalid()){ color = "#00ff00"; }
            out.append("<tr>");
            out.append("<td valign='top'>");
            out.append("<input type=\"text\" name=\"keyword"+counter+"\" value=\""+ Str.cleanForHtml(uploadedKeyword.getKeyword())+"\" size=\"15\" maxlength=\"255\">");
            out.append("</td>");
            out.append("<td valign='top'>");
            out.append("<input type=\"text\" name=\"islocation"+counter+"\" value=\""+ Str.cleanForHtml(uploadedKeyword.getIslocation())+"\" size=\"15\" maxlength=\"255\">");
            out.append("</td>");
            out.append("<td valign='top'  style=\"background: "+color+";\">");
            out.append(uploadedKeyword.getErrors());
            out.append("</td>");
            out.append("</tr>");
        }
        return out.toString();
    }


}
