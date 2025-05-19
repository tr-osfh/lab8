package console;

import java.util.Scanner;

public class ConsoleManager {
    public void write(String message){
        System.out.println(message);
    }

    public String read(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }


}
