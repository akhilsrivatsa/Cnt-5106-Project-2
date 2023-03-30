import java.io.*;
import java.net.*;
import java.io.FileOutputStream;

public class Server {


    private static int socketNo;

    private ServerSocket sSocket;
    private Socket connection = null;

    private DataOutputStream dataOutputStream;

    private DataInputStream dataInputStream;

    private String message;

    Server(int socketNo){
        this.socketNo = socketNo;
    }


    private void run(){
        System.out.println("Server has been started");
        try{
            sSocket = new ServerSocket(socketNo);
            connection = sSocket.accept();
            while(true){
                System.out.println("Running While Loop");
                dataOutputStream = new DataOutputStream(connection.getOutputStream());
                dataInputStream = new DataInputStream(connection.getInputStream());

                String requestType = dataInputStream.readUTF();
                System.out.println("Arrived Here " + requestType);

                if(requestType.equals("POST")){
                    receiveFile("/Users/akhil/computer_networks/newuploadTestFile.pptx");
                }else{
                    fetchFile();
                }
            }

        }catch(Exception ex){
            System.out.println("Exception Occurred => " + ex.getMessage());
        }

    }

    private void fetchFile(){

        try {
            System.out.println("****Downloading File******");
            String fileToBeFetched = dataInputStream.readUTF();

            String fileNamePath = "/Users/akhil/computer_networks/" + fileToBeFetched;
            File file = new File(fileNamePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            dataOutputStream.writeLong(file.length());
            int bytes = 0;

            byte[] buff = new byte[1000];
            while ((bytes = fileInputStream.read(buff))
                    != -1) {
                dataOutputStream.write(buff, 0, bytes);
                dataOutputStream.flush();
            }
            fileInputStream.close();
        }catch(Exception ex){
            System.out.println("Exception is: " + ex.getMessage());
        }

    }


    private void receiveFile(String fileName)
            throws Exception
    {


        System.out.println("**** Receiving File ****");
        int bytes = 0;
        FileOutputStream fileOutputStream
                = new FileOutputStream(fileName);

        long size
                = dataInputStream.readLong();
        byte[] buffer = new byte[1000];
        while (size > 0
                && (bytes = dataInputStream.read(
                buffer, 0,
                (int)Math.min(buffer.length, size)))
                != -1) {
            fileOutputStream.write(buffer, 0, bytes);
            size -= bytes;
        }
        System.out.println("File is Received");
        fileOutputStream.close();
    }


    public static void main(String[] args){

       try{
           BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
           String inp = br.readLine();
           String[] tokens = inp.split(" ");
           if(tokens[0].equals("ftpclient") == false){
               System.out.println("INVALID COMMAND ENTERED! TERMINATING THE PROGRAM. ");
               System.exit(1);
           }
           int clientPort = Integer.parseInt(tokens[1]);
           Server s = new Server(clientPort);
           s.run();
       }catch (Exception ex){
           System.out.println("Exception Occurred: " + ex.getMessage());
       }


    }
}
