
package com.example.kerwinzhou.minilinkedin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kerwinzhou.minilinkedin.models.Education;
import com.example.kerwinzhou.minilinkedin.utils.DateUtils;

import java.util.Arrays;

public class EducationEditActivity extends AppCompatActivity {

    public static final String KEY_EDUCATION = "education";
    public static final String KEY_EDUCATION_ID = "education_id";

    private Education education = new Education();//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//
        education = getIntent().getParcelableExtra(KEY_EDUCATION);
        if (education != null) {
            //Toast.makeText(this, education.id, Toast.LENGTH_LONG).show();//SHOW ID ON SCREEN
            setupUI(education);
        }
    }

    @Override//if select item go to this fucnton
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.finish();
            return true;
        } else if (item.getItemId() == R.id.action_save) {
            saveAndExit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }//callee ! 

    //Show Save Button on up menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void saveAndExit() {
        if (education == null) {//need to reconstruct! 
            education = new Education();
        }
        education.school = ((EditText)(findViewById(R.id.education_school))).getText().toString();
        education.major = ((EditText)(findViewById(R.id.education_major))).getText().toString();
        education.startDate = DateUtils.stringToDate(((EditText)(findViewById(R.id.education_startDate))).getText().toString());
        education.endDate = DateUtils.stringToDate(((EditText)(findViewById(R.id.education_endDate))).getText().toString());
        education.courses = Arrays.asList(TextUtils.split(((EditText)(findViewById(R.id.education_courses))).getText().toString(), "\n"));
        Intent result = new Intent();//intent serialization ! 
        result.putExtra(KEY_EDUCATION, education);//KEY_EDUCATION is name of package! 
        setResult(RESULT_OK, result);
        finish();
    }

    protected void setupUI(final Education data) {
        ((TextView) findViewById(R.id.education_school)).setText(education.school);
        ((TextView) findViewById(R.id.education_major)).setText(education.major);
        ((TextView) findViewById(R.id.education_startDate)).setText(DateUtils.dateToString(education.startDate));
        ((TextView) findViewById(R.id.education_endDate)).setText(DateUtils.dateToString(education.endDate));
        ((TextView) findViewById(R.id.education_courses)).setText(TextUtils.join("\n", education.courses));

        findViewById(R.id.education_edit_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(KEY_EDUCATION_ID, data.id);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}