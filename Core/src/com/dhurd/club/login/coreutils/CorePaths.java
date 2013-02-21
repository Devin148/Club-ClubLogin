package com.dhurd.club.login.coreutils;

import java.io.File;

public class CorePaths {
    public static final String USER_DIR = System.getProperty("netbeans.user");
    public static final String DATABASE_PATH = USER_DIR + File.separator + "database.db";
}
