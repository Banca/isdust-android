package com.isdust.www.Module;

import com.isdust.www.Module.BaseModule;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by zor on 2016/9/28.
 */

public class Catagory {
    private int catagory_id;
    private List<BaseModule> modules = new ArrayList<BaseModule>();

    public Catagory(int catagory_id) {
        this.catagory_id = catagory_id;
    }

    public int getmCategoryName() {
        return catagory_id;
    }
    public List<BaseModule> getProductList() {
        return modules;
    }


    public void addItem(BaseModule pItem) {
        modules.add(pItem);
    }

    /**
     *  获取Item内容
     *
     * @param pPosition
     * @return
     */
    public Object getItem(int pPosition) {
        // Category排在第一位
       // if (pPosition == 0) {
           // return catagory_id;
       // } else {
            return modules.get(pPosition );
       // }
    }

    /**
     * 当前类别Item总数。Category也需要占用一个Item
     * @return
     */
    public int getItemCount() {
        return modules.size() ;
    }
}
