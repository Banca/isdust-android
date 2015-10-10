package pw.isdust.isdust;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import pw.isdust.isdust.function.Xiaoyuanka;

public class MainActivity_old extends AppCompatActivity {
    Bitmap myzm_biaozhuan [];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //校园卡
        //Xiaoyuanka a=new Xiaoyuanka(this);
//        String b=a.login("1501060225", "960826");
//        a.chaxun();
        //校园卡




        //上网登录
//        Shangwangdenglu a=new Shangwangdenglu();
//        String b= a.login("1501060225","960826wang");
//        a.login_cmcc("18866396947", "891031");
//        a.xiaxian_cmcc();

        //System.out.println(b);





    }

    //@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        //return super.onOptionsItemSelected(item);
        return true;
    }
}
