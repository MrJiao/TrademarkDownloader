package com.jackson.domain;

import java.io.File;

/**
 * Create by: Jackson
 */
public class MyImageFile {

    int pageNumber;

    File imageFile;

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyImageFile(int pageNumber, File imageFile,String name) {
        this.pageNumber = pageNumber;
        this.imageFile = imageFile;
        this.name = name;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }
}
