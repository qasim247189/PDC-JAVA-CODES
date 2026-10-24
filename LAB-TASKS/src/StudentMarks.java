import java.util.HashMap;
import java.util.Map;
public class StudentMarks {
    public static void main(String[] args) {
        // 1. Create the map and insert five student records (name -> marks)
        Map<String, Integer> students = new HashMap<>();
        students.put("Ali", 78);
        students.put("Sara", 85);
        students.put("Hamza", 69);
        students.put("Ayesha", 92);
        students.put("Usman", 74);
        System.out.println("=== All Student Records ===");
        for (Map.Entry<String, Integer> entry : students.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        students.put("Hamza", 75);
        System.out.println("\nMarks of Hamza updated from 69 to 75.");
        String target = "Hamza";
        Integer marks = students.get(target);
        System.out.println("Marks of " + target + " (after update): " + marks);
        System.out.println("Total number of students: " + students.size());
    }
}