import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TotalHours {
    public static void main(String[] args) throws Exception {
        String line;
        String[] tokens;
        String firstName;
        String lastName;
        Map<String, List<String>> employeeMap = new HashMap<String, List<String>>();
        List<String> employeeHours;// = new ArrayList<String>();
        

        BufferedReader br = new BufferedReader(
            new FileReader("D:/development/TotalHours/Employee_Hours.csv")
        );

        System.out.println("First Name  LastName    Hours   OT");

        while((line = br.readLine()) != null) {
            tokens = line.split(",");

            if(tokens[0].equals("")) {
                firstName = tokens[2].replace("\"", "");
                lastName = tokens[1].replace("\"", "");

                employeeHours = new ArrayList<String>();

                while((line = br.readLine()) != null) {
                    //tokens = line.split(",");
                    tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                    if(tokens[0].equals("Totals:")) {
                        //employeeMap.put(firstName + lastName, tokens[7] + " " + tokens[8]);
                        tokens[7] = tokens[7].replace(",","");
                        tokens[7] = tokens[7].replace("\"", "");
                        tokens[8] = tokens[8].replace(",","");
                        tokens[8] = tokens[8].replace("\"", "");
                        employeeHours.add(tokens[7] + "," + tokens[8]);
                        employeeMap.put(firstName + " " + lastName, employeeHours);
                        System.out.println(firstName + "    " + lastName + "    " +
                            tokens[7] + "   " + tokens[8]);
                        line = br.readLine();
                        break;
                    }
                }
            }
        }

        br.close();

        int count = 0;

        for(String key : employeeMap.keySet()) {
            List<String> hours = new ArrayList<String>();
            
            hours = employeeMap.get(key);
            count++;

            for(String v : hours) {
                //String[] splits = v.split(",");
                String[] splits = v.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                float totalHours = Float.parseFloat(splits[0]);
                totalHours = totalHours + Float.parseFloat(splits[1]);

                System.out.println(count + ": " + key + " Total Hours = " + totalHours);
            }
        }
    }
}
