package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for renderer.ImageWriter class
 * @author Shir Perez and Yael Izralevitch
 */
class ImageWriterTests {

    /**
     * Test method for {@link ImageWriter#{MethodName}(${Parameters})}
     */
    @Test
    void testWriteToImage() {
        int Nx = 800;
        int Ny = 500;
        //net spacing is 50 pixels
        int net = 50; // (800 / 16) == (500 / 10) == 50

        Color pink = new Color(255d, 29d, 190d);
        Color purple = new Color(191d, 155d, 255d);

        ImageWriter imageWriter = new ImageWriter("firstImageTest", Nx, Ny);

        for(int i = 0; i < Nx; i++){
            for(int j = 0; j < Ny; j++){
                if(i % net == 0 || j % net == 0){
                    imageWriter.writePixel(i, j, pink);
                }
                else{
                    imageWriter.writePixel(i, j, purple);
                }
            }
        }

        imageWriter.writeToImage();
    }
}