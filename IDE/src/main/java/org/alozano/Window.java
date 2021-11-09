package org.alozano;

import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.tree.TreePath;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Window extends JFrame {

    private JPanel panelPrincipal;
    private JTextArea areaTrabajo;
    private JTextArea areaCompilacion;
    //private JTextArea ubicacion;
    private JTree gestorArchivos;
    private JScrollPane scrollTrabajo;
    private JScrollPane scrollCompilacion;
    private JToolBar herramientas;
    private String abierto = null;
    private UndoManager deshacer;
    private JMenuBar menu;
    private JMenu archivo;
    private JMenuItem crearArchivo;
    private JMenuItem abrirArchivo;
    private JMenuItem guardar;
    private JMenuItem guardarArchivo;
    private JMenuItem cerrarArchivo;
    //---------------------------------
    private JMenu editar;
    private JMenuItem atras;
    private JMenuItem alante;
    private JMenuItem copiar;
    private JMenuItem pegar;
    private JMenuItem eliminar;
    //---------------------------------
    private JMenu codigo;
    private JMenuItem ejecutar;
    private JMenuItem compilar;
    //------------------------------------
    private JMenu ajustes;
    private JMenuItem colorFondo;
    private JMenuItem colorTexto;
    private JMenuItem imprimir;
    private JMenuItem salir;
    //------------------------------------
    private JMenu ayuda;
    private JMenuItem infoDelIDE;
    private JMenuItem infoDelCreador;

    public Window(){  //Constructor
        initComponents();
        getIconImage();
        //setResizable(false);
        //String logo = System.getProperty("user.dir") + File.separator + "icon" + File.separator+"logo.png";
        //setIconImage(new ImageIcon(getClass().getResource(logo)).getImage()); //Icono del programa
        setLocationRelativeTo ( null ) ;
        setTitle ( "MIDE La Interfeca Grafica de Adrian Lozano" ) ;
    }


    private void initComponents() {
        //Inicializo las variables adecuadas y opcionalmente añado un icono
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        setPreferredSize(new Dimension(1000, 1000)); //Tamaño
        //JPanel
        panelPrincipal = new JPanel();   //Creo un nuevo contenedor de tipo panel y lo inicializo
        //panelPrincipal.setPreferredSize(new Dimension(100,100));
        //this.getContentPane().setBackground(Color.GREEN); //Color de fondo JFrame
        //panelPrincipal.setBackground(Color.BLUE); //Color de fondo JPanel
        panelPrincipal.setLayout(new BorderLayout());
        add(panelPrincipal);     //Lo añado a la ventana CON ADD SOLO SE AÑADE AL JFRAME

        //----------------------------------------------------MENU------------------------------------------------------
        //Barra superior de los menús
        menu = new JMenuBar();
        //Se instancia el JMenu
        archivo = new JMenu();
        editar = new JMenu();
        codigo = new JMenu();
        ajustes = new JMenu();
        ayuda = new JMenu();
        //Se da nombre al JMenu
        archivo.setText("Archivo");
        editar.setText("Editar");
        codigo.setText("Codigo");
        ajustes.setText("Ajustes");
        ayuda.setText("Ayuda");
        //Se instancia el JMenuItem
        //-------------------------------- ARCHIVO
        crearArchivo = new JMenuItem();
        abrirArchivo = new JMenuItem();
        guardar = new JMenuItem();
        guardarArchivo = new JMenuItem();
        cerrarArchivo = new JMenuItem();
        //--------------------------------- EDITAR
        atras = new JMenuItem();
        alante = new JMenuItem();
        copiar = new JMenuItem();
        pegar = new JMenuItem();
        eliminar = new JMenuItem();
        //---------------------------------- CODIGO
        ejecutar = new JMenuItem();
        compilar = new JMenuItem();
        //---------------------------------- AJUSTES
        colorFondo = new JMenuItem();
        colorTexto = new JMenuItem();
        imprimir = new JMenuItem();
        salir = new JMenuItem();
        //---------------------------------- AYUDA
        infoDelIDE = new JMenuItem();
        infoDelCreador = new JMenuItem();
        //Se nombra el JMenuItem
        //-------------------------------------------- ARCHIVO
        crearArchivo.setText("Nuevo");
        abrirArchivo.setText("Abrir");
        guardar.setText("Guardar");
        guardarArchivo.setText("Guardar Como ...");
        cerrarArchivo.setText("Cerrar");
        //------------------------------------------ EDITAR
        atras.setText("Atras");
        alante.setText("Alante");
        copiar.setText("Copiar");
        pegar.setText("Pegar");
        eliminar.setText("Vaciar");
        //------------------------------------------ CODIGO
        ejecutar.setText("Ejecutar");
        compilar.setText("Depurar");
        //------------------------------------------ AJUSTES
        colorFondo.setText("Cambia el Color Fondo");
        colorTexto.setText("Cambia el Color Texto");
        imprimir.setText("Imprimir");
        salir.setText("Salir");
        //------------------------------------------ AYUDA
        infoDelIDE.setText("Info");
        infoDelCreador.setText("Creador");
        //Se añaden los JMenuItem al JMenu
        //------------------------------------- ARCHIVO
        archivo.add(crearArchivo);
        archivo.add(abrirArchivo);
        archivo.add(guardar);
        archivo.add(guardarArchivo);
        archivo.add(cerrarArchivo);
        //-------------------------------------- EDITAR
        editar.add(atras);
        editar.add(alante);
        editar.add(copiar);
        editar.add(pegar);
        editar.add(eliminar);
        //-------------------------------------- CODIGO
        codigo.add(ejecutar);
        codigo.add(compilar);
        //-------------------------------------- AJUSTES
        ajustes.add(colorFondo);
        ajustes.add(colorTexto);
        ajustes.add(imprimir);
        ajustes.add(salir);
        //-------------------------------------- AYUDA
        ayuda.add(infoDelIDE);
        ayuda.add(infoDelCreador);
        //Se añaden los JMenu al JMenuBar
        menu.add(archivo);
        menu.add(editar);
        menu.add(codigo);
        menu.add(ajustes);
        menu.add(ayuda);

        this.setJMenuBar(menu);
        //------------------------------------------------AREA DE TRABAJO-----------------------------------------------
        areaTrabajo = new JTextArea();
        panelPrincipal.add(areaTrabajo);
        //------------------------------------------------AREA DE COMPILACION-------------------------------------------
        areaCompilacion = new JTextArea();
        panelPrincipal.add(areaCompilacion);
        //--------------------------------------------------------------------------------------------------------------
        //------------------------------------------------GESTOR DE ARCHIVOS--------------------------------------------
        //gestorArchivos = new JTree();
        //panelPrincipal.add(gestorArchivos, BorderLayout.WEST);
        //------------------------------------------------SCROLL-------------------------------------------------
        scrollTrabajo = new JScrollPane();
        scrollTrabajo.setViewportView(areaTrabajo);
        panelPrincipal.add(scrollTrabajo, BorderLayout.CENTER);

        scrollCompilacion = new JScrollPane();
        scrollCompilacion.setViewportView(areaCompilacion);
        panelPrincipal.add(scrollCompilacion, BorderLayout.SOUTH);
        scrollCompilacion.setPreferredSize(new Dimension(150, 150)); //Tamaño
        //------------------------------------------------BARRA DE HERRAMIENTAS---------------------------------------------

        herramientas = new JToolBar();
        panelPrincipal.add(herramientas, BorderLayout.NORTH);
        String iconos = System.getProperty("user.dir") + File.separator + "icon" + File.separator;
        ImageIcon iconoFile = new ImageIcon(iconos+"file.png");
        ImageIcon iconoSave = new ImageIcon(iconos+"save.png");
        ImageIcon iconoSaveAs = new ImageIcon(iconos+"saveAs.png");
        ImageIcon iconoRun = new ImageIcon(iconos+"run.png");
        ImageIcon iconoDebug = new ImageIcon(iconos+"debug.png");
        ImageIcon iconoGitHub = new ImageIcon(iconos+"gitHub.png");
        JButton botonFile = new JButton();
        JButton botonSave = new JButton();
        JButton botonSaveAs = new JButton();
        JButton botonRun = new JButton();
        JButton botonDebug = new JButton();
        JButton botonGitHub = new JButton();
        //ubicacion = new JTextArea();
        botonFile.setIcon(iconoFile);
        botonSave.setIcon(iconoSave);
        botonSaveAs.setIcon(iconoSaveAs);
        botonRun.setIcon(iconoRun);
        botonDebug.setIcon(iconoDebug);
        botonGitHub.setIcon(iconoGitHub);
        herramientas.add(botonFile);
        herramientas.add(botonSave);
        herramientas.add(botonSaveAs);
        herramientas.add(botonRun);
        herramientas.add(botonDebug);
        herramientas.add(botonGitHub);
        //herramientas.add(ubicacion);  //Por si se quisiera mostrar la dirección completa de la carpeta


        //--------------------------------------------------------------------------------------------------------------
        //------------------------------------------------ACCIONES MENÚ-------------------------------------------------

        JFileChooser archivoAabrir = new JFileChooser();    //archivo seleccionado
        deshacer = new UndoManager();
        //Abrir archivo         OK
        abrirArchivo.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                metodoAbrirArchivo(archivoAabrir);
            }
        });

        crearArchivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File archivo = archivoAabrir.getSelectedFile();
                    archivo.createNewFile();
                    //File archivoNuevo = new File();
                }catch(Exception ex){
                    System.out.println("Error: "+ex);
                }
            }
        });

        //Guardar        OK
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metodoGuardar();
            }
        });

        //Guardar como...   OK
        guardarArchivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metodoGuardarComo(archivoAabrir);
            }
        });

        //cerrarArchivo y salir         OK
        cerrarArchivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //Para deshacer y rehacer
        areaTrabajo.getDocument().addUndoableEditListener(new UndoableEditListener(){
            public void undoableEditHappened(UndoableEditEvent evt) {
                deshacer.addEdit(evt.getEdit());
            }
        });

        //deshacer      OK
        atras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //deshace atras
                if (deshacer.canUndo())
                    deshacer.undo();
            }
        });

        //rehacer       OK
        alante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //rehace alante
                if (deshacer.canRedo())
                    deshacer.redo();
            }
        });

        //Copiar        OK
        copiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Declaro el objeto portapapeles:
                Clipboard portaPapeles;
                // Lo instancio:
                portaPapeles = Toolkit.getDefaultToolkit().getSystemClipboard();
                if (areaTrabajo.getSelectedText()!= null){
                    StringSelection seleccion=new StringSelection(""+areaTrabajo.getSelectedText());
                    portaPapeles.setContents(seleccion, seleccion);
                }

            }
        });
        //Pegar         OK
        pegar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Declaro el objeto portapapeles:
                Clipboard portaPapeles;
                // Lo instancio:
                portaPapeles = Toolkit.getDefaultToolkit().getSystemClipboard();
                Transferable datos=portaPapeles.getContents(null);
                try {
                    if (datos != null && datos.isDataFlavorSupported(DataFlavor.stringFlavor))
                        areaTrabajo.replaceSelection(""+datos.getTransferData(DataFlavor.stringFlavor));
                } catch (UnsupportedFlavorException | IOException ex) {System.err.println(ex);}
            }
        });
        //Vaciar contenido del JextArea         OK
        eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                areaTrabajo.setText("");
            }
        });
        //Imprimir contenido del JextArea       OK
        imprimir.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                areaTrabajo.print();
            }
        });


        //Ejecutar  OK
        ejecutar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metodoEjecutar();
            }
        });
        //Depurar   OK
        compilar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metodoCompilar();
            }
        });
        //Cambiar el color del texto y del fondo
        colorFondo.addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent e){
                //Mostramos el dialogo, indicamos el panel, el titulo del dialogo y un color por defecto
                //Devuelve un color
                Color color=JColorChooser.showDialog(panelPrincipal, "Elige un color", Color.BLACK);
                //Cambiamos el color de fondo de la etiqueta
                areaTrabajo.setBackground(color);
                areaTrabajo.setOpaque(true);
            }
        });
        colorTexto.addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent e){
                //Mostramos el dialogo, indicamos el panel, el titulo del dialogo y un color por defecto
                //Devuelve un color
                Color color=JColorChooser.showDialog(panelPrincipal, "Elige un color", Color.BLACK);
                //Cambiamos el color de texto de la etiqueta
                areaTrabajo.setForeground(color);
                areaTrabajo.setOpaque(true);
            }
        });
        //Ayuda
        infoDelIDE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                info("https://github.com/AdrianLozano96/IDE-con-Java-Swing");
            }
        });
        infoDelCreador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                info("https://github.com/AdrianLozano96");
            }
        });

        //---------------------------------------------BOTONES----------------------------------------------------------
        botonFile.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                metodoAbrirArchivo(archivoAabrir);
            }
        });
        botonSave.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                metodoGuardar();
            }
        });
        botonSaveAs.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                metodoGuardarComo(archivoAabrir);
            }
        });
        botonRun.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                metodoEjecutar();
            }
        });
        botonDebug.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                metodoCompilar();
            }
        });
        botonGitHub.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                info("https://github.com/AdrianLozano96");
            }
        });


        //--------------------------------------------------------------------------------------------------------------
        pack(); //Importante
        /*
        Unas notitas de más ;)
        JLabel.setForeground(Color.BLUE); //Color texto
        Font font = new Font("Arial", Font.BOLD, 30); //Fuente Texto
        JLabel.setFont(font);
        System.exit(0); //Cerrar programa
         */
        
    }

