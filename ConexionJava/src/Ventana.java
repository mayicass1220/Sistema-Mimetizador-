
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Ventana extends JFrame {

    private JPanel contentPane;
    private JTextField textField; 
    JTextField jTextFieldMensaje;
    JTextField txtcampo = new JTextField("", 20);
    Enumeration puertos_libres = null;
    CommPortIdentifier port = null; 
    SerialPort puerto_ser = null; 
    private OutputStream Output = null; 
    InputStream in = null;
    int temperatura = 10;
    Thread timer;
    JLabel lblNewLabel;
    JButton btnNewButton, btnNewButton_1, btnEnviar;

    private void EnviarDatos(String date) {
        try {
            Output.write(date.getBytes());
        } catch (IOException e) {
            System.exit(ERROR);
        }
    }

    public void letras() {
        String cadena = ""; 
        cadena = jTextFieldMensaje.getText(); 
    }

    public Ventana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 636, 365);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        btnNewButton = new JButton("Conectar");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                puertos_libres = CommPortIdentifier.getPortIdentifiers();
                int aux = 0;
                while (puertos_libres.hasMoreElements()) {
                    port = (CommPortIdentifier) puertos_libres.nextElement();
                    int type = port.getPortType();
                    if (port.getName().equals(textField.getText())) {
                        try {
                            puerto_ser = (SerialPort) port.open("puerto serial", 2000);
                            int baudRate = 9600; 

                            puerto_ser.setSerialPortParams(
                                    baudRate,
                                    SerialPort.DATABITS_8,
                                    SerialPort.STOPBITS_1,
                                    SerialPort.PARITY_NONE);
                            puerto_ser.setDTR(true);
                            Output = puerto_ser.getOutputStream();
                            in = puerto_ser.getInputStream();
                            btnNewButton_1.setEnabled(true);
                            btnNewButton.setEnabled(false);
                        } catch (IOException e1) {
                        } catch (PortInUseException e1) {
                            e1.printStackTrace();
                        } catch (UnsupportedCommOperationException e1) {
                            e1.printStackTrace();
                        }

                        break;
                    }
                }
            }
        });
        btnNewButton.setBounds(38, 63, 89, 23);
        contentPane.add(btnNewButton); 

        btnNewButton_1 = new JButton("Desconectar");
        btnNewButton_1.setEnabled(false);
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                puerto_ser.close();
                btnNewButton_1.setEnabled(false);
                btnNewButton.setEnabled(true);
            }
        });        
        btnNewButton_1.setBounds(205, 63, 128, 23);
        contentPane.add(btnNewButton_1);


        btnEnviar = new JButton("Enviar");
        btnEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EnviarDatos(jTextFieldMensaje.getText());
                jTextFieldMensaje.setText("");
                letras();
            }
        });
        btnEnviar.setBounds(205, 163, 228, 23);
        contentPane.add(btnEnviar);

        textField = new JTextField();
        textField.setBounds(38, 32, 86, 20);
        contentPane.add(textField);
        textField.setColumns(10);

        jTextFieldMensaje = new JTextField();
        jTextFieldMensaje.setBounds(138, 32, 186, 20);
        contentPane.add(jTextFieldMensaje);
        jTextFieldMensaje.setColumns(10);

        JLabel lblPuertoCom = new JLabel("Puerto COM");
        lblPuertoCom.setBounds(37, 11, 90, 14);
        contentPane.add(lblPuertoCom);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 372, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 337, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
