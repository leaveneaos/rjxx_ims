package com.rjxx.taxeasy.utils;

public class JspPar {
    private JspPar(){
        this.btMarginLeft=15;
        this.btText="查询";
    }

    private Integer btMarginLeft;
    private String btText;

    private static JspPar par;

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
