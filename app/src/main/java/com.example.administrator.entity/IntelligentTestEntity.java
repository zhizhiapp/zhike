package com.example.administrator.entity;


import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理类联考实体类
 */

public class IntelligentTestEntity implements Serializable {
    private String id;
    private String projectId;
    private String subjectId;
    private String poolId;
    private String question;
    private String answer;
    private String explain;
    private String[] items;
    private String num;
    private String title;
    private String paperId;
    private String recordId;
    private List<String> options;

    //-1代表没有选择
    private int select = -1;


    public void setSelect(int i) {
        if (i >= 0 && i <= 4) {
            select = i;
        }
    }

    public int getSelect() {
        return select;
    }

    public boolean isAnswer() {
        return answer.equals("A") && select == 0 || answer.equals("B") && select == 1 || answer.equals("C") && select == 2 || answer.equals("D") && select == 3 || answer.equals("E") && select == 4;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static List<IntelligentTestEntity> get(String json) {
        List<IntelligentTestEntity> data = new ArrayList<>();
        if (!TextUtils.isEmpty(json)) {
            try {
                JSONObject object = new JSONObject(json);
                JSONObject list = object.getJSONObject("list");
                for (int i = 0; i < 25; i++) {
                    IntelligentTestEntity entity = new IntelligentTestEntity();
                    entity.setNum(list.getString("num"));
                    entity.setTitle(list.getString("title"));
                    entity.setPaperId(list.getString("paperid"));
                    entity.setRecordId(list.getString("recordid"));
                    JSONObject jsonObject = list.getJSONObject(String.valueOf(i));
                    String projectId = jsonObject.getString("projectid");
                    entity.setProjectId(projectId);
                    String subjectid = jsonObject.getString("subjectid");
                    entity.setSubjectId(subjectid);
                    String poolId = jsonObject.getString("poolid");
                    entity.setPoolId(poolId);
                    String question = jsonObject.getString("question");
                    entity.setQuestion(question);
                    String answer = jsonObject.getString("answer");
                    entity.setAnswer(answer);
                    String explain = jsonObject.getString("explain");
                    entity.setExplain(explain);
                    String id = jsonObject.getString("id");
                    entity.setId(id);
                    JSONArray items = jsonObject.getJSONArray("items");
                    List<String> options = new ArrayList<>();
                    for (int a = 0; a < items.length(); a++) {
                        String option = items.getString(a);
                        options.add(option);
                    }
                    entity.setOptions(options);
                    data.add(entity);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getPoolId() {
        return poolId;
    }

    public void setPoolId(String poolId) {
        this.poolId = poolId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String[] getItems() {
        return items;
    }

    public void setItems(String[] items) {
        this.items = items;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
