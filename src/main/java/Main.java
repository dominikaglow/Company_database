import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {

    public static void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15;
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            if(width > 300)
                width=300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    public static void main(String[] args) {
        Management obj = new Management();
        Scanner sc = new Scanner(System.in);

        JFrame frame = new JFrame("MENU");
        frame.setLayout(new FlowLayout());
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar menu = new JMenuBar();
        frame.setJMenuBar(menu);

        JMenu employee = new JMenu("Employee");
        menu.add(employee);
        JMenuItem addEmp = new JMenuItem(new AbstractAction("Add employee") {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                JLabel info1 = new JLabel("Enter name: ");
                info1.setAlignmentX(Component.CENTER_ALIGNMENT);
                info1.setBounds(50,50, 200,30);
                info1.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
                panel.add(info1);
                JTextField jf1 = new JTextField(15);
                jf1.setBounds(50, 100, 100, 30);
                jf1.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
                jf1.setToolTipText("Enter name here...");
                panel.add(jf1);

                JLabel info2 = new JLabel("Enter surname: ");
                info2.setAlignmentX(Component.CENTER_ALIGNMENT);
                info2.setBounds(50,50, 200,30);
                info2.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
                panel.add(info2);
                JTextField jf2 = new JTextField(15);
                jf2.setBounds(50, 100, 100, 30);
                jf2.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
                jf2.setToolTipText("Enter surname here...");
                panel.add(jf2);

                JLabel info3 = new JLabel("Enter position ID: ");
                info3.setAlignmentX(Component.CENTER_ALIGNMENT);
                info3.setBounds(50,50, 200,30);
                info3.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
                panel.add(info3);
                JTextField jf3 = new JTextField(15);
                jf3.setBounds(50, 100, 100, 30);
                jf3.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
                jf3.setToolTipText("Enter position ID here...");
                panel.add(jf3);

                Map<String, String> mapPos = obj.printPos(); // map with position id and position name
                JTextArea textArea3 = new JTextArea();
                textArea3.setEditable(false);
                for (Map.Entry<String, String> entry : mapPos.entrySet()) {
                    textArea3.append(entry.getKey() + " -> " + entry.getValue() + "\n");
                }
                panel.add(new JScrollPane(textArea3));

                JLabel info4 = new JLabel("Enter department ID: ");
                info4.setAlignmentX(Component.CENTER_ALIGNMENT);
                info4.setBounds(50,50, 200,30);
                info4.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
                panel.add(info4);
                JTextField jf4 = new JTextField(15);
                jf4.setBounds(50, 100, 100, 30);
                jf4.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
                jf4.setToolTipText("Enter department ID here...");
                panel.add(jf4);

                JTextArea textArea1 = new JTextArea();
                textArea1.setEditable(false); // make it read-only
                List<Department> mapDep = obj.printDep();
                for(int i = 0; i < mapDep.size(); i++){
                    int id = mapDep.get(i).id;
                    String name = mapDep.get(i).name;
                    textArea1.append(id + " -> " + name + "\n");
                }
                panel.add(new JScrollPane(textArea1));

                JLabel info5 = new JLabel("Enter email: ");
                info5.setAlignmentX(Component.CENTER_ALIGNMENT);
                info5.setBounds(50,50, 200,30);
                info5.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
                panel.add(info5);
                JTextField jf5 = new JTextField(15);
                jf5.setBounds(50, 100, 250, 30);
                jf5.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
                jf5.setToolTipText("Enter email here...");
                panel.add(jf5);

                JLabel info6 = new JLabel("Enter phone number: ");
                info6.setAlignmentX(Component.CENTER_ALIGNMENT);
                info6.setBounds(50,50, 200,30);
                info6.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
                panel.add(info6);
                JTextField jf6 = new JTextField(15);
                jf6.setBounds(50, 100, 100, 30);
                jf6.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
                jf6.setToolTipText("Enter phone number here...");
                panel.add(jf6);

                JButton button = new JButton("Add employee");
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                button.setPreferredSize(new Dimension(120, 70));
                panel.add(button);

                frame.add(panel);

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = jf1.getText().trim();
                        String surname = jf2.getText().trim();
                        int position = Integer.parseInt(jf3.getText().trim());
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        int department = Integer.parseInt(jf4.getText().trim());
                        String email = jf5.getText().trim();
                        String phoneNum = jf6.getText().trim();

                        obj.insertEmployee(name, surname, position, dtf.format(now), department, email, phoneNum);
                    }
                });
            }
        });

        employee.add(addEmp);
        JMenuItem printEmp = new JMenuItem(new AbstractAction("Print list of employees") {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                JTable table;
                List<Employee> empList;
                empList = obj.printEmp();
                int size = empList.size();
                Object data[][] = new Object[size][8];
                for(int i = 0; i < empList.size(); i++){
                    data[i][0] = empList.get(i).num;
                    data[i][1] = empList.get(i).name;
                    data[i][2] = empList.get(i).surname;
                    data[i][3] = empList.get(i).pos;
                    data[i][4] = empList.get(i).date;
                    data[i][5] = empList.get(i).dep;
                    data[i][6] = empList.get(i).email;
                    data[i][7] = empList.get(i).phoneNum;
                }
                String[] columnNames = { "ID", "NAME", "SURNAME", "POSITION", "DATE", "DEPARTMENT", "EMAIL", "PHONE_NUMBER"};
                table = new JTable(data, columnNames) {
                    @Override
                    public Dimension getPreferredScrollableViewportSize() {
                        return new Dimension(super.getPreferredSize().width,
                                getRowHeight() * getRowCount());
                    }
                };
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table.setRowHeight(30);

                resizeColumnWidth(table);

                JScrollPane sp = new JScrollPane(table);

                frame.add(sp);
            }
        });
        employee.add(printEmp);

        JMenu position = new JMenu("Position");
        menu.add(position);
        JMenuItem addPos = new JMenuItem(new AbstractAction("Add position") {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                JLabel info1 = new JLabel("Enter new position: ");
                info1.setAlignmentX(Component.CENTER_ALIGNMENT);
                info1.setBounds(50,50, 200,30);
                info1.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
                panel.add(info1);
                JTextField jf1 = new JTextField(15);
                jf1.setBounds(50, 100, 100, 30);
                jf1.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
                jf1.setToolTipText("Enter new position here...");
                panel.add(jf1);

                JButton button = new JButton("Add position");
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                button.setPreferredSize(new Dimension(120, 70));
                panel.add(button);

                frame.add(panel);

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.getContentPane().removeAll();
                        String pos = jf1.getText().trim(); // position name
                        obj.insertPosition(pos);
                    }
                });
            }
        });
        position.add(addPos);

        JMenuItem printPos = new JMenuItem(new AbstractAction("Print list of positions") {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                JTable table;
                Map<String, String> posMap;
                posMap = obj.printPos();
                int size = posMap.size();
                Object data[][] = new Object[size][2];
                Set entries = posMap.entrySet();
                Iterator entriesIterator = entries.iterator();

                int i = 0;
                while(entriesIterator.hasNext()){
                    Map.Entry mapping = (Map.Entry) entriesIterator.next();
                    data[i][0] = mapping.getKey();
                    data[i][1] = mapping.getValue();
                    i++;
                }

                String[] columnNames = { "ID", "POSITION"};
                table = new JTable(data, columnNames) {
                    @Override
                    public Dimension getPreferredScrollableViewportSize() {
                        return new Dimension(super.getPreferredSize().width,
                                getRowHeight() * getRowCount());
                    }
                };
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table.setRowHeight(30);
                resizeColumnWidth(table);

                JScrollPane sp = new JScrollPane(table);
                frame.add(sp);
            }
        });
        position.add(printPos);

        JMenu department = new JMenu("Department");
        menu.add(department);
        JMenuItem addDep = new JMenuItem(new AbstractAction("Add department") {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                JLabel info1 = new JLabel("Enter name for department: ");
                info1.setAlignmentX(Component.CENTER_ALIGNMENT);
                info1.setBounds(50,50, 200,30);
                info1.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
                panel.add(info1);
                JTextField jf1 = new JTextField(15);
                jf1.setBounds(50, 100, 100, 30);
                jf1.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
                jf1.setToolTipText("Enter name for department here...");
                panel.add(jf1);

                obj.printLocation(); // displaying available locations

                JLabel info3 = new JLabel("Select the location id for department: ");
                info3.setAlignmentX(Component.CENTER_ALIGNMENT);
                info3.setBounds(50, 250, 200, 30);
                info3.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
                panel.add(info3);
                JTextField jf2 = new JTextField(15);
                jf2.setBounds(50, 300, 133, 30);
                jf2.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
                jf2.setToolTipText("Enter ID here...");
                panel.add(jf2);

                JButton button = new JButton("Add department");
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                button.setPreferredSize(new Dimension(140, 70));
                panel.add(button);

                frame.add(panel);


                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = jf1.getText().trim(); // department name
                        String locID = jf2.getText().trim(); // location
                        obj.insertDepartment(name, locID);
                    }
                });
                frame.revalidate();
                frame.repaint();
            }
        });
        department.add(addDep);

        JMenuItem printDep = new JMenuItem(new AbstractAction("Print list of departments") {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                JTable table;
                List<Department> depList;
                depList = obj.printDep();
                int size = depList.size();
                Object data[][] = new Object[size][3];

                for(int i = 0; i < depList.size(); i++){
                    data[i][0] = depList.get(i).id;
                    data[i][1] = depList.get(i).name;
                    data[i][2] = depList.get(i).loc;
                }

                String[] columnNames = { "ID", "DEPARTMENT", "LOCATION"};
                table = new JTable(data, columnNames) {
                    @Override
                    public Dimension getPreferredScrollableViewportSize() {
                        return new Dimension(super.getPreferredSize().width,
                                getRowHeight() * getRowCount());
                    }
                };
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table.setRowHeight(30);
                resizeColumnWidth(table);

                JScrollPane sp = new JScrollPane(table);
                frame.add(sp);
            }
        });
        department.add(printDep);

        JMenu location = new JMenu("Location");
        menu.add(location);
        JMenuItem addLoc = new JMenuItem(new AbstractAction("Add location") {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                JLabel info1 = new JLabel("Enter new location: ");
                info1.setAlignmentX(Component.CENTER_ALIGNMENT);
                info1.setBounds(50,50, 200,30);
                info1.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
                panel.add(info1);
                JTextField jf1 = new JTextField(15);
                jf1.setBounds(50, 100, 100, 30);
                jf1.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
                jf1.setToolTipText("Enter name for department here...");
                panel.add(jf1);

                JTextArea textArea2 = new JTextArea();
                textArea2.setEditable(false);
                Map<Integer, String> mapLoc = obj.printLocation();
                for (Map.Entry<Integer, String> entry : mapLoc.entrySet()) {
                    textArea2.append(entry.getKey() + " -> " + entry.getValue() + "\n");
                }
                panel.add(new JScrollPane(textArea2));

                JButton button = new JButton("Add location");
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                button.setPreferredSize(new Dimension(120, 70));
                panel.add(button);

                frame.add(panel);

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String location = jf1.getText().trim(); //location
                        obj.insertLocation(location);
                    }
                });
            }
        });
        location.add(addLoc);
        JMenuItem printLoc = new JMenuItem(new AbstractAction("Print list of locations") {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();

                JTable table;
                Map<Integer, String> locMap;
                locMap = obj.printLocation();
                int size = locMap.size();
                Object data[][] = new Object[size][2];
                Set entries = locMap.entrySet();
                Iterator entriesIterator = entries.iterator();

                int i = 0;
                while(entriesIterator.hasNext()){
                    Map.Entry mapping = (Map.Entry) entriesIterator.next();
                    data[i][0] = mapping.getKey();
                    data[i][1] = mapping.getValue();
                    i++;
                }

                String[] columnNames = { "ID", "LOCATION"};
                table = new JTable(data, columnNames) {
                    @Override
                    public Dimension getPreferredScrollableViewportSize() {
                        return new Dimension(super.getPreferredSize().width,
                                getRowHeight() * getRowCount());
                    }
                };
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table.setRowHeight(30);
                resizeColumnWidth(table);

                JScrollPane sp = new JScrollPane(table);
                frame.add(sp);
            }
        });
        location.add(printLoc);

        frame.setVisible(true);
    }
}