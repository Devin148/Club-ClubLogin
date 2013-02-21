package com.dhurd.club.login.core;

import com.dhurd.club.login.coreutils.CorePaths;
import java.io.File;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.DialogDisplayer;
import org.openide.LifecycleManager;
import org.openide.WizardDescriptor;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {
    private static final Logger logger = Logger.getLogger(Installer.class.getName());

    @Override
    public void restored() {
        String dbPath = CorePaths.DATABASE_PATH;
        File db = new File(dbPath);
        if (!db.exists()) {
            // If the database doesn't exist, make it!
            logger.log(Level.INFO, "Could not find an existing database file at {0}", dbPath);
            WizardDescriptor wiz = new WizardDescriptor(new SetupWizardIterator());
             // {0} will be replaced by WizardDescriptor.Panel.getComponent().getName()
             // {1} will be replaced by WizardDescriptor.Iterator.name()
             wiz.setTitleFormat(new MessageFormat("{0} {1}"));
             wiz.setTitle("Club Login Initial Setup");
             if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
                 logger.log(Level.INFO, "Database setup completed.");
             } else {
                 logger.log(Level.INFO, "User canceled the database setup.");
                 LifecycleManager.getDefault().exit();  // Exit the application
             }
        }
        
    }
}
