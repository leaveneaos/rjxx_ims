package com.rjxx.taxeasy.utils;

import java.util.HashMap;
import java.util.Map;

public class JspPar {
    private JspPar(){
        this.btMarginLeft=15;
        this.btText="查询";
        this.select=new HashMap<String,String>();
        this.select.put("height","33px");
    }

    private Integer btMarginLeft;
    private String btText;
    private Map<String,String> select;

    private static JspPar par;

    public Map<String, String> getSelect() {
        return select;
    }

    public void setSelect(Map<String, String> select) {
        this.select = select;
    }

    public static JspPar getPar(){
        if(par==null){
            par=new JspPar();
        }
        return par;
    }


    public String getBtText() {
        return btText;
    }

    public void setBtText(String btText) {
        this.btText = btText;
    }



    public Integer getBtMarginLeft() {
        return btMarginLeft;
    }

    public void setBtMarginLeft(Integer btMarginLeft) {
        this.btMarginLeft = btMarginLeft;
    }
}
