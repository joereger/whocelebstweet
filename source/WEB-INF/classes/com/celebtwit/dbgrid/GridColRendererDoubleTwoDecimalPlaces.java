package com.celebtwit.dbgrid;

import com.celebtwit.util.Str;

/**
 * User: Joe Reger Jr
 * Date: Nov 7, 2007
 * Time: 12:16:27 PM
 */
public class GridColRendererDoubleTwoDecimalPlaces implements GridColRenderer {

    //Format of content is <$propertyname$>
    // or
    // <$propertyname|"+Grid.GRIDCOLRENDERER_DATETIMECOMPACT+"$>

    public String render(Object in) {
        if (in!=null){
            if (in instanceof Double){
                return Str.formatForMoney((Double)in);
            }
        }
        return "";
    }

}
