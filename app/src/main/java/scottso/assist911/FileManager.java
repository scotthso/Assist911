package scottso.assist911;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;

public class FileManager  {

    public static String[] fileNames;
    public static int accountCount;
    public static HashSet<String> accountNames = new HashSet<String>();

    public static void saveFile(AccountItem account, Context context) {
        try {
            FileOutputStream outputStream = context.openFileOutput(account.getAccountName(), Context.MODE_PRIVATE);
            outputStream.write(Integer.toString(account.getAccountTries()).getBytes());
            outputStream.write((""+"\n").getBytes());
            outputStream.write(Integer.toString(account.getAccountTimesOpened()).getBytes());
            outputStream.write((""+"\n").getBytes());
            outputStream.write(Integer.toString(account.getHighScore()).getBytes());
            outputStream.write((""+"\n").getBytes());
            outputStream.write(account.getAddress().getBytes());
            outputStream.write((""+"\n").getBytes());
            outputStream.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        accountNames.add(account.getAccountName());

        Log.d("test", "save called: " + account.getAccountName());
        refreshFiles(context);
        for (int i = 0; i < fileNames.length; i++) {
            Log.d("test", "current accounts: "+ fileNames[i]);
        }
    }

    public static AccountItem findAndReadAccount (String username, Context context) {
        refreshFiles(context);

        try {
            FileInputStream fis = context.openFileInput(username);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);

            int accountTries = Integer.parseInt(bufferedReader.readLine().trim());
            int timesOpened = Integer.parseInt(bufferedReader.readLine().trim());
            int highScore = Integer.parseInt(bufferedReader.readLine().trim());
            String address = String.valueOf(bufferedReader.readLine().trim());

            AccountItem item = new AccountItem(username, accountTries, timesOpened, highScore, address);

            return item;
        } catch (FileNotFoundException e) {
            return null;
            //e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveToAccount(AccountItem account, Context context) {
        //TODO: think of a better way to edit file instead of delete
        Log.d("test", "savetoaccount called on " + account.getAccountName());
        deleteFile(account.getAccountName(), context);

        saveFile(account, context);
    }

    public static void deleteFile(String username, Context context) {
        context.deleteFile(username);
        accountNames.remove(username);
        refreshFiles(context);
        Log.d("test", "delete called: " + username);
    }

    public static void refreshFiles(Context context) {
        fileNames = context.fileList();
        accountCount = fileNames.length;
    }
}