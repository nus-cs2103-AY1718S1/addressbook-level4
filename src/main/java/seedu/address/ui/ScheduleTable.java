package seedu.address.ui;

import seedu.address.commons.core.PossibleTimes;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Time;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import java.util.TreeSet;


public class ScheduleTable extends JFrame {
    public ScheduleTable(Schedule schedule) {
        TreeSet[] timeSetArray = schedule.splitScheduleToDays();
        construct(timeSetArray);
    }

    public ScheduleTable(TreeSet[] timeSetArray) {
        construct(timeSetArray);
    }

    private void construct(TreeSet[] timeSetArray) {
        String[] columnNames = {"", "Monday", "Tuesday" , "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
        Object[][] data = new Object[36][8];
        for (int s = 0; s < 36; s++) {
            for (int t = 0; t < 8; t++) {
                data[s][t] = "";
            }
        }
        for (int i = 0; i < PossibleTimes.TIMES.length; i++) {
            data[i][0] = Time.getTimeToString(PossibleTimes.TIMES[i])
                    + "--" + Time.increaseTimeInteger(PossibleTimes.TIMES[i]);
        }
        for (int j = 1; j <= timeSetArray.length; j++) {
            for (int k = 0; k < PossibleTimes.TIMES.length; k++) {
                if (timeSetArray[j-1].contains(PossibleTimes.TIMES[k])) {
                    data[k][j] = "AVAILABLE";
                } else {
                    data[k][j] = "N";
                }
            }
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable( model )
        {
            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
            public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };
        table.setShowHorizontalLines(true);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());

        JScrollPane scrollPane = new JScrollPane( table );
        getContentPane().add( scrollPane );

    }

    public static void generates(Schedule schedule) {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        ScheduleTable frame = new ScheduleTable(schedule);
        frame.setDefaultCloseOperation( HIDE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void generates(TreeSet[] timeSetArray) {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        ScheduleTable frame = new ScheduleTable(timeSetArray);
        frame.setDefaultCloseOperation( HIDE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
