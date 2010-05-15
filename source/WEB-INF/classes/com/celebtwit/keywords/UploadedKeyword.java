package com.celebtwit.keywords;

import com.celebtwit.dao.Keyword;
import com.celebtwit.dao.hibernate.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: joereger
 * Date: May 15, 2010
 * Time: 4:27:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class UploadedKeyword {





private String name;
    private String keyword;
    private String islocation;


    private boolean isvalid = false;
    private String errors = "";
    private Keyword keywordObject;

    public UploadedKeyword(String keyword, String islocation) {
        this.keyword = keyword;
        this.islocation = islocation;
        if (islocation!=null && islocation.equals("1")){
            islocation = "1";
        } else {
            islocation = "0";
        }
        validate();
    }


    public void validate(){
        isvalid = true;
        errors = "";
        //Basic required checks
        if (keyword==null || keyword.length()<=0){
            errors = errors + "keyword is required. ";
        }
//        //Check to see if this keyword exists already
//        List<Keyword> keywords = HibernateUtil.getSession().createCriteria(Keyword.class)
//                                               .add(Restrictions.eq("keyword", keyword.toLowerCase()))
//                                               .setCacheable(true)
//                                               .list();
//        for (Iterator<Keyword> kwIt=keywords.iterator(); kwIt.hasNext();) {
//            Keyword keyword1=kwIt.next();
//            errors = errors + "keyword already exists. ";
//        }
        //Set the isvalid flag
        if (errors.length()>0){
            isvalid = false;
        }
    }

    public void createUser(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{
            //Check to see if this keyword exists already
            List<Keyword> keywords = HibernateUtil.getSession().createCriteria(Keyword.class)
                                                   .add(Restrictions.eq("keyword", keyword.toLowerCase()))
                                                   .setCacheable(true)
                                                   .list();
            if (keywords==null || (keywords!=null && keywords.size()==0)){
                //Only create if another doesn't exist
                Keyword newKeyword = new Keyword();
                newKeyword.setKeyword(keyword);
                boolean islocationTmp = false;
                if (islocation.equals("1")){
                    islocationTmp = true;
                }
                newKeyword.setIslocation(islocationTmp);
                newKeyword.setSincetwitpostid(0);
                try{
                    newKeyword.save();
                } catch (Exception ex){
                    logger.error("", ex);
                }

                //Set to this keyword
                this.keywordObject = newKeyword;
            }
        } catch (Exception ex){
            logger.error("", ex);
        }
    }



    public boolean getIsvalid() {
        return isvalid;
    }

    public String getErrors() {
        return errors;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getIslocation() {
        return islocation;
    }

    public void setIslocation(String islocation) {
        this.islocation = islocation;
    }

    public boolean isIsvalid() {
        return isvalid;
    }

    public void setIsvalid(boolean isvalid) {
        this.isvalid = isvalid;
    }

    public Keyword getKeywordObject() {
        return keywordObject;
    }

    public void setKeywordObject(Keyword keywordObject) {
        this.keywordObject = keywordObject;
    }
}
