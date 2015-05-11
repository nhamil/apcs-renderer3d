/**
 * 
 */
package com.tenikkan.abacus.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;
import java.text.DecimalFormat;

import javax.swing.JFrame;

/**
 * @author Nicholas Hamilton
 *
 */
public final class Console
{   
    public static final int STATS   = 1;
    public static final int TEXT    = 20;
    public static final int INFO    = 11;
    public static final int DEBUG   = 10;
    public static final int WARNING = 100;
    public static final int ERROR   = 101;
    
    private static int curLevel = INFO;
    
    private static JFrame frame;
    private static TextArea text;
    
    private static int line = 0;
    
    private static DecimalFormat fmt = new DecimalFormat("0000");
    
    static 
    {
        frame = new JFrame("Console");
        
        text = new TextArea("", 25, 80, TextArea.SCROLLBARS_BOTH);
        text.setEditable(false); 
        text.setFont(new Font("monospaced", Font.BOLD, 12));
        
        text.setBackground(Color.BLACK);
        text.setForeground(Color.GRAY);
        
        frame.add(text);
        frame.pack();
    }
    
    private Console() {}
    
    public static int getLevel() { return curLevel; }
    public static void setLevel(int level) { curLevel = level; }
    
    public static void show() 
    {
        frame.setVisible(true);
    }
    
    public static void hide() 
    {
        frame.setVisible(false);
    }
    
    public static void outln(Object o, int level) 
    {
        if(level >= curLevel) 
        {
            addText(o.toString() + "\n", level);
        }
    }
    
    public static void outln(Object o) { outln(o, TEXT); }
    
    public static void outln() { text.append("\n"); }
    
    private static void addText(String str, int level) 
    {
        String[] strs = str.split("\n");
        
        String prefix = "";
        
        switch(level) 
        {
        case INFO: prefix =  "[INFO] :"; break;
        case DEBUG: prefix = "[DEBUG] :"; break;
        case ERROR: prefix = "[ERROR] :"; break;
        case WARNING: prefix = "[WARNING] :"; break;
        case STATS: prefix = "[STATS] :"; break;
        default: prefix =    "[TEXT] :"; break;
        }
        
        for(String s : strs) 
        {
            text.append(fmt.format(line++) + " " + prefix + " " + s + "\n");
        }
        
        
    }
}
