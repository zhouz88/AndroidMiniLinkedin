package com.example.kerwinzhou.minilinkedin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.kerwinzhou.minilinkedin.models.BasicInfo;
import com.example.kerwinzhou.minilinkedin.models.Education;
import com.example.kerwinzhou.minilinkedin.models.Experience;
import com.example.kerwinzhou.minilinkedin.models.Project;
import com.example.kerwinzhou.minilinkedin.utils.DateUtils;
import com.example.kerwinzhou.minilinkedin.utils.ModelUtils;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_EDUCATION_EDIT = 100;
    private static final int REQ_CODE_EMAIL_EDIT = 101;
    private static final int REQ_CODE_EXPERIENCE_EDIT = 102;
    private static final int REQ_CODE_RPOJECT_EDIT = 103;

    private BasicInfo info;
    private List<Education> educations;
    private List<Experience> experiences;
    private List<Project> projects;

    private static final String MODEL_EDUCATIONS = "educations";
    private static final String MODEL_EXPERIENCES = "experiences";
    private static final String MODEL_PROJECTS = "projects";
    private static final String MODEL_BASIC_INFO = "basic_info";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //fakeData();
        loadData();
        setupUI();
    }

    private void setupUI() {
        setContentView(R.layout.activity_main);//把XML文件读写到内存中！以View的形式存在， view相当于ListNode

        ImageButton addEducationBtn = (ImageButton)findViewById(R.id.add_education_btn);
        addEducationBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EducationEditActivity.class);
                startActivityForResult(intent, REQ_CODE_EDUCATION_EDIT);
            }
        });

        ImageButton addExperiencebtn = (ImageButton) findViewById(R.id.add_experience_btn);
        addExperiencebtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExperienceEditActivity.class);
                startActivityForResult(intent, REQ_CODE_EXPERIENCE_EDIT);
            }
        });

        ImageButton addProjectBtn = (ImageButton)findViewById(R.id.add_project_btn);
        addProjectBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProjectEditActivity.class);
                startActivityForResult(intent, REQ_CODE_RPOJECT_EDIT);
            }
        });

        ImageButton addNameEmailBtn = (ImageButton)findViewById(R.id.addNameEmailbtn);
        addNameEmailBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NameEmailActivity.class);
                intent.putExtra(NameEmailActivity.KEY_EMAILNAME, info);
                startActivityForResult(intent, REQ_CODE_EMAIL_EDIT);
            }
        });

        setupBasicInfoUI();
        setupEducationsUI();
        setupExperienceUI();
        setupProjectUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Get result from callee activity
        if (resultCode != RESULT_OK) {
            return;
        }
        boolean flag = false;
        switch (requestCode) {
            case REQ_CODE_EDUCATION_EDIT :
                String educationID = data.getStringExtra(EducationEditActivity.KEY_EDUCATION_ID);
                if (educationID != null) {
                    deleteEducation(educationID);
                    break;
                }
                Education newEducation = data.getParcelableExtra(EducationEditActivity.KEY_EDUCATION);
                for (int i = 0; i < educations.size(); i++) {
                    Education e = educations.get(i);
                    if (e.id.equals(newEducation.id)) {
                        educations.set(i, newEducation);
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    educations.add(newEducation);
                }
                ModelUtils.save(this, MODEL_EDUCATIONS, educations);
                setupEducationsUI();
                break;
            case REQ_CODE_EMAIL_EDIT :
                BasicInfo info = data.getParcelableExtra(NameEmailActivity.KEY_EMAILNAME);
                this.info = info;
                setupBasicInfoUI();
                ModelUtils.save(this, MODEL_BASIC_INFO, this.info);
                break;
            case REQ_CODE_EXPERIENCE_EDIT:
                String experienceID = data.getStringExtra(ExperienceEditActivity.KEY_EXPERIENCE_ID);
                if (experienceID != null) {
                    deleteExperience(experienceID);
                    break;
                }
                Experience newExperience = data.getParcelableExtra(ExperienceEditActivity.KEY_EXPERIENCE);
                for (int i = 0; i < experiences.size(); i++) {
                    Experience e = experiences.get(i);
                    if (e.id.equals(newExperience.id)) {
                        experiences.set(i, newExperience);
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    experiences.add(newExperience);
                }
                ModelUtils.save(this, MODEL_EXPERIENCES, experiences);
                setupExperienceUI();
                break;
            case REQ_CODE_RPOJECT_EDIT:
                String projectID = data.getStringExtra(ProjectEditActivity.KEY_PROJECT_ID);
                if (projectID != null) {
                    deleteProject(projectID);
                    break;
                }
                Project newProject = data.getParcelableExtra(ProjectEditActivity.KEY_PROJECT);
                for (int i = 0; i < projects.size(); i++) {
                    Project e = projects.get(i);
                    if (e.id.equals(newProject.id)) {
                        projects.set(i, newProject);
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    projects.add(newProject);
                }
                ModelUtils.save(this, MODEL_PROJECTS, projects);
                setupProjectUI();
                break;
            default:
                break;
        }
    }

    //show the data of email and name on the screen
    private void setupBasicInfoUI() {
        ((TextView)findViewById(R.id.name)).setText(info.name);
        ((TextView)findViewById(R.id.email)).setText(info.email);
    }

    private void setupEducationsUI() {
        LinearLayout educationsContainer = (LinearLayout) this.findViewById(R.id.education_container);//输入是一个整数
        //The last part should be "new" by LinearLayout!后半部分父类自身是用LinearLayout new 出来的，才可强制转化！
        educationsContainer.removeAllViews();
        for (final Education education : educations) {
            View view = getLayoutInflater().inflate(R.layout.education_item, null);
            //the same as build a TreeNode. 相当于buildTree转化为内存中一个TreeNode;
            //给一个布局文件，给我一个VIEW！
            String dataString = " (" + DateUtils.dateToString(education.startDate) + " ~ " + DateUtils.dateToString(education.startDate) + ") ";
            ((TextView) view.findViewById(R.id.education_school)).setText(education.school + dataString);
            ((TextView) view.findViewById(R.id.education_courses)).setText(formatCourses(education.courses));

            view.findViewById(R.id.edit_education_btn).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, EducationEditActivity.class);
                    intent.putExtra(EducationEditActivity.KEY_EDUCATION, education);
                    startActivityForResult(intent, REQ_CODE_EDUCATION_EDIT);
                }
            });

            educationsContainer.addView(view);// View is just like a TreeNode. view 本质是一个TreeNode; 所以是一个LinkedList
        }
    }


    private void setupExperienceUI() {
        LinearLayout experiencesContainer = (LinearLayout) this.findViewById(R.id.experience_container);
        experiencesContainer.removeAllViews();
        for (final Experience experience : experiences) {
            View view = getLayoutInflater().inflate(R.layout.experience_item, null);
            ((TextView) view.findViewById(R.id.experience_startDate)).setText(experience.name);
            ((TextView) view.findViewById(R.id.experience_endDate)).setText(experience.content);

            view.findViewById(R.id.edit_experience_btn).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, ExperienceEditActivity.class);
                    intent.putExtra(ExperienceEditActivity.KEY_EXPERIENCE, experience);
                    startActivityForResult(intent, REQ_CODE_EXPERIENCE_EDIT);
                }
            });

            experiencesContainer.addView(view);
        }
    }

    private void setupProjectUI() {
        LinearLayout projectsContainer = (LinearLayout) this.findViewById(R.id.project_container);
        projectsContainer.removeAllViews();
        for (final Project project : projects) {
            View view = getLayoutInflater().inflate(R.layout.project_item, null);
            ((TextView) view.findViewById(R.id.project_startDate)).setText(project.name);
            ((TextView) view.findViewById(R.id.project_endDate)).setText(project.content);

            view.findViewById(R.id.edit_project_btn).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, ProjectEditActivity.class);
                    intent.putExtra(ProjectEditActivity.KEY_PROJECT, project);
                    startActivityForResult(intent, REQ_CODE_RPOJECT_EDIT);
                }
            });

            projectsContainer.addView(view);
        }
    }

    private void fakeData() {
        info = new BasicInfo();
        educations = new ArrayList<>();
        experiences = new ArrayList<>();
        projects = new ArrayList<>();
    }

    private void loadData() {
        BasicInfo savedBasicInfo = ModelUtils.read(this, MODEL_BASIC_INFO, new TypeToken<BasicInfo>(){});
        info = (savedBasicInfo == null ? new BasicInfo() : savedBasicInfo);

        List<Education> savedEducation =  ModelUtils.read(this, MODEL_EDUCATIONS, new TypeToken<List<Education>>(){});
        educations = (savedEducation == null ? new ArrayList<Education>() : savedEducation);

        List<Experience> savedExperience =  ModelUtils.read(this, MODEL_EXPERIENCES, new TypeToken<List<Experience>>(){});
        experiences = (savedExperience == null ? new ArrayList<Experience>() : savedExperience);

        List<Project> savedProjects =  ModelUtils.read(this, MODEL_PROJECTS, new TypeToken<List<Project>>(){});
        projects = (savedProjects == null ? new ArrayList<Project>() : savedProjects);
    }

    @NonNull
    private String formatCourses(List<String> courses) {
        StringBuilder sb = new StringBuilder();
        for (String course : courses) {
            sb.append('-').append(' ').append(course).append("\n");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    private void deleteEducation(@NonNull String educationId) {
        for (int i = 0; i < educations.size(); i++) {
            Education e = educations.get(i);
            if (educationId.equals(e.id)) {
                educations.remove(i);
                break;
            }
        }
        ModelUtils.save(this, MODEL_EDUCATIONS, educations);
        setupEducationsUI();
    }


    private void deleteExperience(String experienceID) {
        for (int i = 0; i < experiences.size(); i++) {
            Experience e = experiences.get(i);
            if (experienceID.equals(e.id)) {
                experiences.remove(i);
                break;
            }
        }
        ModelUtils.save(this, MODEL_EXPERIENCES, experiences);
        setupExperienceUI();
    }

    private void deleteProject(String projectID) {
        for (int i = 0; i < projects.size(); i++) {
            Project e = projects.get(i);
            if (projectID.equals(e.id)) {
                projects.remove(i);
                break;
            }
        }
        ModelUtils.save(this, MODEL_PROJECTS, projects);
        setupProjectUI();
    }
}