import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Visualizer extends JPanel {
    private static final int PADDING = 20;
    private static final int MAX_BAR_HEIGHT = 350, MIN_BAR_HEIGHT = 30;
    private Integer[] array;
    public Integer[] temp;
    private Bar[] bars;
    private int capacity, speed;
    public boolean hasArray;
    private Random rand = new Random();

    private Color originalColor, swappingColor, comparingColor;

    public boolean CheckedFinish = false;

    // drawing
    private BufferStrategy bs; // from the canvas
    private Graphics g;
    private EnableListener listener;

    protected PseudoCode pseudoCode = new PseudoCode();

    public Visualizer(int capacity, int fps, SortedListener listener, EnableListener listener1) {
        setLayout(null);
        this.capacity = capacity;
        this.speed = 6000 / fps;
        originalColor = ColorManager.BAR_WHITE;
        comparingColor = Color.YELLOW;
        swappingColor = ColorManager.BAR_RED;
        bs = listener.getBufferStrategy();
        hasArray = false;
        this.listener = listener1;
        temp = new Integer[capacity];
    }

    public Visualizer() {
    }

    public void setCapacity(int InputCapacity) {
        if (temp.length == InputCapacity)
            return;
        this.capacity = InputCapacity;
        temp = new Integer[this.capacity];
    }

    public void setFPS(int inputFPS) {
        this.speed = 6000 / inputFPS;
    }

    public void resetArray() {
        g = bs.getDrawGraphics();
        for (int i = 0; i < array.length; i++) {
            bars[i].clear(g);
        }
        bs.show();
        g.dispose();
        hasArray = false;
        bars = new Bar[capacity];
        array = new Integer[capacity];
    }

    public void createArray(int canvasWidth, int canvasHeight, boolean state) {
        array = new Integer[capacity];
        bars = new Bar[capacity];
        hasArray = true;

        // initial position
        double x = PADDING;
        int y = canvasHeight - PADDING * 5;

        // width of all bars
        double witdh = (double) (canvasWidth - PADDING * 2) / capacity;

        // get graphics
        g = bs.getDrawGraphics();
        g.setColor(ColorManager.CANVAS_BACKGROUND);
        g.fillRect(0, 0, canvasWidth, canvasHeight);
        Bar bar;
        for (int i = 0; i < array.length; i++) {
            // bars[i].clear(g);
            if (state == false)
                RandomArray(array, i);
            else
                array[i] = temp[i];
            bar = new Bar((int) x, y, (int) witdh, array[i], originalColor);
            bar.draw(g);
            bars[i] = bar;
            x += witdh;
        }
        bs.show();
        g.dispose();
    }

    public void RandomArray(Integer[] array, int i) {
        int value = rand.nextInt(MAX_BAR_HEIGHT) + MIN_BAR_HEIGHT;
        array[i] = value;
        temp[i] = value;
    }

    // for restore purpose
    public void drawArray() {
        if (!hasArray)
            return;

        g = bs.getDrawGraphics();

        for (int i = 0; i < bars.length; i++) {
            bars[i].draw(g);
        }

        bs.show();
        g.dispose();
    }

    public boolean isCreated() {
        if (!hasArray) {
            JOptionPane.showMessageDialog(null, "Bạn cần phải tạo mảng!", "Error", JOptionPane.ERROR_MESSAGE);
            listener.setEnabled(1);
        }
        return hasArray;
    }

    public void bubbleSort() {
        if (!isCreated())
            return;
        g = bs.getDrawGraphics();

        highLight(0);
        highLight(1);
        for (int i = array.length - 1; i >= 0; i--) {
            highLight(2);
            for (int j = 0; j < i; j++) {
                highLight(3);
                highLight(4);
                colorPair(j, j + 1, comparingColor);
                if (array[j] > array[j + 1]) {
                    highLight(5);
                    swap(j, j + 1);
                    highLight(3);
                } else {
                    highLight(3);
                }
                // truyen thong diep 2 thread
                clearPast(j, j + 1);
                if (j + 1 < i)
                    colorPair(j + 1, j + 2, comparingColor);
            }
            next(i);
        }
        highLight(0);
        finishAnimation();
    }

    // SELECTION SORT
    public void selectionSort() {
        if (!isCreated())
            return;

        // gett graphics
        g = bs.getDrawGraphics();
        // calculate elapsed time
        highLight(0);
        highLight(1);
        highLight(2);
        int i, j, min_idx;
        for (i = 0; i < array.length; i++) {
            highLight(3);
            highLight(4);
            min_idx = i;
            colorPair(i, i, comparingColor);
            for (j = i + 1; j < array.length; j++) {
                colorPair(j, j, comparingColor);
                highLight(5);
                if (array[j] < array[min_idx]) {
                    highLight(6);
                    min_idx = j;
                }
                clearPast(j, j + 1);
            }
            highLight(7);
            colorPair(min_idx, i, comparingColor);
            swap(min_idx, i);
            clearPast(min_idx, i);
            next(i);
        }
        highLight(0);
        finishAnimation();
    }

    public void insertionSort() {
        if (!isCreated())
            return;

        // gett graphics
        g = bs.getDrawGraphics();
        // calculate elapsed time
        highLight(0);
        highLight(1);
        for (int i = 1; i < array.length; i++) {
            highLight(2);
            highLight(3);
            int index = i - 1, element = array[i];
            while (index >= 0 && element < array[index]) {
                highLight(4);
                array[index + 1] = array[index];
                swap(index + 1, index);
                highLight(5);
                clearPast2(index, index + 1);
                index--;
                highLight(6);
            }
            highLight(8);
            index++;
            array[index] = element;
            nextOfInsertSort(index, element);
        }
        highLight(0);
        finishAnimation();
        finishAnimation();
    }

    public void nextOfInsertSort(int index, int element) {
        curT++;
        int cur = curT;
        threads[cur] = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (cur != 0) {
                        threads[cur - 1].join();
                    }
                    bars[index].clear(g);
                    bars[index].setValue(element);
                    bars[index].setColor(getBarColor(index));
                    bars[index].draw(g);
                    bs.show();
                } catch (Exception e) {

                }
            }
        });
        threads[cur].start();
        // neu dung ve 2
        // colorPair(index,index,swappingColor);
        // clearPast2(index, index);
    }

    public void processOfInsertionSort(int index) {
        curT++;
        int cur = curT;
        threads[cur] = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (cur != 0) {
                        threads[cur - 1].join();
                    }
                    bars[index + 1].clear(g);
                    bars[index + 1].setValue(bars[index].getValue());
                    bars[index].setValue(bars[index].getValue());
                    bars[index].setColor(comparingColor);
                } catch (Exception e) {

                }
            }
        });
        threads[cur].start();
        colorPair(index + 1, index + 1, swappingColor);
    }

    // /* QUICK SORT */
    public void quickSort() {
        if (!isCreated())
            return;

        // get graphics
        g = bs.getDrawGraphics();

        Quick(0, array.length - 1);
        highLight(0);
        finishAnimation();
        finishAnimation();
    }

    private void Quick(int left, int right) {
        highLight(0);
        highLight(1);
        highLight(2);
        int i, j, x;
        x = array[(left + right) / 2];
        colorPair((left + right) / 2, (left + right) / 2, comparingColor);
        highLight(3);
        i = left;
        colorPair(i, i, comparingColor);
        j = right;
        colorPair(j, j, comparingColor);
        highLight(4);
        while (i < j) {
            highLight(5);
            while (array[i] < x) {
                clearPast(i, i);
                i++;
                colorPair(i, i, comparingColor);
            }
            highLight(6);
            while (array[j] > x) {
                clearPast(j, j);
                j--;
                colorPair(j, j, comparingColor);
            }
            highLight(7);
            if (i <= j) {
                highLight(8);
                swap(i, j);
                highLight(9);
                clearPast(i, i);
                clearPast(j, j);
                i++;
                j--;
                colorPair(i, i, comparingColor);
                colorPair(j, j, comparingColor);
            }
        }
        clearPast(i, i);
        clearPast(j, j);
        highLight(10);
        highLight(11);
        highLight(12);
        if (left < j) {
            highLight(13);
            Quick(left, j);
            highLight(0);
        }
        highLight(14);
        if (i < right) {
            highLight(15);
            Quick(i, right);
            highLight(0);
        }
    }

    // Merge sort
    public void mergeSort() {
        if (!isCreated())
            return;
        g = bs.getDrawGraphics();
        highLight(0);
        mergeSort(0, array.length - 1);
        highLight(0);
        finishAnimation();
    }

    private void mergeSort(int left, int right) {
        highLight(1);
        if (left >= right) {
            return;
        }
        highLight(4);
        int middle = (right + left) / 2;
        highLight(5);
        mergeSort(left, middle);
        highLight(6);
        mergeSort(middle + 1, right);
        highLight(7);
        highLight(10);
        merge(left, middle, right);
    }

    private void merge(int left, int middle, int right) {
        Color mergeColor = getBarColor(middle);

        // number of items in the first half
        highLight(11);
        int n1 = middle - left + 1;
        highLight(12);
        int n2 = right - middle; // second half

        // create array for those parts
        highLight(14);
        int[] leftArr = new int[n1];
        highLight(15);
        for (int i = 0; i < n1; i++) {
            highLight(16);
            leftArr[i] = array[left + i];
        }
        highLight(18);
        int[] rightArr = new int[n2];
        highLight(19);
        for (int i = 0; i < n2; i++) {
            highLight(20);
            rightArr[i] = array[middle + i + 1];
        }

        highLight(22);
        int l = 0, r = 0, k = left; // k: for the original array

        highLight(24);
        while (l < n1 && r < n2) {
            highLight(25);
            int test = 0;
            highLight(26);
            if (leftArr[l] < rightArr[r]) {
                highLight(27);
                test = leftArr[l];
                highLight(28);
                l++;
                highLight(29);
            } else {
                highLight(30);
                test = rightArr[r];
                highLight(31);
                r++;
            }
            mergeTool(test, mergeColor, swappingColor, k);
            highLight(34);
            k++;
        }

        highLight(35);
        while (l < n1) {
            highLight(36);
            mergeTool(leftArr[l], mergeColor, swappingColor, k);
            highLight(37);
            k++;
            highLight(38);
            l++;
        }

        highLight(40);
        while (r < n2) {
            highLight(41);
            mergeTool(rightArr[r], mergeColor, swappingColor, k);
            highLight(42);
            k++;
            highLight(43);
            r++;
        }
    }

    private void mergeTool(int arrElement, Color color1, Color color2, int k) {
        Bar bar = bars[k];
        array[k] = arrElement;
        curT++;
        int cur = curT;
        threads[cur] = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (cur != 0) {
                        threads[cur - 1].join();
                    }
                    bar.clear(g);
                    // Thread.sleep(speed);
                    bar.setValue(arrElement);
                    // bar.setColor(color2);
                } catch (Exception e) {

                }
            }
        });
        threads[cur].start();
        // k++;
        colorPair(k, k, color2);
        // bar.setColor(color2);
        clearPast2(k, k);
    }

    int curT = -1;
    Thread[] threads = new Thread[1000000];

    public void testThread(int i) {
        curT++;
        int cur = curT;
        threads[cur] = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    if (cur != 0) {
                        threads[cur - 1].join();
                    }
                    bars[i].setColor(swappingColor);
                    Thread.sleep(speed);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

        });
        threads[cur].start();
    }

    public void highLight(int line) {
        curT++;
        int cur = curT;
        threads[cur] = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (cur != 0) {
                        threads[cur - 1].join();
                    }
                    Thread.sleep(speed);
                    pseudoCode.lsCode.setSelectedIndex(line);
                    pseudoCode.lsCode.ensureIndexIsVisible(line); // Tu cuon den dong dang highlight

                } catch (Exception e) {

                }
            }
        });
        threads[cur].start();
    }

    public void next(int i) {
        curT++;

        int cur = curT;
        threads[cur] = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (cur != 0) {
                        threads[cur - 1].join();
                    }
                    bars[i].setColor(getBarColor(i));
                    bars[i].draw(g);
                    bs.show();
                    // g.dispose();
                    // Thread.sleep(speed);
                } catch (Exception e) {

                }
            }
        });
        threads[cur].start();
    }

    private Color getBarColor(int value) {
        int interval = (int) (bars.length / 5.0);
        if (value < interval)
            return ColorManager.BAR_ORANGE;
        else if (value < interval * 2)
            return ColorManager.BAR_YELLOW;
        else if (value < interval * 3)
            return ColorManager.BAR_GREEN;
        else if (value < interval * 4)
            return ColorManager.BAR_CYAN;
        return ColorManager.BAR_BLUE;
    }

    private void swap(int i, int j) {
        colorPair(i, j, swappingColor);
        int temp = array[j];
        array[j] = array[i];
        array[i] = temp;

        curT++;
        int cur = curT;
        threads[cur] = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (cur != 0) {
                        threads[cur - 1].join();
                    }
                    // swap the elements
                    // clear the bar
                    bars[i].clear(g);
                    bars[j].clear(g);
                    // Thread.sleep(speed);
                    // swap the drawings
                    bars[j].setValue(bars[i].getValue());
                    bars[i].setValue(temp);
                } catch (Exception e) {

                }
            }
        });
        threads[cur].start();
        colorPair(i, j, swappingColor);
    }

    private void colorPair(int i, int j, Color color) {
        curT++;
        int cur = curT;
        threads[cur] = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (cur != 0) {
                        threads[cur - 1].join();
                    }
                    // drawing
                    bars[i].setColor(color);
                    bars[i].draw(g);
                    bars[j].setColor(color);
                    bars[j].draw(g);
                    bs.show();
                    Thread.sleep(speed);
                    // Thread.sleep(50);
                } catch (Exception e) {

                }
            }
        });
        threads[cur].start();
    }

    private void clearPast(int i, int j) {
        curT++;
        int cur = curT;
        threads[cur] = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (cur != 0) {
                        threads[cur - 1].join();
                    }
                    // drawing
                    Color color1 = ColorManager.BAR_WHITE;
                    bars[i].setColor(color1);
                    bars[i].draw(g);
                    bars[j].setColor(color1);
                    bars[j].draw(g);
                    bs.show();
                } catch (Exception e) {

                }
            }
        });
        threads[cur].start();
    }

    private void clearPast2(int i, int j) {
        curT++;
        int cur = curT;
        threads[cur] = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (cur != 0) {
                        threads[cur - 1].join();
                    }
                    // drawing
                    bars[i].setColor(getBarColor(i));
                    bars[i].draw(g);
                    bars[j].setColor(getBarColor(i));
                    bars[j].draw(g);
                    bs.show();
                    Thread.sleep(speed);
                } catch (Exception e) {

                }
            }
        });
        threads[cur].start();
    }

    private void finishAnimation() {
        curT++;
        int cur = curT;
        threads[cur] = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (cur != 0) {
                        threads[cur - 1].join();
                    }
                    // drawing
                    for (int i = 0; i < bars.length; i++) {
                        colorBar(i, comparingColor);
                        bars[i].setColor(getBarColor(i));
                        bars[i].draw(g);
                        bs.show();
                    }
                    // CheckedFinish = true;
                    listener.setEnabled(1);
                } catch (Exception e) {

                }
            }
        });
        threads[cur].start();
    }

    private void colorBar(int index, Color color) {
        Bar bar = bars[index];
        Color oldColor = bar.getColor();

        curT++;
        int cur = curT;
        threads[cur] = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (cur != 0) {
                        threads[cur - 1].join();
                    }
                    // drawing
                    bar.setColor(color);
                    bar.draw(g);
                    bs.show();

                    Thread.sleep(speed);

                    bar.setColor(oldColor);
                    bar.draw(g);

                    bs.show();
                    // Thread.sleep(speed);
                    // Thread.sleep(50);
                } catch (Exception e) {

                }
            }
        });
        threads[cur].start();
    }

    public void interupt() {
        for (int i = 0; i < curT; i++) {
            try {
                threads[i].interrupt();
                listener.setEnabled(1);
                resetArray();
            } catch (Exception e) {

            }
        }
        curT = -1;
    }

    public interface SortedListener {
        BufferStrategy getBufferStrategy();
    }

    public interface EnableListener {
        void setEnabled(int i);
    }
}
