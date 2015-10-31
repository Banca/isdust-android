package com.formal.sdusthelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.formal.sdusthelper.baseactivity.BaseMainActivity;
import com.formal.sdusthelper.datatype.Book;

import java.util.List;

import pw.isdust.isdust.function.Library;

/**
 * Created by Administrator on 2015/10/17.
 */
public class LibraryActivity extends BaseMainActivity {
    Context mContext;
    Library mLibrary;
    List<Book> mBooks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext=this;
        super.onCreate(savedInstanceState);
        mLibrary=new Library();
        INIT(R.layout.helper_library, "图书馆");
        Button mButton_library=(Button)findViewById(R.id.button_library_scan);
        mButton_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent mIntent = new Intent();
//                mIntent.setClass(mContext, Library_scan.class);
//                mContext.startActivity(mIntent);


                Intent intent = new Intent();
                intent.setClass(LibraryActivity.this, Library_scan.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String isbnString = bundle.getString("result");
                    Toast.makeText(mContext, "ISBN:" + isbnString,
                            Toast.LENGTH_SHORT).show();
                    mBooks=mLibrary.findBookByISBN(isbnString);
                    mBooks.get(0).downloadpicture();

                }
        }
    }


}
