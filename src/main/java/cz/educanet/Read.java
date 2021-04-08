package cz.educanet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Read {

    public static String read() {
        String mesh = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("Maze.txt"));
            String nextline = br.readLine();
            while (nextline != null) {
                mesh += nextline + "\n";
                nextline = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("error");
        }
        return mesh;
    }
}
