import org.junit.Test;

public class Job2Test {


    @Test
    public void debug() throws Exception {
        String[] input = new String[2];
        input[0] = "C:\\Users\\owner\\Documents\\Classes\\DS4433\\DS4433projects\\project1\\Proj1CSVs\\AccessLog.csv";
        input[1] = "C:\\Users\\owner\\Documents\\Classes\\DS4433\\DS4433projects\\project1\\Proj1Outputs\\job2output.csv";

        /*
        1. put the data.txt into a folder in your pc
        2. add the path for the following two files.
            windows : update the path like "file:///C:/Users/.../projectDirectory/data.txt"
            mac or linux: update the path like "file:///Users/.../projectDirectory/data.txt"
        */

        input[0] = "file:///C:/Users/owner/Documents/Classes/DS4433Project0/project0/data.txt";
        input[1] = "file:///C:/Users/owner/Documents/Classes/DS4433Project0/project0/output";

    }
}
