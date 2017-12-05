package com.example.kerwinzhou.minilinkedin;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kerwinzhou.minilinkedin.models.Experience;


public class ExperienceEditActivity extends AppCompatActivity {

    public static final String KEY_EXPERIENCE = "experience";
    public static final String KEY_EXPERIENCE_ID = "experience_id";
    private Experience experience = new Experience();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回按钮\
        experience = getIntent().getParcelableExtra(KEY_EXPERIENCE);
        if (experience != null) {
            setupUI(experience);
        }
    }

    @Override//把menu中item显示出来
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
    }//回调函数

    private void saveAndExit() {
        if (experience == null) {
            experience = new Experience();
        }
        experience.name = ((EditText)(findViewById(R.id.experience_startDate))).getText().toString();
        experience.content = ((EditText)(findViewById(R.id.experience_endDate))).getText().toString();
        Intent result = new Intent();//intent 序列化后传输！！！
        result.putExtra(KEY_EXPERIENCE, experience);//KEY_EDUCATION是包的名字！
        setResult(RESULT_OK, result);
        finish();
    }

    private void setupUI(final Experience experience) {
        ((TextView) findViewById(R.id.experience_startDate)).setText(experience.name);
        ((TextView) findViewById(R.id.experience_endDate)).setText(experience.content);
        findViewById(R.id.experience_edit_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(KEY_EXPERIENCE_ID, experience.id);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
