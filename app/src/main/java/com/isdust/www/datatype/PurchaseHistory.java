package com.isdust.www.datatype;

/**
 * Created by Administrator on 2015/10/12.
 */
public class PurchaseHistory {
    private String behavior, //用户操作行为
            time,   //操作时间
            addr,   //操作地点
            bala,   //操作后余额
            money;  //操作金额
    public PurchaseHistory() {
        behavior = "";
        time = "";
        addr = "";
        bala = "";
        money = "";
    }
    public void setBehavior(String str) {
        behavior = str;
    }
    public void setTime(String str) {
        time = str;
    }
    public void setAddr(String str) {
        addr = str;
    }
    public void setBala(String str) {
        bala = str;
    }
    public void setMoney(String str) {
        money = str;
    }


    public String getBehavior() {
        return behavior;
    }
    public String getTime() {
        return time;
    }
    public String getAddr() {
        return addr;
    }
    public String getBala() {
        if (money.charAt(0) == '-') {
            return bala;
        }
        else {
            float ba,mo,sum;
            ba = Float.valueOf(bala).floatValue();
            mo = Float.valueOf(money).floatValue();
            sum = ba + mo;
            return Float.toString(sum);
        }
    }   //返回操作后金额。（判断用来解决转账后，余额不变的问题
    public String getMoney() {
        return money;
    }
}
