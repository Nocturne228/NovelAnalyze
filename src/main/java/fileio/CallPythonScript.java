package fileio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CallPythonScript {
    public void callPython(String pythonScriptPath) {
        Process proc;
        try {
            String[] args1 = new String[] { "python3", pythonScriptPath};
            proc = Runtime.getRuntime().exec(args1);
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
