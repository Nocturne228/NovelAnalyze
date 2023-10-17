package readtxt;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextFileReader
{
    private final String folderPath;
    private final List<String> fileContents;

    public TextFileReader(String folderPath)
    {
        this.folderPath = folderPath;
        this.fileContents = new ArrayList<>();
    }

    public void readFilesInFolder()
    {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null)
        {
            for (File file : files)
            {
                if (file.isFile() && file.getName().endsWith(".txt"))
                {
                    readFile(file);
                }
            }
        }
    }

    private void readFile(File file)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
            {
                content.append(line).append("\n");
            }
            fileContents.add(content.toString());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public List<String> getFileContents()
    {
        return fileContents;
    }
}
