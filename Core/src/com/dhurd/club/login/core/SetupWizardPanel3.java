package com.dhurd.club.login.core;

import com.dhurd.club.login.sql.SQLManager;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class SetupWizardPanel3 implements WizardDescriptor.Panel<WizardDescriptor> {

    private SetupVisualPanel3 component;
    
    @Override
    public SetupVisualPanel3 getComponent() {
        if (component == null) {
            component = new SetupVisualPanel3();
        }
        return component;
    }

    @Override
    public HelpCtx getHelp() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }

    @Override
    public void readSettings(WizardDescriptor wiz) {
        // Initialize this panel
        String name = wiz.getProperty("name").toString();
        String password = wiz.getProperty("password").toString();
        getComponent().setUsername(name);
        getComponent().setPassword(password);
        
        // Setup the database
        SQLManager manager = SQLManager.getDefault();
        manager.create();
        manager.addManager(name, password);
        
        // Add some temp users
        manager.addEmployee("Mike", "Smith", "Mary", "28 MaryJAne", "111-111-111");
        manager.addEmployee("Tom", "Hurd", "Candy", "2 Juniper", "222-222-222");
        manager.addEmployee("Tim", "Grover", "Ally", "5 Red Spy", "333-333-333");
        manager.addEmployee("John", "Gorst", "Smith", "12 Unhappy", "444-444-444");
        
        // Fix the wizard options
        wiz.setOptions(new Object[] {WizardDescriptor.PREVIOUS_OPTION, WizardDescriptor.NEXT_OPTION, WizardDescriptor.FINISH_OPTION, WizardDescriptor.CANCEL_OPTION});
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
    }
}
