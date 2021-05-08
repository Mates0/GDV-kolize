package cz.educanet;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {
        String file = "-0.19999999;-1.0;0.4\n" +
                "0.20000005;-1.0;0.4\n" +
                "0.6;-1.0;0.4\n" +
                "0.6;-0.6;0.4\n" +
                "-1.0;-0.19999999;0.4\n" +
                "-0.6;-0.19999999;0.4\n" +
                "-0.19999999;-0.19999999;0.4\n" +
                "0.6;-0.19999999;0.4\n" +
                "-1.0;0.20000005;0.4\n" +
                "-1.0;0.6;0.4\n" +
                "-0.6;0.6;0.4\n" +
                "-0.19999999;0.6;0.4\n";

        String[] filesplit = file.split("\n");
        ArrayList<Block> squares = new ArrayList<>();
        GLFW.glfwInit();
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        long window = GLFW.glfwCreateWindow(800, 600, "Collision", 0, 0);
        if (window == 0) {
            GLFW.glfwTerminate();
            throw new Exception("Error");
        }
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GL33.glViewport(0, 0, 800, 600);
        GLFW.glfwSetFramebufferSizeCallback(window, (win, w, h) -> GL33.glViewport(0, 0, w, h));
        Shaders.initShaders();
        Block block = new Block(0f, 0f, 0.25f);

        for (String s : filesplit) {
            String[] filesplit2 = s.split(";");
            Block square = new Block(Float.parseFloat(filesplit2[0]), Float.parseFloat(filesplit2[1]), Float.parseFloat(filesplit2[2]));
            squares.add(square);
        }

        while (!GLFW.glfwWindowShouldClose(window)) {
            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS)
                GLFW.glfwSetWindowShouldClose(window, true);
            boolean iscollisionactive = false;
            GL33.glClearColor(0f, 0f, 0f, 1f);
            GL33.glClear(GL33.GL_COLOR_BUFFER_BIT);
            for (Block square : squares) {
                square.render();
            }
            for (Block square : squares) {
                if (contact(block, square))
                    iscollisionactive = true;
            }
            block.update(window);
            block.render();
            if (!iscollisionactive)
                block.colorgreen();
            else
                block.colorred();

            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
        GLFW.glfwTerminate();
    }

    public static boolean contact(Block a, Block b) {
        return a.gety() + a.getz() / 2 + a.getz() > b.gety() &&
                a.gety() + a.getz() / 2 < b.gety() + b.getz() &&
                a.getx() < b.getx() + b.getz() &&
                a.getx() + a.getz() > b.getx();
    }
}