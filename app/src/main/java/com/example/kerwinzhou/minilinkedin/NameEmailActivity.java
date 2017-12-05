package com.example.kerwinzhou.minilinkedin;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kerwinzhou.minilinkedin.models.BasicInfo;

public class NameEmailActivity extends AppCompatActivity {
    public static final String KEY_EMAILNAME = "emailname";
    private BasicInfo info = new BasicInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_email);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回按钮
        info = getIntent().getParcelableExtra(KEY_EMAILNAME);
        if (info != null) {
            setupUI(info);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override//菜单项被点击了后都会进入这个函数
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.finish();
            return true;
        } else if (item.getItemId() == R.id.action_save) {
            saveAndExit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }//回调函数 callee

    private void saveAndExit() {
        if ( info == null) {
            info = new BasicInfo();
        }
        info.name = ((EditText)(findViewById(R.id.user_name))).getText().toString();
        info.email = ((EditText)(findViewById(R.id.email_address))).getText().toString();
        Intent result = new Intent();//intent 序列化后传输！！！
        result.putExtra(KEY_EMAILNAME, info);//KEY_EDUCATION是包的名字！
        setResult(RESULT_OK, result);
        finish();
    }

    private void setupUI(final BasicInfo info) {
        ((TextView) findViewById(R.id.user_name)).setText(info.name);
        ((TextView) findViewById(R.id.email_address)).setText(info.email);
    }
}