package pw.isdust.isdust.function;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.isdust.www.datatype.Kebiao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wzq on 10/1/16.
 */

public class ScheduleDB {

    SQLiteDatabase db;
    public ScheduleDB(){
        String DBPath = Environment.getDataDirectory().getName()+"//data//com.isdust.android//databases//";

//        String a=Environment.getDataDirectory().getName();

        db=SQLiteDatabase.openOrCreateDatabase(DBPath+"jiaowu_schedule_new.db",null);
        try{
            create();
        }catch (Exception e){

        }



    }
    public void create(){
        db.execSQL("CREATE TABLE schedule (_id INTEGER PRIMARY KEY AUTOINCREMENT, zhoushu SMALLINT, xingqi SMALLINT, jieci SMALLINT, class VARCHAR,location VARCHAR,teacher VARCHAR)");  //写数据库
    }
    public void drop(){
        db.execSQL("DROP TABLE IF EXISTS schedule");
    }
    public void delete(Kebiao kebiao,int mode){
        switch (mode){
            case 0:
                db.execSQL("DELETE FROM schedule WHERE zhoushu=? and xingqi=? and jieci=?", new Object[]{(kebiao.zhoushu), (kebiao.xingqi), (kebiao.jieci)});
                break;
            case 1:
                db.execSQL("DELETE FROM schedule WHERE zhoushu=? and xingqi=? and jieci=? and class=?", new Object[]{(kebiao.zhoushu), (kebiao.xingqi), (kebiao.jieci),(kebiao.kecheng)});
                break;
        }
    }
    public void delete(Kebiao [] kebiao,int mode){
        for(int i=0;i<kebiao.length;i++){
            delete(kebiao[i],mode);
        }

    }

    public int count(){
        Cursor mCursor;
        mCursor = db.rawQuery("select count(*) from schedule", new String[]{});
        mCursor.moveToNext();
        return mCursor.getInt(0);
    }
    public void add(Kebiao[] kebiao){
        int len=kebiao.length;
        for (int i=0;i<len;i++){
            add(kebiao[i]);
        }
    }
    public void add(Kebiao kebiao){
        db.execSQL("INSERT INTO schedule VALUES (NULL, ?, ?,?,?,?,?)", new Object[]{Integer.valueOf(kebiao.zhoushu), Integer.valueOf(kebiao.xingqi), Integer.valueOf(kebiao.jieci), kebiao.kecheng,kebiao.location,kebiao.teacher});
    }
    public void add(HashMap<String,Object> schedule){
            int [] mzhoushu=(int[]) schedule.get("zhoushu");
            Kebiao temp_kebiao=new Kebiao();
            temp_kebiao.location= (String) schedule.get("location");
            temp_kebiao.kecheng= (String) schedule.get("class");
            temp_kebiao.teacher= (String) schedule.get("teacher");
            temp_kebiao.xingqi= (Integer) schedule.get("xingqi")+"";
            temp_kebiao.jieci= (Integer) schedule.get("jieci")+"";
            for(int j=0;j<mzhoushu.length;j++){
                temp_kebiao.zhoushu= mzhoushu[j]+"";
                add(temp_kebiao);
            }

    }
    public void delete(HashMap<String,Object> schedule){
            int [] mzhoushu=(int[]) schedule.get("zhoushu");
            Kebiao temp_kebiao=new Kebiao();
            temp_kebiao.location= (String) schedule.get("location");
            temp_kebiao.kecheng= (String) schedule.get("class");
            temp_kebiao.teacher= (String) schedule.get("teacher");
            temp_kebiao.xingqi= (Integer) schedule.get("xingqi")+"";
            temp_kebiao.jieci= (Integer) schedule.get("jieci")+"";
            for(int j=0;j<mzhoushu.length;j++){
                temp_kebiao.zhoushu= mzhoushu[j]+"";
                delete(temp_kebiao,1);
            }

    }
    public Kebiao[] search_zhoushu(String zhoushu){
        List<Kebiao> mList_kebiao=new ArrayList<Kebiao>();
        Kebiao mkebiao_temp;
        Cursor mCursor = db.rawQuery("SELECT * FROM schedule WHERE `zhoushu`=?", new String[]{zhoushu});
        while (mCursor.moveToNext()) {
            mkebiao_temp=new Kebiao();
            mkebiao_temp.zhoushu=mCursor.getInt(mCursor.getColumnIndex("zhoushu"))+"";
            mkebiao_temp.xingqi=mCursor.getInt(mCursor.getColumnIndex("xingqi"))+"";
            mkebiao_temp.jieci=mCursor.getInt(mCursor.getColumnIndex("jieci"))+"";
            mkebiao_temp.kecheng=mCursor.getString(mCursor.getColumnIndex("class"))+"";
            mkebiao_temp.location=mCursor.getString(mCursor.getColumnIndex("location"))+"";
            mkebiao_temp.teacher=mCursor.getString(mCursor.getColumnIndex("teacher"))+"";
            mList_kebiao.add(mkebiao_temp);
        }
        Kebiao[] result=mList_kebiao.toArray(new Kebiao[1]);
        return result;
    }
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        db.close();
    }

}
