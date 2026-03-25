import java.io.*;
import java.util.*;
public class StudentRecordProcessor {
    // Поля для хранения данных
    private final List<Student> students = new ArrayList<>();
    private double averageScore;
    private Student highestStudent;

    // _____реализуйте класс Student ниже в этом же файле______
    public class Student{
        String name;
        int score;

        public Student(String name, int score){
            this.name = name;
            this.score = score;
        }

        public int getScore(){
            return this.score;
        }

        public String getName(){
            return this.name;
        }
    }


    /**
     * Task 1 + Task 2 + Task 5 + Task 6
     *  
     */
    public void readFile() {
        // TODO: реализуйте чтение файла здесь
        try (BufferedReader br = new BufferedReader(new FileReader("input/students.txt"))){
            String line;
            while ((line = br.readLine()) != null){
                try {
                    String[] parts = line.split(",");
                    if (parts.length != 2) {
                        throw new IllegalArgumentException("Invalid format");
                    }

                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    if (score < 0 || score > 100){
                        throw new InvalidScoreException("wrong score");
                    }

                    students.add(new Student(name, score));

                    System.out.println(line);

                } catch (NumberFormatException e) {
                    System.out.println("invalid number format");
                } catch (InvalidScoreException e){
                    System.out.println(e.getMessage());
                } catch (Exception e){
                    System.out.println("wrong data");
                }
            } 
        } catch (FileNotFoundException e){
            System.out.println("file not found");
        } catch (IOException e){
            System.out.println("error while trying to read an file");
        }
    }

    /**
     * Task 3 + Task 8
     */
    public void processData() {
        // TODO: обработка данных и сортировка здесь
        if (students.isEmpty()){
            System.out.println("invalid data to work with");
            return;
        }

        int suma = 0;
        for (Student i: students){
            suma += i.getScore();
        }

        averageScore = (double) suma / students.size();

        Student topStudent = students.get(0); // Берем первого за основу

        for (Student s : students) {
            if (s.getScore() > topStudent.getScore()) {
                topStudent = s;
            }
        }
        highestStudent = topStudent;

        students.sort((x, y) -> Integer.compare(y.getScore(), x.getScore())); 
    }

    /**
     * Task 4 + Task 5 + Task 8
     */
    public void writeFile() {
        // TODO: запись результата в файл здесь
        String output = "output/report.txt";

        try(BufferedWriter bw = new BufferedWriter(new FileWriter("output/report.txt"))){
            bw.write("avarage score: " + averageScore);
            bw.newLine();

            if (highestStudent != null){
                bw.write("Highest Student: " + highestStudent.getName() + " - " + highestStudent.getScore());
                bw.newLine();
            }

            for (Student s : students) {
                bw.write(s.getName() + " - " + s.getScore());
                bw.newLine();
            }

        } catch (IOException e){
            System.out.println("problems with writing file");
        }

    }

    public static void main(String[] args) {
        StudentRecordProcessor processor = new StudentRecordProcessor();

        try {
            processor.readFile();
            processor.processData();
            processor.writeFile();
            System.out.println("Processing completed. Check output/report.txt");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}

// class InvalidScoreException реализуйте меня
class InvalidScoreException extends Exception {
    public InvalidScoreException(String message){
        super(message);
    }
}
