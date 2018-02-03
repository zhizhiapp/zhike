package com.example.administrator.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class VideoCatalogEntity implements Serializable {

    ArrayList<list> list;

    public ArrayList<VideoCatalogEntity.list> getList() {
        return list;
    }

    public void setList(ArrayList<VideoCatalogEntity.list> list) {
        this.list = list;
    }

    public class list {
        int id;
        String name;
        int number;


        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<VideoCatalogEntity.list.catalog> getCatalog() {
            return catalog;
        }

        public void setCatalog(ArrayList<VideoCatalogEntity.list.catalog> catalog) {
            this.catalog = catalog;
        }

        public int getNumber() {
            return number;
        }
        public void setNumber(int number) {
            this.number = number;
        }
        ArrayList<catalog> catalog;

        public class catalog {
            int id;
            String title;
            String url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getChapterid() {
                return chapterid;
            }

            public void setChapterid(int chapterid) {
                this.chapterid = chapterid;
            }

            public double getTimes() {
                return times;
            }

            public void setTimes(double times) {
                this.times = times;
            }

            String thumb;
            String price;
            int chapterid;
            double times;
        }

    }


}
