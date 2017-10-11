package com.example.s522050.newsapp.Model;


import java.util.List;

public class WebSite {

    public String status;
    public List<Source> sources;

    public WebSite() {
    }

    public WebSite(String status, List<Source> sources) {
        this.status = status;
        this.sources = sources;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }
}
