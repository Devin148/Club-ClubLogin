package com.dhurd.club.login.core;

import com.dhurd.club.login.sql.SQLManager;
import java.awt.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
    dtd = "-//com.dhurd.club.login.core//Employees//EN",
autostore = false)
@TopComponent.Description(
    preferredID = "EmployeesTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "explorer", openAtStartup = true)
@ActionID(category = "Window", id = "com.dhurd.club.login.core.EmployeesTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
    displayName = "#CTL_EmployeesAction",
preferredID = "EmployeesTopComponent")
@Messages({
    "CTL_EmployeesAction=Employees",
    "CTL_EmployeesTopComponent=Employees",
    "HINT_EmployeesTopComponent=Current listing of club employees."
})
public final class EmployeesTopComponent extends TopComponent {
    private static final Logger logger = Logger.getLogger(EmployeesTopComponent.class.getName());
    private static EmployeesTopComponent instance;
    
    private Map<String, Boolean> employeeStates;
    private List<String> employees;
    
    private EmployeeListModel model;
    private EmployeeListRenderer renderer;
    
    /**
     * @return the default instance of EmployeesTopComponent
     */
    public static EmployeesTopComponent getDefault() {
        if (instance == null) {
            instance = new EmployeesTopComponent();
        }
        return instance;
    }

    public EmployeesTopComponent() {
        initComponents();
        setName(Bundle.CTL_EmployeesTopComponent());
        setToolTipText(Bundle.HINT_EmployeesTopComponent());
        putClientProperty(TopComponent.PROP_CLOSING_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_DRAGGING_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_MAXIMIZATION_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_SLIDING_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_UNDOCKING_DISABLED, Boolean.TRUE);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        employeesScrollPane = new javax.swing.JScrollPane();
        employeesList = new javax.swing.JList();

        employeesScrollPane.setViewportView(employeesList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(employeesScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(employeesScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList employeesList;
    private javax.swing.JScrollPane employeesScrollPane;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        employees = new ArrayList<>();
        employeeStates = new HashMap<>();
        ResultSet rs = SQLManager.getDefault().runQuery("SELECT stagename, loggedin FROM employees WHERE active = 1 ORDER BY stagename");
        try {
            while (rs.next()) {
                String stageName = rs.getString("stagename");
                int loggedIn = rs.getInt("loggedin");
                employees.add(stageName);
                employeeStates.put(stageName, loggedIn == 1 ? Boolean.TRUE : Boolean.FALSE);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Failed to get list of employees for top component", ex);
        }
        
        model = new EmployeeListModel();
        renderer = new EmployeeListRenderer();
        employeesList.setCellRenderer(renderer);
        employeesList.setModel(model);
    }
    
    public void componentRefresh() {
        employees.clear();
        employeeStates.clear();
        ResultSet rs = SQLManager.getDefault().runQuery("SELECT stagename, loggedin FROM employees WHERE active = 1 ORDER BY stagename");
        try {
            while (rs.next()) {
                String stageName = rs.getString("stagename");
                int loggedIn = rs.getInt("loggedin");
                employees.add(stageName);
                employeeStates.put(stageName, loggedIn == 1 ? Boolean.TRUE : Boolean.FALSE);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Failed to get list of employees for top component", ex);
        }
        
        employeesList.validate();
        employeesList.repaint();
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
    
    /**
     * Table model representing club employees. Will be shown as a list.
     * Possible change to a node structure in future?
     */
    private class EmployeeListModel implements ListModel {

        @Override
        public int getSize() {
            return employees.size();
        }

        @Override
        public Object getElementAt(int index) {
            return employees.get(index);
        }

        @Override
        public void addListDataListener(ListDataListener l) {
        }

        @Override
        public void removeListDataListener(ListDataListener l) {
        }
    
    }
    
    /**
     * Temp employee renderer. Testing purposes only.
     */
    private class EmployeeListRenderer extends JLabel implements ListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value != null) {
                setEnabled(list.isEnabled());
                
                // Set logged in/out icon
                boolean loggedin = employeeStates.get(value.toString());
                ImageIcon loggedoutIcon = new ImageIcon(getClass().getResource("/com/dhurd/club/login/core/logged-out.png"));
                ImageIcon loggedinIcon = new ImageIcon(getClass().getResource("/com/dhurd/club/login/core/logged-in.png"));
                if (loggedin) {
                    this.setIcon(loggedinIcon);
                } else {
                    this.setIcon(loggedoutIcon);
                }
                
                setFont(list.getFont());
                // setBackground(list.getBackground()); set the background red if they haven't logged in for a while
                setText(value.toString());
                return this;
            }
            return new JLabel();
        }
    }
}
