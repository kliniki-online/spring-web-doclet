package ru.hts.springwebdoclet;

import com.sun.javadoc.PackageDoc;

import java.util.List;

/**
 * Global configuration object
 * @author Ivan Sungurov
 */

public class Config {
    private List<PackageDoc> specifiedPackages;

    public List<PackageDoc> getSpecifiedPackages() {
        return specifiedPackages;
    }

    void setSpecifiedPackages(List<PackageDoc> specifiedPackages) {
        this.specifiedPackages = specifiedPackages;
    }
}
