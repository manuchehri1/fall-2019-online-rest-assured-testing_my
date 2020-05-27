package com.automation.tests.murodil.day06;

import java.util.List;

public class AllSpartans {
    private List<Spartan> spartanList;

    public AllSpartans(List<Spartan> spartanList) {
        this.spartanList = spartanList;
    }

    public void setSpartanList(List<Spartan> spartanList) {
        this.spartanList = spartanList;
    }

    public List<Spartan> getSpartanList() {
        return spartanList;
    }

    @Override
    public String toString() {
        return "AllSpartans{" +
                "spartanList=" + spartanList +
                '}';
    }
}
