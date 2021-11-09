package org.alozano;

import com.formdev.flatlaf.FlatDarculaLaf;

public class App
{

    public static void main( String[] args )
    {

        FlatDarculaLaf.setup();

        new Window().setVisible(true);

    }
}
