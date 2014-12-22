package com.dmteam.extractor;

import java.io.File;

/**
 * Created by Administrator on 2014/12/1.
 */
public class MyFileFilter implements FileFilter {
    @Override
    public File transFileName(File file) {
        return new File(file.getAbsolutePath() + ".ctt");
    }

    @Override
    public boolean accept(File pathname) {

        if (pathname.getName().endsWith(".ctt")) return false;

        File f = new File(pathname.getAbsolutePath() + ".ctt");
        return !f.exists();
    }
 }
