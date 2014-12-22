/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Toolspkg;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Doddc
 */
public class TableTools {
   public static void adjustTableColumnWidths(JTable table) 
    {
        JTableHeader header = table.getTableHeader();  //表头
        int rowCount = table.getRowCount();  //表格的行数
        TableColumnModel cm = table.getColumnModel();  //表格的列模型

        for (int i = 0; i < cm.getColumnCount(); i++) {  //循环处理每一列
            TableColumn column = cm.getColumn(i);          //第i个列对象
            int width = (int)header.getDefaultRenderer().getTableCellRendererComponent(table, column.getIdentifier(), false, false, -1, i).getPreferredSize().getWidth();  //用表头的绘制器计算第i列表头的宽度
            for(int row = 0; row<rowCount; row++){  //循环处理第i列的每一行，用单元格绘制器计算第i列第row行的单元格宽度
        int preferedWidth = (int)table.getCellRenderer(row, i).getTableCellRendererComponent(table, table.getValueAt(row, i), false, false, row, i).getPreferredSize().getWidth();
        width = Math.max(width, preferedWidth);  //取最大的宽度
             }
            column.setPreferredWidth(width+table.getIntercellSpacing().width);  //设置第i列的首选宽度
    }

    table.doLayout();   //按照刚才设置的宽度重新布局各个列
}
}
