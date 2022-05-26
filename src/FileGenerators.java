import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FileGenerators {


    @Test
    public void generaterHashes_handMadePasswords() throws IOException {
        Random random = new Random(1000);
        FileWriter fileWriterSalt = new FileWriter("genSalt.txt");

        for(int i = 0; i < 5; i++) {
            String randomSalt = "$1$" + getRandomString(8, random);
            fileWriterSalt.write(randomSalt);
            fileWriterSalt.write('\n');

        }

        fileWriterSalt.close();
    }







    private int NUM_USERS = 50;
    private String PASSWD_FILE_NAME = "random2p";
    private String SHADOW_FILE_NAME = "random2s";

    @Test
    public void makeUserPasswdAndShadowFile() throws IOException {
        List<String> users = new ArrayList<>();
        Random random = new Random(1000);

        FileWriter fileWriter = new FileWriter(PASSWD_FILE_NAME);
        List<Integer> taken = new ArrayList<>();
        while(taken.size() < NUM_USERS) {
            int num = random.nextInt(NUM_USERS);
            if (taken.stream().noneMatch(s -> s == num)) {
                taken.add(num);
                String user = "user" + num;
                users.add(user);
                fileWriter.write(user);
                fileWriter.write(":x:");
                fileWriter.write(Integer.toString(num + 1));
                fileWriter.write(":");
                int groupId = 1000 + random.nextInt(NUM_USERS);
                fileWriter.write(Integer.toString(groupId));
                fileWriter.write(":GECOS:directory:");
                fileWriter.write("shell");
                fileWriter.write(Integer.toString(random.nextInt(NUM_USERS)));
                fileWriter.write('\n');
            }

        }

        fileWriter.close();


        fileWriter = new FileWriter(SHADOW_FILE_NAME);
        BufferedReader fileReader = new BufferedReader(new FileReader("hashedPasswords.txt"));
        for(String user : users) {
            fileWriter.write(user);
            fileWriter.write(":");
            String saltPassword = fileReader.readLine();
            fileWriter.write(saltPassword);
            fileWriter.write(":14538:0:99999:7:::");
            fileWriter.write('\n');
        }
        fileWriter.close();
        fileReader.close();
    }

    @Test
    public void genPasswords() throws IOException {

        Random random = new Random(1000);
        FileWriter fileWriterSalt = new FileWriter("genSalt.txt");
        FileWriter fileWriterPass = new FileWriter("genPasswords.txt");

        for(int i = 0; i < NUM_USERS; i++) {
            String randomSalt = "$1$" + getRandomString(8, random);
            String randomPass = getRandomString(24, random);
            fileWriterSalt.write(randomSalt);
            fileWriterSalt.write('\n');

            fileWriterPass.write(randomPass);
            fileWriterPass.write('\n');

        }

        fileWriterSalt.close();
        fileWriterPass.close();
    }


    private String getRandomString(int length, Random random) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < length; i++) {
            char c = (char) (random.nextInt(74) + 48);
            boolean retry = (c < 97 && c > 90) || (c < 65 && c > 57);
            while (retry) {
                c = (char) (random.nextInt(74) + 48);
                retry = (c < 97 && c > 90) || (c < 65 && c > 57);
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }


}
