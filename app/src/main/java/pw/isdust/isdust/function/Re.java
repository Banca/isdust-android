package pw.isdust.isdust.function;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wzq on 10/1/16.
 */

public class Re {
    private Pattern mpattern;
    public static Re compile(String expression){
        Re res=new Re();
        res.mpattern= Pattern.compile(expression);
        return res;
    }
    public String[][] findall(String data){
        Matcher matcher=mpattern.matcher(data);
        String[][] result;
        List<List<String>> array_temp=new ArrayList<List<String>>();
        List<String> result_child;
        while (matcher.find()){
            result_child=new ArrayList<String>();
            int size=matcher.groupCount();
            for (int i=0;i<size+1;i++){
                result_child.add(matcher.group(i));


            }
            array_temp.add(result_child);
        }

        result=new String[array_temp.size()][];
        for(int i=0;i<array_temp.size();i++){

            String temp[]=new String[array_temp.get(i).size()];
            for(int j=0;j<array_temp.get(i).size();j++){
                temp[j]=array_temp.get(i).get(j);


            }
            result[i]=temp;

        }


        return result;
    }
    public String sub(String data1,String data2){

        return mpattern.matcher(data2).replaceAll(data1);
    }

}
