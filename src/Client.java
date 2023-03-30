import java.io.*;
import java.net.Socket;


public class Client {

    private static int socketNo;
    private static final String LOCALHOST = "localhost";

    private static final String UPLOAD_FILE_MESSAGE = "upload";

    private static final String GET_FILE_MESSAGE = "get";

    private static final String INVALID_INPUT_MESSAGE = "INVALID INPUT PROVIDED !!!!";
    private Socket rs;

    private DataInputStream dataInputStream;

    private DataOutputStream dataOutputStream;

    private FileInputStream fileInputStream;




    Client(){

    }

    private void uploadFile(String fileName){

        try{
            System.out.println("FILE NAME: " + fileName);
            dataOutputStream.writeUTF("POST");
            String fileNamePath = "/Users/akhil/computer_networks/" + fileName;
            File file = new File(fileNamePath);
            fileInputStream = new FileInputStream(file);
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
            System.out.println("Exception occurred in upload file. Exception Message is: " + ex.getMessage());
        }

    }

    private void getFile(String fileName){

        try{
            System.out.println("File Name is: " + fileName);
            String getFile = fileName;
            dataOutputStream.writeUTF("GET");
            dataOutputStream.writeUTF(fileName);
            String outputPath = "/Users/akhil/computer_networks/newDownloadTestFile.pptx";
            int bytes = 0;
            FileOutputStream fileOutputStream
                    = new FileOutputStream(outputPath);

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

        }catch (Exception e){
            System.out.println("Error in Getting File :" + e.getMessage());
        }



    }

    void run(){

        try {
            System.out.println("Client Program Initiated !!!!!");
            rs = new Socket(LOCALHOST, 5106);
            dataInputStream = new DataInputStream(rs.getInputStream());
            dataOutputStream = new DataOutputStream(rs.getOutputStream());

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while(true){

                String input  = br.readLine();
                String[] tokens = input.split(" ");
                System.out.println("Entered Input is : " + tokens[0]);

                if(tokens[0].equals(UPLOAD_FILE_MESSAGE)){

                    uploadFile(tokens[1]);
                }
                else if(tokens[0].equals(GET_FILE_MESSAGE)){
                    getFile(tokens[1]);

                }else{
                    System.out.println("Invalid Command Entered. Please follow the instructions - ");
                    System.out.println("1) To upload the file - Enter upload filename");
                    System.out.println("2) To download the file - Enter get filename");
                }

            }

        }catch(Exception ex){
            System.out.println("Exception occurred in Client with message: " + ex.getMessage());
        }


    }

    public static void main(String[] args){
        Client c = new Client();
        c.run();
    }
}
