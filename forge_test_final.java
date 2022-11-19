import javax.imageio.ImageIO;
import Jama.Matrix;
import Jama.SingularValueDecomposition;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.awt.*;

public class forge_test_final {
    //Importing images
    public static void main(String[] args) throws IOException{
            //Creating an image array lists to store the chunks or tiles of the original image
            ArrayList<BufferedImage> imgs = new ArrayList<BufferedImage>();
            ArrayList<BufferedImage> imgs2 = new ArrayList<BufferedImage>();
            ArrayList<BufferedImage> matches = new ArrayList<BufferedImage>();

            //Creating a two slide array lists to store the locations of each matched pair of chunks
            ArrayList<Slide> slidesA = new ArrayList<Slide>();
            ArrayList<Slide> slidesB = new ArrayList<Slide>();

            //Importing original image and test image
            String fileName1 = "rabbit_1.png";
            File origi = new File(fileName1);
            FileInputStream fis1 = new FileInputStream(origi);
            BufferedImage original = ImageIO.read(fis1);
            //original = Lum(original);

            String fileName2 = "rabbit_2.png";
            File fk = new File(fileName2);
            FileInputStream fis2 = new FileInputStream(fk);
            BufferedImage fake = ImageIO.read(fis2);
            //fake = Lum(fake);

            //Initializing the number of rows and columns the image should be divided into
            int rows = 4;
            int columns = 4;

            int Width = original.getWidth() / columns; // The width of each chunk
            int Length = original.getHeight() / rows; //The length of each chunk
            int count = 0;

            //Partioning the original image into chunks
            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < columns; y++) {
                    imgs.add(new BufferedImage(Width, Length, original.getType()));

                    Graphics2D g = imgs.get(count++).createGraphics();
                    int w = Width/2;    //The amount of width of each chunk that will overlap with the next
                    int l = Length/2;   //The amount of width of each chunk that will overlap with the next
    
                    if(x==0 && y==0){
                        g.drawImage(original, 0, 0, Width , Length, Width * y, Length * x , Width * y + Width+w, 
                        Length * x+l + Length, null);
                        slidesA.add(new Slide(Width * y, Length * x, Width * y + Width+w, Length * x+l + Length));
                    }

                    if(x==0 && y != 0){
                        g.drawImage(original, 0, 0, Width , Length, Width * y, Length * x , Width * y-y + Width+w,
                        Length * x+l + Length, null);
                        slidesA.add(new Slide(Width * y, Length * x, Width * y-y + Width+w, Length * x+l + Length));
                    }

                    if(x !=0 && y==0){
                        g.drawImage(original, 0, 0, Width , Length, Width * y, Length * x-l, Width * y + Width+w,
                        Length * x+l + Length, null);
                        slidesA.add(new Slide(Width * y, Length * x-l, Width * y + Width+w, Length * x+l + Length));
                    }

                    if(x==rows-1 && y==columns-1){
                        g.drawImage(original, 0, 0, Width , Length, Width * y, Length * x-l, Width * y-y + Width,
                        Length * x + Length, null);
                        slidesA.add(new Slide(Width * y, Length * x-l, Width * y-y + Width, Length * x + Length));
                    }

                    if(x==rows-1 && y !=columns-1){
                        g.drawImage(original, 0, 0, Width , Length, Width * y, Length * x-l, Width * y-y + Width+w,
                        Length * x + Length, null);
                        slidesA.add(new Slide(Width * y, Length * x-l, Width * y-y + Width+w, Length * x + Length));
                    }

                    if(x !=rows-1 && y==columns-1){
                        g.drawImage(original, 0, 0, Width , Length, Width * y, Length * x-l, Width * y-y + Width,
                        Length * x+l + Length, null);
                        slidesA.add(new Slide(Width * y, Length * x-l, Width * y-y + Width, Length * x+l + Length));
                    }

                    if(x !=0 && y!=0 && x!= rows-1 && y != columns-1)
                    {
                        g.drawImage(original, 0, 0, Width , Length, Width * y, Length * x-l, Width * y-y + Width+w,
                        Length * x+l + Length, null);
                        slidesA.add(new Slide(Width * y, Length * x-l, Width * y-y + Width+w, Length * x+l + Length));
                    }
                    g.dispose();
                }
            }

            //Resetting the count variable to 0;
            count = 0;

            //Partioning the test image into chunks
            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < columns; y++) {
                    imgs2.add(new BufferedImage(Width, Length, fake.getType()));

                    Graphics2D g = imgs2.get(count++).createGraphics();
                    int w = Width/2;    //The amount of width of each chunk that will overlap with the next
                    int l = Length/2;   //The amount of width of each chunk that will overlap with the next
    
                    if(x==0 && y==0){
                        g.drawImage(original, 0, 0, Width , Length, Width * y, Length * x , Width * y + Width+w, 
                        Length * x+l + Length, null);
                        slidesB.add(new Slide(Width * y, Length * x, Width * y + Width+w, Length * x+l + Length));
                    }

                    if(x==0 && y != 0){
                        g.drawImage(original, 0, 0, Width , Length, Width * y, Length * x , Width * y-y + Width+w,
                        Length * x+l + Length, null);
                        slidesB.add(new Slide(Width * y, Length * x, Width * y-y + Width+w, Length * x+l + Length));
                    }

                    if(x !=0 && y==0){
                        g.drawImage(original, 0, 0, Width , Length, Width * y, Length * x-l, Width * y + Width+w,
                        Length * x+l + Length, null);
                        slidesB.add(new Slide(Width * y, Length * x-l, Width * y + Width+w, Length * x+l + Length));
                    }

                    if(x==rows-1 && y==columns-1){
                        g.drawImage(original, 0, 0, Width , Length, Width * y, Length * x-l, Width * y-y + Width,
                        Length * x + Length, null);
                        slidesB.add(new Slide(Width * y, Length * x-l, Width * y-y + Width, Length * x + Length));
                    }

                    if(x==rows-1 && y !=columns-1){
                        g.drawImage(original, 0, 0, Width , Length, Width * y, Length * x-l, Width * y-y + Width+w,
                        Length * x + Length, null);
                        slidesB.add(new Slide(Width * y, Length * x-l, Width * y-y + Width+w, Length * x + Length));
                    }

                    if(x !=rows-1 && y==columns-1){
                        g.drawImage(original, 0, 0, Width , Length, Width * y, Length * x-l, Width * y-y + Width,
                        Length * x+l + Length, null);
                        slidesB.add(new Slide(Width * y, Length * x-l, Width * y-y + Width, Length * x+l + Length));
                    }

                    if(x !=0 && y!=0 && x!= rows-1 && y != columns-1)
                    {
                        g.drawImage(original, 0, 0, Width , Length, Width * y, Length * x-l, Width * y-y + Width+w,
                        Length * x+l + Length, null);
                        slidesB.add(new Slide(Width * y, Length * x-l, Width * y-y + Width+w, Length * x+l + Length));
                    }
                    g.dispose();
                }
            }

            System.out.println("Image Partitioning Completed...\n");


            //Comparing each chunk of the original image to every chunk of the test image
            for(int i = 0; i < imgs.size(); i++){
                for(int j = 0; j < imgs2.size(); j++){

                    //Obtaining the singular values of each chunk
                    double[] U1 = Singularize(imgs.get(i));
                    double[] U2 = Singularize(imgs2.get(j));

                    //Computing the EuclideanDistance of the singular values of each matrix
                    double difference = EuclideanDistance(U1, U2);
                    //System.out.println(difference);
                 
                    //Threshold Used: 
                    //90m to 100m with 6x6 6x7 7x7
                    if( difference < 25_000_000 && difference > 1_000_000 ){
                        matches.add(imgs.get(i));
                        matches.add(imgs2.get(j));
                    }
                }
            }

            //Printing matched pairs of chunks
            String x = "A";
            for (int i = 0; i < matches.size(); i++) {
                ImageIO.write(matches.get(i), "png", new File("Pair" + (i+1) + x + ".png"));
                if(x.equals("A")) x = "B";
                else x = "A";
            }

            System.out.println("Matched chunk pairs created...\n");

            if(matches.size() != 0){
                System.out.println(fileName2 + " is a forged version of " + fileName1 + "\n");
            }
    }

    //Function to convert an image's pixels to a matrix
    public static double[][] getImageMatrix(BufferedImage image) {
        int height = image.getHeight(null);
        int breadth = image.getWidth(null);

        double[][] ImageMatrix = new double[breadth][height];

        //Assigning the RGB values of each pixel to each cell
        for (int i = 0; i < breadth; i++) {
            for (int j = 0; j < height; j++) {
                ImageMatrix[i][j] = image.getRGB(i, j);  
            }
        }

        return ImageMatrix;
    }

    //Function to obtain the singular values of an image
    public static double[] Singularize(BufferedImage img){
        Matrix A = new Matrix(getImageMatrix(img));
        SingularValueDecomposition S = new SingularValueDecomposition(A);
        double[] U1 = S.getSingularValues();
        return U1;
    }

    //Function to obtain the EuclideanDistance between the pairs of values of two image matrices
    public static double EuclideanDistance(double[] A, double[] B){
        double sum = 0;
        for(int i = 0; i < A.length; i++){
            double val1 = A[i];
            double val2 = B[i];
            sum += Math.sqrt(Math.pow(val1 - val2, 2));
        }
        return sum;
    }

    //Slide class to contain the coordinates of each chunk
    public static class Slide{
        public int startX;
        public int startY;
        public int endX;
        public int endY;

        public Slide(int sX, int sY, int eX, int eY){
            startX = sX;
            startY = sY;
            endX = eX;
            endY = eY;
        }
    }

    public static BufferedImage Lum( BufferedImage current ){
        int height = current.getHeight(null);
        int breadth = current.getWidth(null);

        for (int i = 0; i < breadth; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = current.getRGB(i,j);
                double Y = 0.0;            
                Color colour = new Color(pixel, true);

                int red = colour.getRed();
                int green = colour.getGreen();
                int blue = colour.getBlue();

                if ((red == green) && (red == blue)) { Y = red; }
                else { Y = (0.29 * red) + (0.58 * green) + (0.11 * blue); }
                int Y_ = (int) (Math.round(Y));

                Color gr = new Color(Y_, Y_, Y_);
                int gn = gr.getRGB();
                current.setRGB(i, j, gn);
            }
        }
        return current;
    }
}