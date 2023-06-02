// Importamos las librerias necesarias
// Para la lectura y esritura de ficheros
package interfaces;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class GestionDatos {
    
    private DefaultTableModel dt;
    private final String path;
    private FileWriter writer;
    private Scanner myReader;
    private File  fl;

    public GestionDatos(String path){
        this.path = path;
        this.writer = null;
        this.dt = null;
    }
    //Agrega un nuevo equiipo
    public void Add(String datos){
        try {
            this.writer = new FileWriter(this.path,true);
            this.writer.write(datos+"\n");
            this.writer.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }
    //Lee los datos para ser cargados en la JTable
    public TableModel readData(String[] head){
        fl = new File(this.path);
        this.dt = new DefaultTableModel();
        int id = 1;
        this.dt.setColumnIdentifiers(head);
        if (fl.exists()) {
            try {
                myReader = new Scanner(fl);
                while (myReader.hasNextLine()) {
                String data = id+","+myReader.nextLine();
                this.dt.addRow(data.split(","));
                id++;
                }
                myReader.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(GestionDatos.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return this.dt; 
    }

    //Eliminar un elemnento, por nombre
    public void delete(Object value) {
        fl = new File(path);
        if(fl.exists()){
            try {
                myReader = new Scanner(fl);
                String todo = "";
                while (myReader.hasNextLine()) { 
                    String data = myReader.nextLine();
                    String[] temp = data.split(",");           
                    if (!temp[0].equals(value.toString())) {
                        todo+=data+"\n";
                    }
                }
                myReader.close();
                try {
                    writer = new FileWriter(this.path);
                    writer.write(todo);
                    writer.close();
                } catch (Exception e) {
                    System.out.println("Error al eliminar");
                }
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(GestionDatos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //Carga una lista de equipos, solo los quipos(nombres)
    //Retorna una lista de quipos
    public ArrayList listaEquipos(){
        ArrayList<String> equipos = new ArrayList<>();
        fl = new File(path);
        if (fl.exists()) {
            try {
                myReader = new Scanner(fl);
                while (myReader.hasNextLine()) {                    
                    String datos = myReader.nextLine();
                    String[]temp = datos.split(",");
                    equipos.add(temp[0]);
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        
        return equipos;
        
    }
}
