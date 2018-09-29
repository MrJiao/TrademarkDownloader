package com.jackson.domain;

/**
 * Create by: Jackson
 */
public class GetDocNumBean {

    String docid;
    String docno;

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getDocno() {
        return docno;
    }

    public void setDocno(String docno) {
        this.docno = docno;
    }

    @Override
    public String toString() {
        return "GetDocNumBean{" +
                "docid='" + docid + '\'' +
                ", docno='" + docno + '\'' +
                '}';
    }
}