//------------------------------------------------MÉTODOS---------------------------------------------------------------
    /**
     * Metodo que permite acceder a una páguina web
     * @param uri {@link String}
     */
    private void info(String uri) {
        try {
            Desktop.getDesktop().browse(new URI(uri));
        } catch (URISyntaxException ex) {
            System.out.println(ex);
        }catch(IOException exe){
            System.out.println(exe);
        }
    }

    /**
     * Compila un archivo y genera un archivo.class en casa de compilar un archivo.java
     */
    private void metodoCompilar() {
        try{
            String exec = "cmd.exe /C javac "+abierto;
            Process process = Runtime.getRuntime().exec(exec);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Permite abrir archivos de diferentes extensiones
     * @param archivoAabrir {@link JFileChooser}
     * @throws FileNotFoundException
     */
    private void metodoAbrirArchivo(JFileChooser archivoAabrir) throws FileNotFoundException {
        areaTrabajo.setText("");    //Al abrir un nuevo archivo elimino el contenido del panel
        //Buscar y abrir
        archivoAabrir.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int resultado = archivoAabrir.showOpenDialog(archivoAabrir);//abrirArchivo
        //Coger el archivo
        File archivo = archivoAabrir.getSelectedFile();
        // muestra error si es inválido
        if ((archivo == null) || (archivo.getName().equals(""))) {
            JOptionPane.showMessageDialog(archivoAabrir, "Nombre de archivo inválido",
                    "Nombre de archivo inválido", JOptionPane.ERROR_MESSAGE);
        }
        //ubicacion.setText(archivo.getAbsolutePath()+"\n\n\n"); //Ruta del archivo completa
        TreePath path = new TreePath(archivo.getPath());
        //Escribe el contenido del archivo en el JTextArea
        Scanner scn = new Scanner(archivo);
        while (scn.hasNext()) {
            areaTrabajo.insert(scn.nextLine() + "\n", areaTrabajo.getText().length());
        }
        abierto = archivo.getAbsolutePath();
    }

    /**
     * Permite el guardado de un archivo
     */
    private void metodoGuardar() {
        File archivo = new File(abierto);
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
            bw.write(areaTrabajo.getText());    bw.flush();     bw.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        abierto = archivo.getAbsolutePath();
    }

    /**
     * Permite guardar un archivo escogiendo el directorio donde se quiera almacenar
     * @param archivoAabrir {@link JFileChooser}
     */
    private void metodoGuardarComo(JFileChooser archivoAabrir) {
        String texto=areaTrabajo.getText();
        int opcion = archivoAabrir.showSaveDialog(guardarArchivo);
        File archivo = archivoAabrir.getSelectedFile();
        try(FileWriter escritor = new FileWriter(archivo)){
            if(opcion == JFileChooser.APPROVE_OPTION)
                if(archivo!=null)
                    escritor.write(texto);
        }catch(IOException ex){
            System.out.println("Error"+ex.getMessage());
        }
        abierto = archivo.getAbsolutePath();
    }

    /**
     * Realiza la acción de run (ejecutar) un archivo por ejemplo .java
     */
    private void metodoEjecutar() {
        try{
            String exec = "cmd.exe /C java "+abierto;
            Process process = Runtime.getRuntime().exec(exec);
            areaCompilacion.setText("");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String lineas = areaTrabajo.getText();
            while((lineas = reader.readLine()) != null) {
                areaCompilacion.setText("\n"+areaCompilacion.getText()+"\n"+lineas+"\n");
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}