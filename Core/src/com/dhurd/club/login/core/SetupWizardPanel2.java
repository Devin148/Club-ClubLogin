package com.dhurd.club.login.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class SetupWizardPanel2 implements WizardDescriptor.Panel<WizardDescriptor> {
    private JButton nextButton;
    private SetupVisualPanel2 component;
    
    @Override
    public SetupVisualPanel2 getComponent() {
        if (component == null) {
            component = new SetupVisualPanel2(this);
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
    public void readSettings(final WizardDescriptor wiz) {
        nextButton = new JButton("Next >");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pass = getComponent().getPassword();
                String vpass = getComponent().getVerifyPassword();
                if (pass.equals(vpass)) {
                    wiz.doNextClick();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Passwords do not match.",
                            "Verification Error",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        wiz.setOptions(new Object[] {WizardDescriptor.PREVIOUS_OPTION, nextButton, WizardDescriptor.FINISH_OPTION, WizardDescriptor.CANCEL_OPTION});
        setNextButtonEnabled(false);
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        String name = getComponent().getUsername();
        String password = getComponent().getPassword();
        wiz.putProperty("name", name);
        wiz.putProperty("password", password);
    }
    
    void setNextButtonEnabled(boolean enabled) {
        nextButton.setEnabled(enabled);
    }
}
