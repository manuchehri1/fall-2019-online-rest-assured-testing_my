package com.automation.pojos;

/**
 * "links": [
 *                 {
 *                     "rel": "self",
 *                     "href": "http://3.85.41.58:1000/ords/hr/employees/101"
 *                 }
 */
public class Link {

    private String rel;
    private String href;

    public void setRel(String rel) {
        this.rel = rel;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public String getHref() {
        return href;
    }

    @Override
    public String toString() {
        return "Link{" +
                "rel='" + rel + '\'' +
                ", href='" + href + '\'' +
                '}';
    }
}
