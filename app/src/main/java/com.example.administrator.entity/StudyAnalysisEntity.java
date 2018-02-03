package com.example.administrator.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class StudyAnalysisEntity implements Serializable {


    String code;
    String msg;
    list list;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public StudyAnalysisEntity.list getList() {
        return list;
    }

    public void setList(StudyAnalysisEntity.list list) {
        this.list = list;
    }

    public class list {

        public StudyAnalysisEntity.list.chapter_accuracys getChapter_accuracys() {
            return chapter_accuracys;
        }

        public void setChapter_accuracys(StudyAnalysisEntity.list.chapter_accuracys chapter_accuracys) {
            this.chapter_accuracys = chapter_accuracys;
        }

        chapter_accuracys chapter_accuracys;

        public class chapter_accuracys {//能
            int chapter_accuracy[];
            String chapter_name[];

            public int[] getChapter_accuracy() {
                return chapter_accuracy;
            }

            public void setChapter_accuracy(int[] chapter_accuracy) {
                this.chapter_accuracy = chapter_accuracy;
            }

            public String[] getChapter_name() {
                return chapter_name;
            }

            public void setChapter_name(String[] chapter_name) {
                this.chapter_name = chapter_name;
            }
        }


        memdata_accuracy memdata_accuracy;

        public StudyAnalysisEntity.list.memdata_accuracy getMemdata_accuracy() {
            return memdata_accuracy;
        }

        public void setMemdata_accuracy(StudyAnalysisEntity.list.memdata_accuracy memdata_accuracy) {
            this.memdata_accuracy = memdata_accuracy;
        }

        public class memdata_accuracy {  //不能
            float this_week_accuracy[];
            float last_week_accuracy[];

            public float[] getThis_week_accuracy() {
                return this_week_accuracy;
            }

            public void setThis_week_accuracy(float[] this_week_accuracy) {
                this.this_week_accuracy = this_week_accuracy;
            }

            public float[] getLast_week_accuracy() {
                return last_week_accuracy;
            }

            public void setLast_week_accuracy(float[] last_week_accuracy) {
                this.last_week_accuracy = last_week_accuracy;
            }
        }

        public StudyAnalysisEntity.list.subject_solution getSubject_solution() {
            return subject_solution;
        }

        public void setSubject_solution(StudyAnalysisEntity.list.subject_solution subject_solution) {
            this.subject_solution = subject_solution;
        }

        subject_solution subject_solution;

        public class subject_solution {
            public float[] getThis_week_solution() {
                return this_week_solution;
            }

            public void setThis_week_solution(float[] this_week_solution) {
                this.this_week_solution = this_week_solution;
            }

            public float[] getLast_week_solution() {
                return last_week_solution;
            }

            public void setLast_week_solution(float[] last_week_solution) {
                this.last_week_solution = last_week_solution;
            }

            float this_week_solution[];
            float last_week_solution[];
        }


        public StudyAnalysisEntity.list.recomm getRecomm() {
            return recomm;
        }

        public void setRecomm(StudyAnalysisEntity.list.recomm recomm) {
            this.recomm = recomm;
        }

        recomm recomm;

        public class recomm {

            ArrayList<maximum> maximum;

            public class maximum {
                int chapter_accuracy;

                public String getChapter_name() {
                    return chapter_name;
                }

                public void setChapter_name(String chapter_name) {
                    this.chapter_name = chapter_name;
                }

                public int getChapter_accuracy() {
                    return chapter_accuracy;
                }

                public void setChapter_accuracy(int chapter_accuracy) {
                    this.chapter_accuracy = chapter_accuracy;
                }

                String chapter_name;
            }


            ArrayList<minimum> minimum;

            public ArrayList<StudyAnalysisEntity.list.recomm.maximum> getMaximum() {
                return maximum;
            }

            public void setMaximum(ArrayList<StudyAnalysisEntity.list.recomm.maximum> maximum) {
                this.maximum = maximum;
            }

            public ArrayList<StudyAnalysisEntity.list.recomm.minimum> getMinimum() {
                return minimum;
            }

            public void setMinimum(ArrayList<StudyAnalysisEntity.list.recomm.minimum> minimum) {
                this.minimum = minimum;
            }

            public class minimum {
                int chapter_accuracy;
                String chapter_name;

                public int getChapter_accuracy() {
                    return chapter_accuracy;
                }

                public void setChapter_accuracy(int chapter_accuracy) {
                    this.chapter_accuracy = chapter_accuracy;
                }

                public String getChapter_name() {
                    return chapter_name;
                }

                public void setChapter_name(String chapter_name) {
                    this.chapter_name = chapter_name;
                }
            }

        }


    }


}
