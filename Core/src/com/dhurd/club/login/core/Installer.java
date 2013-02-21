package com.dhurd.club.login.core;

import com.dhurd.club.login.coreutils.CorePaths;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import org.openide.DialogDisplayer;
import org.openide.LifecycleManager;
import org.openide.WizardDescriptor;
import org.openide.modules.ModuleInstall;
import org.openide.windows.WindowManager;

public class Installer extends ModuleInstall {
    private static final Logger logger = Logger.getLogger(Installer.class.getName());

    @Override
    public void restored() {
        // Set the size of the application
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Frame f = WindowManager.getDefault().getMainWindow();
                f.addWindowListener(new Listener());
            }
        });
        
        // Check if the database exists
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
    
    // Listener for setting size, because the size is reset after installation:
    private class Listener extends WindowAdapter {
        @Override
        public void windowActivated(WindowEvent event) {
            Frame f = WindowManager.getDefault().getMainWindow();
            f.setSize(1280, 720);
        }
    }
}
