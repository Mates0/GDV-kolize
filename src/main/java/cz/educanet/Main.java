package cz.educanet;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33;

public class Main {

    public static void main(String[] args) throws Exception {
        String maze = Read.read();
        String[] bars = maze.split("\n");
        int distance = bars.length;
        int[][] numbers = new int[distance][distance];
        float area = 2 / (float) distance;

        for (int i = 0; i < distance; i++) {
            for (int j = 0; j < distance; j++) {
                numbers[i][j] = bars[i].charAt(j);
            }
        }

        //region: Window init
        GLFW.glfwInit();

        // Tell GLFW what version of OpenGL we want to use.
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);

        // Create the window...
        // We can set multiple options with glfwWindowHint ie. fullscreen, resizability etc.
        long window = GLFW.glfwCreateWindow(800, 600, "maze", 0, 0);
        if (window == 0) {
            GLFW.glfwTerminate();
            throw new Exception("Error, can't open the window");
        }
        GLFW.glfwMakeContextCurrent(window);

        // Tell GLFW, that we are using OpenGL
        GL.createCapabilities();
        GL33.glViewport(0, 0, 800, 600);

        // Resize callback
        GLFW.glfwSetFramebufferSizeCallback(window, (win, w, h) -> GL33.glViewport(0, 0, w, h));
        //endregion

        Shaders.initShaders();

        Block[][] squares = new Block[distance][distance];
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < distance; j++) {
                if (numbers[i][j] == '0') {
                    squares[i][j] = new Block((j * area - 1), 1 - (i * area), area);
                } else {
                    squares[i][j] = null;
                }
            }
        }
        while (!GLFW.glfwWindowShouldClose(window)) {
            // Key input management
            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS) {
                GLFW.glfwSetWindowShouldClose(window, true); // Send a shutdown signal...
            }
            // Change the background color
            GL33.glClearColor(0f, 0f, 0f, 1f);
            GL33.glClear(GL33.GL_COLOR_BUFFER_BIT);

            for (int i = 0; i < squares.length; i++) {
                for (int j = 0; j < squares.length; j++) {
                    if (squares[i][j] != null) {
                        squares[i][j].render();
                    }
                }
            }
            // Swap the color buffer -> screen tearing solution
            GLFW.glfwSwapBuffers(window);
            // Listen to input
            GLFW.glfwPollEvents();
        }
        // Don't forget to cleanup
        GLFW.glfwTerminate();
    }
}
