package com.formal.sdusthelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
    static EditText mEditText;
    List<Book> mBooks;
    ImageView mImageView_library;
    ImageView mImageView_search;

    //Application isdust;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext=this;
        super.onCreate(savedInstanceState);
        mLibrary=new Library();
        INIT(R.layout.helper_library, "图书馆");
        mEditText=(EditText)findViewById(R.id.guancang_edittext);
        mImageView_library=(ImageView)findViewById(R.id.guancang_scan);
        mImageView_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(mContext, Library_scan.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 1);
            }
        });
        mImageView_search=(ImageView)findViewById(R.id.guancang_search);
        mImageView_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBooks=mLibrary.findBookByName(mEditText.getText().toString());
                isdustapp.setBooks(mBooks);

                Intent intent = new Intent();

                intent.setClass(mContext,Library_result.class);
                startActivity(intent);
                //启动activity
//                this.startActivity(intent);
//
//                Intent intent = new Intent();
//                intent.setClass(mContext, Library_result.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivityForResult(intent,1);
            }
        });
//        Intent intent = new Intent();
//        intent.setClass(mContext, Library_result.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivityForResult(intent,1);
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
                    mEditText.setText(isbnString);
                    mBooks=mLibrary.findBookByISBN(isbnString);
                    isdustapp.setBooks(mBooks);
//                    mBooks.get(0).downloadpicture();

                }
        }
    }


}
