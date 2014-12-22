package com.dmteam.extractor;

import java.io.File;

/**
 * Created by Administrator on 2014/12/1.
 */
public interface FileFilter extends java.io.FileFilter {

    File transFileName(File file);
}
