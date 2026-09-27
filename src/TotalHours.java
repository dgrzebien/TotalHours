import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TotalHours {
    private static Map<String, List<String>> employeeMap;

    // calculate the number of earned sick leave hours for an employee
    public static float calculateSickLeave(String hours, String OT) {
        float h = Float.parseFloat(hours);
        float overtime = Float.parseFloat(OT);

        // employee gets 1 hour of earned paid sick leave for every 30 hours worked
        return (h + overtime) / 30;
    }

    // print out the information in the employee map
    // employee first and last name
    // number of hours employee has worked
    // number of hours of paid sick leave employee has earned
    public static void printOut() {
        int count = 0;

        for(String key : employeeMap.keySet()) {
            List<String> hours = new ArrayList<String>();
            
            hours = employeeMap.get(key);
            count++;

            for(String v : hours) {
                String[] splits = v.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                float totalHours = Float.parseFloat(splits[0]);
                totalHours = totalHours + Float.parseFloat(splits[1]);
                float leave = Float.parseFloat(splits[2]);

                System.out.println(count + ": " + key + " Total Hours = " + totalHours + " " + leave);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String line;
        String[] tokens;
        String firstName;
        String lastName;
        employeeMap = new HashMap<String, List<String>>();
        List<String> employeeHours;
        double sickLeave;
        
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
                    tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                    if(tokens[0].equals("Totals:")) {
                        tokens[7] = tokens[7].replace(",","");
                        tokens[7] = tokens[7].replace("\"", "");
                        tokens[8] = tokens[8].replace(",","");
                        tokens[8] = tokens[8].replace("\"", "");
                        sickLeave = Math.round(calculateSickLeave(tokens[7], tokens[8]) * 100.0) / 100.0;
                        employeeHours.add(tokens[7] + "," + tokens[8] + "," + sickLeave);
                        employeeMap.put(firstName + " " + lastName, employeeHours);
                        System.out.println(firstName + "    " + lastName + "    " +
                            tokens[7] + "   " + tokens[8] + sickLeave);
                        line = br.readLine();
                        break;
                    }
                }
            }
        }

        br.close();

        printOut();
        
    }
}
