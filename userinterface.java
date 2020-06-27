import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;



public class userinterface extends JFrame implements ActionListener{

    String[] commissionName = new String[100];      // Holds up to 100 values for commission
    int[] commissionValue = new int[100];           // Holds up to 100 values for the value of the commission


    private
        JLabel title;
        JLabel tableLabel;
        JLabel transactionDetailLabel;

        JButton addCommissionTable;
        JButton addTransactionDetail;
        JButton execute;

        /*
        // Storing the commission table
        String[] commissionName = new String[100];      // Holds up to 100 values for commission
        int[] commissionValue = new int[100];           // Holds up to 100 values for the value of the commission

        String key = "";
        int value = 0;
        Map<String, Integer> knownGoodMap = new LinkedHashMap<String, Integer>();
         */

    public userinterface(){
        super("Commission Interface");


        // Declarations of variables

        final boolean shouldFill = true;
        final boolean shouldWeightX = true;
        final boolean RIGHT_TO_LEFT = false;

        String key = "";
        int value = 0;
        Map<String, Integer> knownGoodMap = new LinkedHashMap<String, Integer>();




        // Setting the GUI
        setSize(500,  600);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        if(shouldFill){
            // natural height, maximum width
            c.fill = GridBagConstraints.HORIZONTAL;
        }

        // Title
        title = new JLabel ("Employee Commission");
        title.setToolTipText("This is gonna show up on hover");
        title.setHorizontalAlignment(JLabel.CENTER);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 100;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        add(title, c);


        // Resets value of ipady and gridwidth
        c.ipady = 0;
        c.gridwidth = 1;


        // Enter Commission Table
        tableLabel = new JLabel ("Commission Table File: ");
        tableLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START; //start of space
        c.gridx = 0;
        c.gridy = 1;
        add(tableLabel, c);
        // Commission Table Button
        addCommissionTable = new JButton("Add file...");
        addCommissionTable.addActionListener(this::actionPerformedCommissionTable);             //ActionListener
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        add(addCommissionTable, c);

        // Enter the Transaction Detail File
        transactionDetailLabel = new JLabel ("Transaction Detail File: ");
        transactionDetailLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START; //start of space
        c.gridx = 0;
        c.gridy = 2;
        add(transactionDetailLabel, c);
        //Transaction Detail Button
        addTransactionDetail = new JButton("Add file...");
        addTransactionDetail.addActionListener(this::actionPerformed);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        add(addTransactionDetail, c);

        //Execute Button
        execute = new JButton ("Execute");
        execute.addActionListener(this::actionExecute);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.insets = new Insets(10,0,0,0);  //top padding
        add(execute, c);

    }

    //The action performed when Commission Table file is selected
    public void actionPerformedCommissionTable(ActionEvent e){

        JFileChooser fs = new JFileChooser();
        fs.setFileFilter(new FileNameExtensionFilter("Excel File","xlsx"));

        if(fs.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            FileInputStream commissionTable = null;
            File fi = fs.getSelectedFile();
            try {
                commissionTable = new FileInputStream(fi);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = null;
            try {
                workbook = new XSSFWorkbook(commissionTable);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            // Create a DataFormatter to format and get each cell's value as String
            DataFormatter dataFormatter = new DataFormatter();


            // 1. You can obtain a rowIterator and columnIterator and iterate over them

            /*
            // Decide which rows to process
            int rowStart = Math.min(15, sheet.getFirstRowNum());
            int rowEnd = Math.max(1400, sheet.getLastRowNum());
            for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
               Row r = sheet.getRow(rowNum);
               if (r == null) {
                  // This whole row is empty
                  // Handle it as needed
                  continue;
               }
               int lastColumn = Math.max(r.getLastCellNum(), MY_MINIMUM_COLUMN_COUNT);
               for (int cn = 0; cn < lastColumn; cn++) {
                  Cell c = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
                  if (c == null) {
                     // The spreadsheet is empty in this cell
                  } else {
                     // Do something useful with the cell's contents
                  }
               }
            }


             */

            // Need EXT price Accessory to calculate

            int i = 0;
            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                // Now let's iterate over the columns of the current row
                Iterator<Cell> cellIterator = row.cellIterator();
                if(row != null){
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();

                        // Here need to put the value into a hashtable
                        String cellValue =dataFormatter.formatCellValue(cell);
                        //System.out.print(cellValue + "\t");

                        commissionName[i] = cellValue;
                        System.out.print("i: " + commissionName[i] + " ");
                        i++;
                    }
                }
                System.out.println();
            }
            try {
                commissionTable.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //The action performed when .xlsx file is selected
    public void actionPerformed(ActionEvent e) {

        JFileChooser fs1 = new JFileChooser();
        fs1.setFileFilter(new FileNameExtensionFilter("Excel File","xlsx"));

        if(fs1.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            //String content = textContent.getText();

            FileInputStream transactionDetail = null;
            File fi1 = fs1.getSelectedFile();
            try {
                transactionDetail = new FileInputStream(fi1);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = null;
            try {
                workbook = new XSSFWorkbook(transactionDetail);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            // Create a DataFormatter to format and get each cell's value as String
            DataFormatter dataFormatter = new DataFormatter();

            // 1. You can obtain a rowIterator and columnIterator and iterate over them
            System.out.println("\n\nIterating over Rows and Columns using Iterator\n");
            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                // Now let's iterate over the columns of the current row
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    String cellValue = dataFormatter.formatCellValue(cell);
                    System.out.print(cellValue + "\t");
                }
                System.out.println();
            }
            try {
                transactionDetail.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void actionExecute(ActionEvent e){

        // Need to see if spiff name from table match with the ones in the column in the transaction detail
        // If there is a match, then store in an array the line
        // Once completed, loop to append new information into a new excel
        /* BONUS - STORE THE USER NAME AND JUST OUTPUT THE TOTAL HE/SHE GETS */


        //

    }
}
