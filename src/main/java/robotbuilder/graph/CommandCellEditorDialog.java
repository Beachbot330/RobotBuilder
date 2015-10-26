
package robotbuilder.graph;

import java.awt.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import lombok.Getter;

import robotbuilder.CenteredDialog;
import robotbuilder.MainFrame;
import robotbuilder.ParameterEditorTable;
import robotbuilder.Utils;
import robotbuilder.data.CommandGroupEntry;
import robotbuilder.data.RobotComponent;
import robotbuilder.data.properties.ParametersProperty;
import robotbuilder.data.properties.ValuedParameterDescriptor;

/**
 * A dialog for editing commands in a command group.
 *
 * @author Sam Carlberg
 */
public class CommandCellEditorDialog extends CenteredDialog {

    private final CommandGraph graph;

    /**
     * The command being edited.
     */
    @Getter
    private final CommandGroupEntry command;

    /**
     * The parameters of the command being edited; these are just for
     * convenience.
     */
    private final ParametersProperty parameters;
    private final List<ValuedParameterDescriptor> parameterList;

    /**
     * Was the data saved?
     */
    private boolean didSave = false;

    public CommandCellEditorDialog(CommandGraph graph, CommandCell command) {
        super(MainFrame.getInstance(), command.getValue().getName());
        this.graph = graph;
        this.command = Utils.deepCopy(command.getValue());
        parameters = this.command.getParameters();
        parameterList = (List<ValuedParameterDescriptor>) parameters.getValue();
        initComponents();
        parameterTable.setShowHorizontalLines(true);
        parameterTable.setShowVerticalLines(true);
        parameterTable.setRowHeight(25);
        parameterTable.setBackground(new Color(240, 240, 240));
        parameterTable.setGridColor(Color.BLACK);
        parameterList.forEach(param -> parameterTable.getModel().addRow(param.toArray()));
    }

    public void save() {
        // Save the parameters
        Vector<Vector<Object>> dataVector = parameterTable.getModel().getDataVector();
        List<ValuedParameterDescriptor> params = new ArrayList<>();
        dataVector.stream().forEach((dataRow) -> {
            String name = (String) dataRow.get(0);
            ValuedParameterDescriptor existing = parameters.getParameterByName(name);
            existing.setValue(dataRow.get(2));
            params.add(existing);
        });

        parameters.setValueAndUpdate(params);

        // Save the order
        command.setOrder((String) orderBox.getSelectedItem());

        // Save the graph
        graph.save();
        didSave = true;
    }

    public boolean didSave() {
        return didSave;
    }

    /**
     * Autogenerated by the Netbeans form builder
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        saveButton = new JButton();
        orderLabel = new JLabel();
        orderBox = new JComboBox();
        tableScrollPane = new JScrollPane();

        RobotComponent subsystem = MainFrame.getInstance().getCurrentRobotTree()
                .getComponentByName((String) command.getCommand().getProperty("Requires").getValue());

        parameterTable = new ParameterEditorTable(subsystem.getName(), (List) subsystem.getProperty("Constants").getValue());

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        saveButton.setText("Save and close");
        saveButton.addActionListener(this::saveButtonActionPerformed);

        orderLabel.setText("Order:");

        orderBox.setModel(new DefaultComboBoxModel(new String[]{CommandGroupEntry.SEQUENTIAL, CommandGroupEntry.PARALLEL}));
        orderBox.setSelectedItem(command.getOrder());

        // there's a bug where, if the combobox has focus when it's clicked,
        // it won't show the correct value until it loses focus
        orderBox.setFocusable(false);

        tableScrollPane.setViewportView(parameterTable);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(saveButton))
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(orderLabel)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(orderBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 131, Short.MAX_VALUE))
                                .addComponent(tableScrollPane, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(orderLabel)
                                .addComponent(orderBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tableScrollPane, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveButton)
                        .addContainerGap())
        );

        pack();
    }// </editor-fold>

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {
        save();
        dispose();
    }

    // Variables declaration - do not modify
    private JScrollPane tableScrollPane;
    private JComboBox orderBox;
    private JLabel orderLabel;
    private ParameterEditorTable parameterTable;
    private JButton saveButton;
    // End of variables declaration
}
