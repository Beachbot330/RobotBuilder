
package robotbuilder;

import com.sun.org.apache.xalan.internal.xsltc.dom.CurrentNodeListFilter;
import java.awt.BorderLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import robotbuilder.data.PaletteComponent;
import robotbuilder.data.RobotComponent;

/**
 *
 * @author brad
 * RobotTree is the tree representation of the robot map. It will contain
 * nodes that represent the various components that can be used to build
 * the robot. This is constructed by the user by dragging palette items to the tree.
 */
class RobotTree extends JPanel implements TreeSelectionListener {
    
    private JTree tree;
    private DefaultTreeModel treeModel;
    private PropertiesDisplay properties;
    
    /** Names used by components during name auto-generation */
    private Set<String> usedNames = new HashSet<String>();
    /** The currently selected node */
    private DefaultMutableTreeNode currentNode;
    
    public RobotTree(PropertiesDisplay properties) {
	this.properties = properties;
        setLayout(new BorderLayout());
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Team190Robot");
        DefaultMutableTreeNode motors = new DefaultMutableTreeNode("Motors");
        motors.add(new DefaultMutableTreeNode("Left Front"));
        motors.add(new DefaultMutableTreeNode("Right Front"));
        root.add(motors);
        treeModel = new DefaultTreeModel(root);
        tree = new JTree(treeModel);
	tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	tree.addTreeSelectionListener(this);
        tree.setDropMode(DropMode.ON_OR_INSERT);
        add(new JScrollPane(tree), BorderLayout.CENTER);

        tree.setTransferHandler(new TreeTransferHandler(tree.getTransferHandler()));
        tree.setDragEnabled(true);
    }
    
    /**
     * @param component The type of component to generate a default name for.
     * @return The default name.
     */
    private String getDefaultComponentName(PaletteComponent componentType) {
        int i = 0;
        String name;
        while (true) {
            i++;
            name = componentType.toString() + " " + i;
            if (!usedNames.contains(name)) {
                usedNames.add(name);
                return name;
            }
        }
    }

    @Override
    public void valueChanged(TreeSelectionEvent tse) {
	DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                       tree.getLastSelectedPathComponent();

	if (node == null) return;
	if (node instanceof DefaultMutableTreeNode) {
	    properties.setCurrentComponent(node);
            currentNode = node;
        }
    }

    public static DataFlavor ROBOT_COMPONENT_FLAVOR = new DataFlavor(RobotComponent.class, "Robot Component Flavor");
    /**
     * A transfer handler for that wraps the default transfer handler of RobotTree.
     * 
     * @author Alex Henning
     */
    private class TreeTransferHandler extends TransferHandler {
        private TransferHandler delegate;
        
        public TreeTransferHandler(TransferHandler delegate) {
            this.delegate = delegate;
        }
        
        @Override
        public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
            return delegate.canImport(comp, transferFlavors);
        }
        
        @Override
        public boolean canImport(TransferSupport support) {
            if (!support.isDrop()) return false;
            support.setShowDropLocation(true);
            if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)
                    && !support.isDataFlavorSupported(ROBOT_COMPONENT_FLAVOR)) {
                System.out.println("Unsupported flavor. The flavor you have chosen is no sufficiently delicious.");
                return false;
            }
            JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
            TreePath path = dl.getPath();
            if (path == null) return false;
            return true;
            //return true;
        }
        
        @Override
        protected Transferable createTransferable(final JComponent c) {
            return new Transferable() {
                DataFlavor[] flavors = {ROBOT_COMPONENT_FLAVOR};
                
                @Override
                public DataFlavor[] getTransferDataFlavors() {
                    return flavors;
                }

                @Override
                public boolean isDataFlavorSupported(DataFlavor df) {
                    for (DataFlavor flavor : flavors) {
                        if (flavor.equals(df)) return true;
                    }
                    return false;
                }

                @Override
                public Object getTransferData(DataFlavor df) throws UnsupportedFlavorException, IOException {
                    return currentNode.getUserObject();
                }
            };
//            try {
//                Method method = delegate.getClass().getDeclaredMethod("createTransferable", JComponent.class);
//                method.setAccessible(true);
//                return (Transferable) method.invoke(delegate, c);
//            } catch (Exception e) {
//                return super.createTransferable(c);
//            }
        }
        
        @Override
        public void exportAsDrag(JComponent comp, InputEvent event, int action) {
            delegate.exportAsDrag(comp, event, action);
        }
        
        @Override
        protected void exportDone(JComponent source, Transferable data, int action) {
            System.out.println(source.getClass());
            currentNode.removeFromParent();
            ((DefaultTreeModel) tree.getModel()).reload();
            //((MutableTreeNode) currentNode.getParent()).remove(currentNode);
//            try {
//                Method method = delegate.getClass().getDeclaredMethod("exportDone", JComponent.class, Transferable.class,
//                        int.class);
//                method.setAccessible(true);
//                method.invoke(delegate, source, data, action);
//            } catch (Exception e) {
//            }
//            }
        }
        
        @Override
        public int getSourceActions(JComponent c) {
            //return COPY_OR_MOVE;
            return delegate.getSourceActions(c);
        }
        
        @Override
        public Icon getVisualRepresentation(Transferable t) {
            return delegate.getVisualRepresentation(t);
        }
        
        @Override
        public boolean importData(JComponent comp, Transferable t) {
            return delegate.importData(comp, t);
        }
        
        @Override
        public boolean importData(TransferHandler.TransferSupport support) {
            if (!canImport(support))
                return false;
            JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
            TreePath path = dl.getPath();
            int childIndex = dl.getChildIndex();
            if (childIndex == -1) {
                childIndex = tree.getModel().getChildCount(path.getLastPathComponent());
            }
            DefaultMutableTreeNode newNode;
            if (support.getTransferable().isDataFlavorSupported(DataFlavor.stringFlavor)) {
                String data;
                try {
                    data = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
                } catch (UnsupportedFlavorException e) {
                    return false;
                } catch (IOException e) {
                    return false;
                }
                System.out.println("Data: " + data);
                PaletteComponent base = Palette.getInstance().getItem(data);
                assert base != null; // TODO: Handle more gracefully
                newNode = new DefaultMutableTreeNode(new RobotComponent(getDefaultComponentName(base), base));
            } else if (support.getTransferable().isDataFlavorSupported(ROBOT_COMPONENT_FLAVOR)) {
                System.out.println("Importing a robot component");
                RobotComponent data;
                try {
                    data = (RobotComponent) support.getTransferable().getTransferData(ROBOT_COMPONENT_FLAVOR);
                } catch (UnsupportedFlavorException e) {
                    System.out.println("UnsupportedFlavor");
                    return false;
                } catch (IOException e) {
                    System.out.println("IOException");
                    return false;
                }
                System.out.println("Imported a robot component: "+data.toString());
                newNode = new DefaultMutableTreeNode(data);
            } else {
                return false;
            }
            DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)path.getLastPathComponent();
            treeModel.insertNodeInto(newNode, parentNode, childIndex);
            tree.makeVisible(path.pathByAddingChild(newNode));
            tree.scrollRectToVisible(tree.getPathBounds(path.pathByAddingChild(newNode)));
            return true;
            //return delegate.importData(support);
        }
    }
}