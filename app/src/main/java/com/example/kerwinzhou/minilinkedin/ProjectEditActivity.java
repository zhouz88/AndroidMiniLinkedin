package com.example.kerwinzhou.minilinkedin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kerwinzhou.minilinkedin.models.Project;

public class ProjectEditActivity extends AppCompatActivity {

    public static final String KEY_PROJECT = "project";
    public static final String KEY_PROJECT_ID = "project_ID";
    private Project project = new Project();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回按钮\
        project = getIntent().getParcelableExtra(KEY_PROJECT);
        if (project != null) {
            setupUI(project);
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void saveAndExit() {
        if (project == null) {
            project = new Project();
        }
        project.name = ((EditText)(findViewById(R.id.project_startDate))).getText().toString();
        project.content = ((EditText)(findViewById(R.id.project_endDate))).getText().toString();
        Intent result = new Intent();//intent 序列化后传输！！！
        result.putExtra(KEY_PROJECT, project);//KEY_EDUCATION是包的名字！
        setResult(RESULT_OK, result);
        finish();
    }

    private void setupUI(final Project project) {
        ((TextView) findViewById(R.id.project_startDate)).setText(project.name);
        ((TextView) findViewById(R.id.project_endDate)).setText(project.content);
        findViewById(R.id.project_edit_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(KEY_PROJECT_ID, project.id);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}